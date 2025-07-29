package com.suqb.www;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.EasyExcel;
import com.suqb.www.domain.UserEntity;
import com.suqb.www.domain.excel.ProductEntity;
import com.suqb.www.listener.DataListener;
import com.suqb.www.mapper.UserMapper;
import com.suqb.www.service.SkuMapService;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.StringHttpMessageConverter;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@SpringBootTest
class SpringShardingSphereJdbcApplicationTests
{

    @Autowired
    private SkuMapService skuMapService;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StringHttpMessageConverter stringHttpMessageConverter;
    @Autowired
    private ListableBeanFactory listableBeanFactory;

    @Test
    void contextLoads()
    {
    }

    @Data
    private static class Image
    {
        private Integer userId;
        private String path;
        private String code;
    }


//    @Test
//    public void buildCreateAgencyPurchaseLink()
//    {
//        List<UserEntity> dataList = userMapper.queryBySql("SELECT id, ai_image_json as json FROM t_print_library");
//
//        String sql = "INSERT INTO t_print_image(parent_id, code, image_path, create_by, update_by, type) VALUE (%d, '%s', '%s', %d, %d, 1);";
//
//        for (UserEntity data : dataList)
//        {
//
//            String json = data.getJson();
//
//            List<Image> imageList = JSON.parseArray(json, Image.class);
//
//            for (Image image : imageList)
//            {
//                System.err.printf((sql) + "%n", data.getId(), image.getCode(), image.getPath(), image.getUserId(), image.getUserId());
//            }
//        }
//    }

    @Test
    public void query()
    {
        ArrayList<ProductEntity> exportList = new ArrayList<>();

        EasyExcel.read("C:/Users/wumingjie/Downloads/新-查询速卖通近6月所有出单SKU(2).xlsx", ProductEntity.class, new DataListener<>(exportList))
                .sheet(0)
                .doRead();


        List<String> collect = exportList.stream().map(ProductEntity::getProductSku).collect(Collectors.toList());


        Map<String, Integer> productMap = new HashMap<>();

        CollUtil.split(collect, 2000).forEach(subList -> {
            String skuJoin = subList.stream().map(e -> "'" + e + "'").collect(Collectors.joining(","));
            Map<String, Integer> subMap = userMapper.queryBySql("SELECT product_sku, product_id FROM t_product WHERE product_sku IN (" + skuJoin + ");").stream()
                    .collect(Collectors.toMap(UserEntity::getProductSku, UserEntity::getProductId));
            productMap.putAll(subMap);
        });

        Map<Integer, Integer> supplierMap = new HashMap<>();

        CollUtil.split(productMap.values(), 5000).forEach(subList -> {

            String productIdJoin = subList.stream().map(String::valueOf).collect(Collectors.joining(","));

            Map<Integer, List<UserEntity>> supplierGroup = userMapper.queryBySql("SELECT product_id, supplier_id, priority FROM t_product_supplier WHERE product_id in(" + productIdJoin + ") AND supplier_id != 58123").stream()
                    .collect(Collectors.groupingBy(UserEntity::getProductId));

            supplierGroup.forEach((id, list) -> {

                if (list.size() == 1)
                {
                    supplierMap.put(id, list.get(0).getSupplierId());
                }
                else
                {
                    list.stream()
                            .peek(e -> {
                                if (e.getPriority() == null)
                                {
                                    e.setPriority(2);
                                }
                            })
                            .min(Comparator.comparing(UserEntity::getPriority))
                            .ifPresent(e -> supplierMap.put(id, e.getSupplierId()));
                }
            });
        });

        Map<Integer, String> numberMap = new HashMap<>();
        CollUtil.split(supplierMap.values(), 5000).forEach(subList -> {
            String skuJoin = subList.stream().map(e -> "'" + e + "'").collect(Collectors.joining(","));
            Map<Integer, String> map = userMapper.queryBySql("SELECT supplier_id, supplier_number FROM t_supplier WHERE supplier_id in(" + skuJoin + ")").stream()
                    .collect(Collectors.toMap(UserEntity::getSupplierId, UserEntity::getSupplierNumber));
            numberMap.putAll(map);
        });


        for (ProductEntity entity : exportList)
        {
            Integer productId = productMap.get(entity.getProductSku());

            if (productId != null)
            {
                Integer supplierId = supplierMap.get(productId);

                if (supplierId != null)
                {
                    entity.setSupplierNumber(numberMap.get(supplierId));
                }
            }
        }



        EasyExcel.write("C:/Users/wumingjie/Downloads/速卖通动销SKU-供应商编号.xlsx", ProductEntity.class)
                .sheet("Pil")
                .doWrite(exportList);
    }
//
//
//    @Test
//    public void query()
//    {
//
//        ArrayList<ProductEntity> exportList = new ArrayList<>();
//
//        EasyExcel.read("C:/Users/wumingjie/Downloads/sku需匹配供应商信息(1).xlsx", ProductEntity.class, new DataListener<>(exportList))
//                .sheet(1)
//                .doRead();
//
//        Map<String, UserEntity> map = new HashMap<>();
//
//        CollUtil.split(exportList, 2000).forEach(subList -> {
//
//            String join = subList.stream().map(ProductEntity::getAlibabaOrderNo).map(e -> "'" + e + "'").collect(Collectors.joining(","));
//
//            Map<String, UserEntity> collect = userMapper.queryBySql("SELECT t_purchase_order.alibaba_order_id, supplier_number, seller_company_name FROM t_purchase_order LEFT JOIN t_supplier ON t_purchase_order.supplier_id = t_supplier.supplier_id LEFT JOIN t_alibaba_order ON t_purchase_order.alibaba_order_id = t_alibaba_order.alibaba_order_id WHERE t_purchase_order.alibaba_order_id IN (" + join + ");")
//                    .stream()
//                    .collect(Collectors.toMap(UserEntity::getAlibabaOrderId, Function.identity()));
//
//            map.putAll(collect);
//        });
//
//        exportList.forEach(item -> {
//            UserEntity userEntity = map.get(item.getAlibabaOrderNo());
//            if (userEntity != null) {
//                item.setSupplierNumber(userEntity.getSupplierNumber());
//                item.setSeller(userEntity.getSellerCompanyName());
//            }
//        });
//
//        HashMap<String, String> stringStringHashMap = new HashMap<>();
//
//        CollUtil.split(exportList, 2000).forEach(subList -> {
//            String join = subList.stream().map(ProductEntity::getSeller).map(e -> "'" + e + "'").collect(Collectors.joining(","));
//
//            userMapper.queryBySql("SELECT supplier_name, supplier_number FROM t_supplier WHERE supplier_name IN (" + join + ");")
//                    .forEach(item -> stringStringHashMap.put(item.getSupplierName(), item.getSupplierNumber()));
//        });
//
//        exportList.forEach(item -> {
//            item.setSupplierNumber(stringStringHashMap.get(item.getSeller()));
//        });
//
//
//        EasyExcel.write("C:/Users/wumingjie/Downloads/1688订单需匹配供应商信息-已处理.xlsx", ProductEntity.class)
//                .sheet("sku-一级供应商编号")
//                .doWrite(exportList);
//
//    }
//
//    public static String getBlackListStatus(Integer statsus)
//    {
//        if (statsus == null)
//        {
//            return "";
//        }
//
//        switch (statsus)
//        {
//            case 0:
//                return "正常";
//            case 1:
//                return "暂停上新";
//            case 2:
//                return "永久拉黑";
//            case 3:
//                return "灰名单";
//            case 4:
//                return "绿名单";
//            default:
//                return "";
//        }
//    }


//    @Test
//    public void query()
//    {
//
//        String sql = "SELECT product_id, t1.supplier_id FROM t_cloth_product_supplier t1 LEFT JOIN t_supplier t2 ON t1.supplier_id = t2.supplier_id LEFT JOIN t_supplier_extend t3 ON t1.supplier_id = t3.supplier_id WHERE product_id IN (205, 212, 213, 215, 232, 243, 257, 277, 278, 282, 283, 285, 289, 290, 293, 298, 300, 302, 306, 308, 309, 313, 315, 317, 318, 324, 326, 330, 332, 333, 338, 342, 345, 349, 351, 352, 354, 358, 359, 360, 364, 366, 367, 368, 369, 376, 378, 384, 385, 388, 390, 391, 392, 393, 394, 398, 399, 400, 405, 407, 410, 412, 414, 416, 421, 422, 424, 425, 426, 427, 428, 429, 430, 433, 443, 447, 449, 450, 455, 458, 460, 463, 464, 469, 470, 471, 472, 473, 474, 476, 477, 480, 481, 483, 489, 494, 496, 497, 500, 505, 508, 510, 514, 519, 522, 523, 528, 529, 532, 534, 537, 539, 540, 541, 546, 548, 552, 554, 555, 557, 561, 562, 563, 568, 569, 570, 573, 575, 579, 585, 587, 589, 594, 599, 600, 601, 603, 611, 612, 613, 616, 617, 620, 623, 626, 629, 631, 636, 637, 638, 639, 640, 643, 644, 645, 646, 647, 653, 658, 660, 661, 662, 664, 666, 667, 668, 671, 673, 674, 675, 676, 677, 678, 681, 684, 688, 691, 694, 695, 698, 702, 703, 708, 711, 712, 714, 720, 721, 735, 738, 739, 744, 745, 746, 747, 756, 760, 761, 762, 769, 771, 773, 783, 788, 790, 791, 793, 797, 799, 800, 813, 814, 817, 826, 834, 844, 860, 863, 874, 875, 876, 877, 889, 891, 894, 906, 945, 947, 952, 953, 964, 965, 982, 1003, 1057, 1075, 1078, 1090, 1092, 1110, 1119, 1125, 1151, 1158, 1165, 1186, 1194, 1238, 1251, 1270, 1275, 1276, 1277, 1298, 1311, 1313, 1322, 1550, 1551)";
//
//        List<UserEntity> dataList = userMapper.queryBySql(sql);
//
//        Map<Integer, UserEntity> subMap = dataList.stream()
//                .collect(Collectors.toMap(UserEntity::getProductId, Function.identity(), (v1, v2) -> v1));
//
//        Map<Integer, List<UserEntity>> collect = dataList.stream().collect(Collectors.groupingBy(UserEntity::getProductId));
//
//        Set<String> set = subMap.values().stream().map(item -> item.getProductId() + "--" + item.getSupplierId()).collect(Collectors.toSet());
//
//        collect.forEach((pid, list) -> {
//
//
//            for (UserEntity userEntity : list)
//            {
//                String key = pid + "--" + userEntity.getSupplierId();
//
//                if (!set.contains(key))
//                {
//                    System.err.printf("DELETE FROM t_cloth_product_supplier WHERE supplier_id = %d AND product_id = %d;%n", userEntity.getSupplierId(), pid);
//                }
//            }
//
//        });
//    }

//    @Test
//    public void query()
//    {
//
//        ArrayList<ProductEntity> exportList = new ArrayList<>();
//
//        EasyExcel.read("C:/Users/wumingjie/Downloads/SKU数据-3.10.xlsx/", ProductEntity.class, new DataListener<>(exportList))
//                .sheet(0)
//                .doRead();
//
//        String collect = exportList.stream().map(ProductEntity::getSku).map(e -> "'" + e + "'").collect(Collectors.joining(","));
//
//
//        Map<String, UserEntity> productInfoList = userMapper.queryBySql("SELECT product_id, product_sku, product_buyer FROM t_product WHERE product_sku IN (" + collect + ");")
//                .stream().collect(Collectors.toMap(UserEntity::getProductSku, Function.identity()));
//
//        String collect2 = productInfoList.values().stream().map(UserEntity::getProductBuyer).filter(Objects::nonNull).distinct().map(String::valueOf).collect(Collectors.joining(","));
//
//        Map<Integer, String> userMap = userMapper.queryBySql("SELECT u1.user_id AS id, u2.name as name FROM t_user u1 left JOIN t_user u2 ON u1.supervisor = u2.user_id WHERE u1.user_id IN (" + collect2 + ")")
//                .stream().collect(Collectors.toMap(UserEntity::getId, UserEntity::getName));
//
//        String collect1 = productInfoList.values().stream().map(UserEntity::getProductId).map(String::valueOf).collect(Collectors.joining(","));
//
//        Map<Integer, List<UserEntity>> supplierGroup = userMapper.queryBySql("SELECT product_id, supplier_id, priority, sales_price FROM t_product_supplier WHERE product_id in(" + collect1 + ")").stream()
//                .collect(Collectors.groupingBy(UserEntity::getProductId));
//
//        for (ProductEntity product : exportList)
//        {
//            UserEntity entity = productInfoList.get(product.getSku());
//
//            Integer productBuyer = entity.getProductBuyer();
//
//            product.setManager(userMap.get(productBuyer));
//
//            Integer productId = entity.getProductId();
//
//            List<UserEntity> supplierList = supplierGroup.get(productId);
//
//            if (CollUtil.isNotEmpty(supplierList))
//            {
//                supplierList.stream().peek(e -> {
//                    if (e.getPriority() == null)
//                    {
//                        e.setPriority(0);
//                    }
//                }).min(Comparator.comparing(UserEntity::getPriority))
//                        .ifPresent(supplier -> product.setSkuPrice(supplier.getSalesPrice()));
//            }
//        }
//
//
//        EasyExcel.write("C:/Users/wumingjie/Downloads/SKU数据-3.10-已处理.xlsx", ProductEntity.class)
//                .sheet("退款率")
//                .doWrite(exportList);
//    }


//    @Test
//    public void query()
//    {
//        List<UserEntity> productInfoList = userMapper.queryBySql("SELECT product_sku, least_sixty_sales, refund_rate60, category_id FROM t_product LEFT JOIN t_product_refund_rate ON t_product.product_sku = t_product_refund_rate.sku LEFT JOIN t_sales ON t_product.product_sku = t_sales.sku WHERE t_product.completion_date BETWEEN '2024-08-01 00:00:00' AND '2025-12-31 23:59:59';");
//
//        Map<Integer, UserEntity> categoryMap = userMapper.queryBySql("SELECT category_id as id, parent_id, category_name FROM t_product_category;")
//                .stream().collect(Collectors.toMap(UserEntity::getId, Function.identity()));
//
//        DecimalFormat decimalFormat = new DecimalFormat("##.00%");
//
//
//        ArrayList<ProductEntity> exportList = new ArrayList<>();
//
//
//        for (UserEntity supplierEntity : productInfoList)
//        {
//            ProductEntity productEntity = new ProductEntity();
//
//
//            Integer categoryId = supplierEntity.getCategoryId();
//            String categoryPath;
//            UserEntity userEntity4 = categoryMap.get(categoryId);
//
//            if (userEntity4 != null)
//            {
//                categoryPath = userEntity4.getCategoryName();
//                Integer id = userEntity4.getParentId();
//                if (id != null && id != -1)
//                {
//                    UserEntity userEntity3 = categoryMap.get(id);
//                    if (userEntity3 != null)
//                    {
//                        categoryPath = userEntity3.getCategoryName() + " -> " + categoryPath;
//                        id = userEntity3.getParentId();
//                        if (id != null && id != -1)
//                        {
//                            UserEntity userEntity2 = categoryMap.get(id);
//                            if (userEntity2 != null)
//                            {
//                                categoryPath = userEntity2.getCategoryName() + " -> " + categoryPath;
//                                id = userEntity2.getParentId();
//                                if (id != null && id != -1)
//                                {
//                                    UserEntity userEntity1 = categoryMap.get(id);
//                                    if (userEntity1 != null)
//                                    {
//                                        categoryPath = userEntity1.getCategoryName() + " -> " + categoryPath;
//
//                                        id = userEntity1.getParentId();
//                                        if (id != null && id != -1)
//                                        {
//                                            UserEntity userEntity = categoryMap.get(id);
//                                            if (userEntity != null)
//                                            {
//                                                categoryPath = userEntity.getCategoryName() + " -> " + categoryPath;
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                }
//
//                productEntity.setCate(categoryPath);
//                productEntity.setSku(supplierEntity.getProductSku());
//                productEntity.setSales(supplierEntity.getLeastSixtySales());
//
//
//                Double refundRate60 = supplierEntity.getRefundRate60();
//
//                if (refundRate60 == null || refundRate60 == 0)
//                {
//                    productEntity.setRefundRate60("0.00%");
//                }
//                else
//                {
//                    productEntity.setRefundRate60(decimalFormat.format(refundRate60));
//                }
//
//                exportList.add(productEntity);
//            }
//        }
//
//
//        EasyExcel.write("C:/Users/wumingjie/Downloads/24-08~24-12.xlsx", ProductEntity.class)
//                .sheet("退款率")
//                .doWrite(exportList);
//    }


//    @Test
//    public void query()
//    {
//        ArrayList<ProductEntity> exportList = new ArrayList<>();
//
//        EasyExcel.read("C:/Users/wumingjie/Downloads/红遍天下单SKU.xlsx/", ProductEntity.class, new DataListener<>(exportList))
//                .sheet(0)
//                .doRead();
//
//        List<String> collect = exportList.stream().map(ProductEntity::getSku).collect(Collectors.toList());
//
//
//        Map<String, Integer> productMap = new HashMap<>();
//
//        CollUtil.split(collect, 2000).forEach(subList -> {
//            String skuJoin = subList.stream().map(e -> "'" + e + "'").collect(Collectors.joining(","));
//            Map<String, Integer> subMap = userMapper.queryBySql("SELECT product_sku, product_id FROM t_product WHERE product_sku IN (" + skuJoin + ");").stream()
//                    .collect(Collectors.toMap(UserEntity::getProductSku, UserEntity::getProductId));
//            productMap.putAll(subMap);
//        });
//
//        Map<Integer, Integer> supplierMap = new HashMap<>();
//
//        CollUtil.split(productMap.values(), 5000).forEach(subList -> {
//
//            String productIdJoin = subList.stream().map(String::valueOf).collect(Collectors.joining(","));
//
//            Map<Integer, List<UserEntity>> supplierGroup = userMapper.queryBySql("SELECT product_id, supplier_id, priority FROM t_product_supplier WHERE product_id in(" + productIdJoin + ") AND supplier_id != 58123").stream()
//                    .collect(Collectors.groupingBy(UserEntity::getProductId));
//
//            supplierGroup.forEach((id, list) -> {
//
//                if (list.size() == 1)
//                {
//                    supplierMap.put(id, list.get(0).getSupplierId());
//                }
//                else
//                {
//                    list.stream()
//                            .peek(e -> {
//                                if (e.getPriority() == null)
//                                {
//                                    e.setPriority(2);
//                                }
//                            })
//                            .min(Comparator.comparing(UserEntity::getPriority))
//                            .ifPresent(e -> supplierMap.put(id, e.getSupplierId()));
//                }
//            });
//        });
//
//
//        Map<Integer, String> numberMap = new HashMap<>();
//        CollUtil.split(supplierMap.values(), 5000).forEach(subList -> {
//            String skuJoin = subList.stream().map(e -> "'" + e + "'").collect(Collectors.joining(","));
//            Map<Integer, String> map = userMapper.queryBySql("SELECT supplier_id, supplier_number FROM t_supplier WHERE supplier_id in(" + skuJoin + ")").stream()
//                    .collect(Collectors.toMap(UserEntity::getSupplierId, UserEntity::getSupplierNumber));
//            numberMap.putAll(map);
//        });
//
//
//        for (ProductEntity entity : exportList)
//        {
//            Integer productId = productMap.get(entity.getSku());
//
//            if (productId != null)
//            {
//                Integer supplierId = supplierMap.get(productId);
//
//                if (supplierId != null)
//                {
//                    entity.setBakSupplier(numberMap.get(supplierId));
//                }
//            }
//        }
//
//        EasyExcel.write("C:/Users/wumingjie/Downloads/红遍天下单SKU-已处理.xlsx", ProductEntity.class)
//                .sheet("SKU信息")
//                .doWrite(exportList);
//    }

//    @Test
//    public void query()
//    {
//        ArrayList<ProductEntity> exportList = new ArrayList<>();
//
//
//        List<UserEntity> productSupplierList = userMapper.queryBySql("SELECT product_id, supplier_id FROM t_product_supplier WHERE (product_id, supplier_id) IN (SELECT product_id, supplier_id FROM t_product_supplier WHERE product_id IN (SELECT product_id FROM t_product_supplier WHERE supplier_id = 58123) AND supplier_id != 58123);");
//
//        HashMap<Integer, List<Integer>> productSupplierMap = new HashMap<>();
//
//        for (UserEntity productSupplier : productSupplierList)
//        {
//            Integer supplierId = productSupplier.getSupplierId();
//            Integer productId = productSupplier.getProductId();
//
//            if (supplierId == null || productId == null || supplierId == 0 || productId == 0)
//            {
//                continue;
//            }
//
//            List<Integer> productList = productSupplierMap.computeIfAbsent(supplierId, e -> new ArrayList<>());
//
//            productList.add(productId);
//        }
//
//
//        HashMap<Integer, UserEntity> supplierMap = new HashMap<>();
//        CollUtil.split(productSupplierMap.keySet(), 5000).forEach(subList -> {
//
//            String supplierIdReq = subList.stream().map(String::valueOf).collect(Collectors.joining(","));
//            List<UserEntity> supplierList = userMapper.queryBySql("SELECT supplier_id, supplier_number, supplier_name FROM t_supplier WHERE supplier_id IN (" + supplierIdReq + ")");
//
//            if (CollUtil.isNotEmpty(supplierList))
//            {
//                for (UserEntity supplier : supplierList)
//                {
//                    supplierMap.put(supplier.getSupplierId(), supplier);
//                }
//            }
//        });
//
//        String productIdRequest = productSupplierMap.values().stream().filter(Objects::nonNull).flatMap(Collection::stream).map(String::valueOf).collect(Collectors.joining(","));
//
//        Map<Integer, String> productMap = userMapper.queryBySql("SELECT product_id, product_sku FROM t_product WHERE t_product.product_id IN (" + productIdRequest + ")")
//                .stream().collect(Collectors.toMap(UserEntity::getProductId, UserEntity::getProductSku));
//
//        productSupplierMap.forEach((supplierId, productIdList) -> {
//
//            if (supplierId != null && CollUtil.isNotEmpty(productIdList))
//            {
//                String productIdReq = productIdList.stream().map(productMap::get).map(e -> "'" + e + "'").collect(Collectors.joining(","));
//
//                List<UserEntity> inStockList = userMapper.queryBySql("SELECT product_id, quantity, price FROM t_in_stock LEFT JOIN t_in_stock_item ON t_in_stock.in_id = t_in_stock_item.in_id WHERE t_in_stock.confirm_date > '2024-07-01' AND in_status = 2 AND supplier_id = " + supplierId + " AND t_in_stock_item.sku IN (" + productIdReq + ");");
//
//                if (CollUtil.isNotEmpty(inStockList))
//                {
//                    for (UserEntity inStock : inStockList)
//                    {
//                        ProductEntity productEntity = new ProductEntity();
//                        productEntity.setProductSku(productMap.get(inStock.getProductId()));
//                        productEntity.setInStockQuantity(inStock.getQuantity());
//                        productEntity.setPrice(inStock.getPrice());
//
//                        UserEntity supplier = supplierMap.get(supplierId);
//                        if (supplier != null)
//                        {
//                            productEntity.setSupplierName(supplier.getSupplierName());
//                            productEntity.setSupplierNumber(supplier.getSupplierNumber());
//                        }
//
//                        exportList.add(productEntity);
//                    }
//                }
//            }
//        });
//
//        EasyExcel.write("C:/Users/wumingjie/Downloads/红遍天名下SKU2024年七月后被分流供应商采购信息.xlsx", ProductEntity.class)
//                .sheet("SKU信息")
//                .doWrite(exportList);
//    }

//    @Test
//    public void query()
//    {
//        ArrayList<ProductEntity> exportList = new ArrayList<>();
//
//        EasyExcel.read("C:/Users/wumingjie/Downloads/匹配系统编码.xlsx", ProductEntity.class, new DataListener(exportList))
//                .sheet(0)
//                .doRead();
//
//
//        String collect1 = exportList.stream().map(ProductEntity::getName).map(e -> "'" + e + "'").collect(Collectors.joining(","));
//
//        Map<String, String> collect = userMapper.queryBySql("SELECT supplier_name, supplier_number FROM t_supplier WHERE supplier_name in (" + collect1 + ") AND is_delete = 0").stream()
//                .collect(Collectors.toMap(UserEntity::getSupplierName, UserEntity::getSupplierNumber, (v1, v2) -> v1 ));
//
//        for (ProductEntity productEntity : exportList)
//        {
//            productEntity.setSupplierNumber(collect.get(productEntity.getName()));
//        }
//
//        EasyExcel.write("C:/Users/wumingjie/Downloads/匹配系统编码-已处理.xlsx", ProductEntity.class)
//                .sheet("SKU信息")
//                .doWrite(exportList);
//    }


//    @Test
//    public void join()
//    {
//        ArrayList<SupplierEntity> exportList = new ArrayList<>();
//
//        EasyExcel.read("C:\\Users\\wumingjie\\Downloads\\product.prepare.stock.approve_20250120072033672.xlsx", SupplierEntity.class, new DataListener(exportList))
//                .sheet(0)
//                .doRead();
//
//        String collect = exportList.stream().map(SupplierEntity::getSku).filter(StringUtils::isNotBlank).map(e -> "'" + e + "'").collect(Collectors.joining(","));
//
//        System.out.println(collect);
//
//    }
//
//    @Test
//    public void query()
//    {
//        ArrayList<ProductLevelStaticsEntity> exportList = new ArrayList<>();
//
//        EasyExcel.read("C:/workspace/database/主SKU销售退款信息.xlsx", ProductLevelStaticsEntity.class, new DataListener(exportList))
//                .sheet(0)
//                .doRead();
//
//        Map<Integer, UserEntity> categoryMap = userMapper.queryBySql("SELECT category_id as id, parent_id, category_name FROM t_product_category;")
//                .stream().collect(Collectors.toMap(UserEntity::getId, Function.identity()));
//
//        exportList.removeIf(e -> e.getHistorySales() == null || e.getHistorySales() == 0);
//
//        DecimalFormat decimalFormat = new DecimalFormat("##.00%");
//
//        for (ProductLevelStaticsEntity supplierEntity : exportList)
//        {
//            String category = supplierEntity.getCategory();
//            if (StringUtils.isNotBlank(category))
//            {
//                String categoryPath;
//
//                Integer categoryId = (int) Double.parseDouble(category);
//
//                UserEntity userEntity4 = categoryMap.get(categoryId);
//
//                if (userEntity4 != null)
//                {
//                    categoryPath = userEntity4.getCategoryName();
//                    Integer id = userEntity4.getParentId();
//                    if (id != null && id != -1)
//                    {
//                        UserEntity userEntity3 = categoryMap.get(id);
//                        if (userEntity3 != null)
//                        {
//                            categoryPath = userEntity3.getCategoryName() + " -> " + categoryPath;
//                            id = userEntity3.getParentId();
//                            if (id != null && id != -1)
//                            {
//                                UserEntity userEntity2 = categoryMap.get(id);
//                                if (userEntity2 != null)
//                                {
//                                    categoryPath = userEntity2.getCategoryName() + " -> " + categoryPath;
//                                    id = userEntity2.getParentId();
//                                    if (id != null && id != -1)
//                                    {
//                                        UserEntity userEntity1 = categoryMap.get(id);
//                                        if (userEntity1 != null)
//                                        {
//                                            categoryPath = userEntity1.getCategoryName() + " -> " + categoryPath;
//
//                                            id = userEntity1.getParentId();
//                                            if (id != null && id != -1)
//                                            {
//                                                UserEntity userEntity = categoryMap.get(id);
//                                                if (userEntity != null)
//                                                {
//                                                    categoryPath = userEntity.getCategoryName() + " -> " + categoryPath;
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//
//                    }
//
//                    supplierEntity.setCategory(categoryPath);
//
//                    String refundRate30 = supplierEntity.getRefundRate30();
//                    supplierEntity.setRefundRate30(StringUtils.isBlank(refundRate30) ? "0.00%" : decimalFormat.format(Double.parseDouble(refundRate30)));
//
//
//                    String refundRate60 = supplierEntity.getRefundRate60();
//                    supplierEntity.setRefundRate60(StringUtils.isBlank(refundRate60) ? "0.00%" : decimalFormat.format(Double.parseDouble(refundRate60)));
//
//                    String refundRateTotal = supplierEntity.getRefundRateTotal();
//                    supplierEntity.setRefundRateTotal(StringUtils.isBlank(refundRateTotal) ? "0.00%" : decimalFormat.format(Double.parseDouble(refundRateTotal)));
//                }
//            }
//        }
//
//        EasyExcel.write("C:/workspace/database/主SKU销售退款信息-已处理.xlsx", ProductLevelStaticsEntity.class)
//                .sheet("主SKU销售信息")
//                .doWrite(exportList);
//    }


//    @Test
//    public void exportAliasSkuOrderInfo()
//    {
//        ArrayList<SupplierEntity> exportList = new ArrayList<>();
//
//        EasyExcel.read("C:/workspace/database/24-11别名换主订单明细.xlsx", SupplierEntity.class, new DataListener(exportList))
//                .sheet(0)
//                .doRead();
//
//        Pattern pattern = Pattern.compile(  "别名SKU:[^,]+");
//
//
//        for (SupplierEntity entity : exportList)
//        {
//            String remark = entity.getAliasSku();
//
//            Matcher matcher = pattern.matcher(remark);
//
//            if (matcher.find())
//            {
//                String group = matcher.group();
//
//                String replace = group.replace("别名SKU:", "");
//
//                entity.setAliasSku(replace.replaceAll("替换.*", ""));
//            }
//            else
//            {
//                System.out.println();
//            }
//        }
//
//        String sku = exportList.stream().map(SupplierEntity::getAliasSku).filter(StringUtils::isNotBlank).map(e -> "'" + e + "'").collect(Collectors.joining(","));
//
//        Map<String, UserEntity> skuMap = userMapper.queryBySql("SELECT product_sku, job_number, name FROM t_product left join t_user ON t_product.product_developer = t_user.user_id WHERE product_sku IN (" + sku + ");")
//                .stream().collect(Collectors.toMap(UserEntity::getProductSku, Function.identity()));
//
//        for (SupplierEntity entity : exportList)
//        {
//            String aliasSku = entity.getAliasSku();
//
//            UserEntity userInfo = skuMap.get(aliasSku);
//            if (StringUtils.isNotBlank(aliasSku) && userInfo != null)
//            {
//                entity.setUsername(userInfo.getName());
//                entity.setJobNumber(userInfo.getJobNumber());
//            }
//        }
//
//        EasyExcel.write("C:/workspace/database/24-11别名换主订单明细-已处理.xlsx", SupplierEntity.class)
//                .sheet("工作表-1")
//                .doWrite(exportList);
//    }


//    @Test
//    public void exportPurchaseOrder() throws DocumentException
//    {
//        ArrayList<SupplierEntity> exportList = new ArrayList<>();
//
//        EasyExcel.read("C:\\workspace\\database\\近半年采购单明细信息.xlsx", SupplierEntity.class, new DataListener(exportList))
//                .sheet(1)
//                .doRead();
//
//        Map<Integer, UserEntity> categoryMap = userMapper.queryBySql("SELECT category_id as id, parent_id, category_name FROM t_product_category;")
//                .stream().collect(Collectors.toMap(UserEntity::getId, Function.identity()));
//        Map<Integer, String> mainMap = userMapper.queryBySql("SELECT category_id as id, category_name FROM t_main_product_category;")
//                .stream().collect(Collectors.toMap(UserEntity::getId, UserEntity::getCategoryName));
//
//        Map<String, String> addrMap = AddressUtils.extractProvinceCityEntity();
//
//
//        for (SupplierEntity supplierEntity : exportList)
//        {
//            Integer categoryId = supplierEntity.getCategoryId();
//            if (categoryId != null)
//            {
//                String categoryPath;
//
//                UserEntity userEntity4 = categoryMap.get(categoryId);
//
//                if (userEntity4 != null)
//                {
//                    categoryPath = userEntity4.getCategoryName();
//                    Integer id = userEntity4.getParentId();
//                    if (id != null && id != -1)
//                    {
//                        UserEntity userEntity3 = categoryMap.get(id);
//                        if (userEntity3 != null)
//                        {
//                            categoryPath = userEntity3.getCategoryName() + " -> " + categoryPath;
//                            id = userEntity3.getParentId();
//                            if (id != null && id != -1)
//                            {
//                                UserEntity userEntity2 = categoryMap.get(id);
//                                if (userEntity2 != null)
//                                {
//                                    categoryPath = userEntity2.getCategoryName() + " -> " + categoryPath;
//                                    id = userEntity2.getParentId();
//                                    if (id != null && id != -1)
//                                    {
//                                        UserEntity userEntity1 = categoryMap.get(id);
//                                        if (userEntity1 != null)
//                                        {
//                                            categoryPath = userEntity1.getCategoryName() + " -> " + categoryPath;
//
//                                            id = userEntity1.getParentId();
//                                            if (id != null && id != -1)
//                                            {
//                                                UserEntity userEntity = categoryMap.get(id);
//                                                if (userEntity != null)
//                                                {
//                                                    categoryPath = userEntity.getCategoryName() + " -> " + categoryPath;
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//
//                    }
//
//                    supplierEntity.setCategoryPath(categoryPath);
//                }
//
//                String addr = supplierEntity.getSourceGoods();
//                if (StringUtils.isBlank(addr))
//                {
//                    addr = Optional.ofNullable(addrMap.get(supplierEntity.getSourceAddrProvince())).orElse("") + Optional.ofNullable(addrMap.get(supplierEntity.getSourceAddrCity())).orElse("");
//                }
//                supplierEntity.setSourceAddrProvince(addr);
//
//                String mainProduct = supplierEntity.getMainProduct();
//                if (StringUtils.isNotBlank(mainProduct))
//                {
//                    String main = Arrays.stream(mainProduct.split(",")).filter(StringUtils::isNotBlank).map(Integer::parseInt).map(mainMap::get).filter(StringUtils::isNotBlank).collect(Collectors.joining(","));
//                    supplierEntity.setMainProduct(main);
//                }
//            }
//        }
//
//
//        EasyExcel.write("C:/workspace/database/近半年采购单明细信息-已处理-2.xlsx", SupplierEntity.class)
//                .sheet("sku分类信息")
//                .doWrite(exportList);
//    }


//    @Test
//    public void exportCategory()
//    {
//
//        ArrayList<SupplierEntity> parentSkuList = new ArrayList<>();
//
//        EasyExcel.read("C:/Users/wumingjie/Downloads/工作簿2.xlsx", SupplierEntity.class, new DataListener(parentSkuList))
//                .sheet()
//                .doRead();
//
//        List<UserEntity> categoryList = userMapper.queryBySql("SELECT category_id as id, parent_id, category_name FROM t_product_category;");
//        ArrayList<SkuCategoryEntity> exportList = new ArrayList<>();
//
//        List<String> paramsList = parentSkuList.stream().map(SupplierEntity::getSupplierNumber).filter(StringUtils::isNotBlank).collect(Collectors.toList());
//
//        for (List<String> batchSkuList : CollUtil.split(paramsList, 5000))
//        {
//
//            String params = batchSkuList.stream().map(e -> "'" + e + "'").collect(Collectors.joining(","));
//
//            List<UserEntity> skuList = userMapper.queryBySql("SELECT product_sku, category_id FROM t_product WHERE product_sku IN (" + params + ");");
//
//
//            for (UserEntity userEntity : skuList)
//            {
//                SkuCategoryEntity skuCategoryEntity = new SkuCategoryEntity();
//
//                skuCategoryEntity.setSku(userEntity.getProductSku());
//
//                Integer categoryId = userEntity.getCategoryId();
//
//                if (categoryId != null)
//                {
//                    UserEntity category = findCategoryByCid(categoryList, categoryId);
//                    if (category != null)
//                    {
//                        skuCategoryEntity.setCategoryName(category.getCategoryName());
//                    }
//                }
//
//                exportList.add(skuCategoryEntity);
//            }
//        }
//
//
//
//        EasyExcel.write("C:/Users/wumingjie/Desktop/sku分类.xlsx", SkuCategoryEntity.class)
//                .sheet("sku分类信息")
//                .doWrite(exportList);
//
//    }


//    @Test
//    public void getSkuMapService()
//    {
//        List<SkuMapDTO> skuMap = skuMapService.getByPlatformSku(Collections.singletonList("24924ZSAWJJ240116008KHS"));
//
//        System.out.println(skuMap);
//    }
//
//    @Test
//    public void exportSupplier()
//    {
//        ArrayList<SupplierEntity> parentSkuList = new ArrayList<>();
//
//        EasyExcel.read("C:/Users/wumingjie/Downloads/查找产品小类.xlsx", SupplierEntity.class, new DataListener(parentSkuList))
//                .sheet()
//                .doRead();
//
//
//        List<String> collect = parentSkuList.stream().map(SupplierEntity::getSupplierNumber).filter(StringUtils::isNotBlank).collect(Collectors.toList());
//
//
//
//        System.out.println(collect);
//    }


//    @Test
//    public void exportLargeSaless()
//    {
//
//        ArrayList<SupplierEntity> parentSkuList = new ArrayList<>();
//
//        EasyExcel.read("C:/Users/wumingjie/Downloads/查找产品小类.xlsx", SupplierEntity.class, new DataListener(parentSkuList))
//                .sheet()
//                .doRead();
//
//
//        List<String> paramsList = parentSkuList.stream().map(SupplierEntity::getSupplierNumber).filter(StringUtils::isNotBlank).collect(Collectors.toList());
//
//        List<UserEntity> skuList = userMapper.queryBySql("SELECT product_sku, category_id FROM t_product WHERE product_sku IN (" + "sku" + ");");
//
//        List<UserEntity> categoryList = userMapper.queryBySql("SELECT category_id as id, parent_id, category_name FROM t_product_category;");
//
//        ArrayList<SkuCategoryEntity> exportList = new ArrayList<>();
//
//        for (UserEntity userEntity : skuList)
//        {
//            SkuCategoryEntity skuCategoryEntity = new SkuCategoryEntity();
//
//            skuCategoryEntity.setSku(userEntity.getProductSku());
//
//            Integer categoryId = userEntity.getCategoryId();
//
//            if (categoryId != null)
//            {
//                UserEntity c4 = findCategoryByCid(categoryList, categoryId);
//                if (c4 != null)
//                {
//                    skuCategoryEntity.setClassify4(c4.getCategoryName());
//                    UserEntity c3 = findCategoryByCid(categoryList, c4.getParentId());
//                    if (c3 != null)
//                    {
//                        skuCategoryEntity.setClassify3(c3.getCategoryName());
//                        UserEntity c2 = findCategoryByCid(categoryList, c3.getParentId());
//                        if (c2 != null)
//                        {
//                            skuCategoryEntity.setClassify2(c2.getCategoryName());
//                            UserEntity c1 = findCategoryByCid(categoryList, c2.getParentId());
//                            if (c1 != null)
//                            {
//                                skuCategoryEntity.setClassify1(c1.getCategoryName());
//
//                            }
//                        }
//                    }
//                }
//            }
//
//            exportList.add(skuCategoryEntity);
//        }
//
//
//
//        EasyExcel.write("C:/Users/wumingjie/Desktop/sku分类.xlsx", SkuCategoryEntity.class)
//                .sheet("sku分类信息")
//                .doWrite(exportList);
//
//    }


//    @Test
//    public void exportLargeSales()
//    {
//
//        List<UserEntity> orderList = userMapper.queryBySql("SELECT platform_order_id FROM t_order WHERE paid_date BETWEEN '2024-12-02 00:00:00' AND '2024-12-02 23:59:59' AND t_order.status IN (3, 4, 15, 16, 17, 25, 27, 28, 31, 33, 90, 10067, 120, 10090, 10089, 10080);");
//        List<UserEntity> categoryList = userMapper.queryBySql("SELECT category_id as id, parent_id, category_name FROM t_product_category;");
//
//
//        ArrayList<UserEntity> orderItemList = new ArrayList<>();
//
//        for (List<UserEntity> subOrderIdList : CollUtil.split(orderList, 5000))
//        {
//            List<UserEntity> itemList = userMapper.queryBySql("SELECT product_sku, sale_quantity FROM t_order_item WHERE order_id IN (" + subOrderIdList.stream().map(UserEntity::getPlatformOrderId).map(e -> "'" + e + "'").collect(Collectors.joining(",")) +");");
//
//            orderItemList.addAll(itemList);
//        }
//
//        orderList = null;
//        System.gc();
//
//        Map<String, Long> skuSaleList = orderItemList.stream()
//                .filter(item -> item.getProductSku() != null)
//                .collect(
//                        Collectors.groupingBy(
//                                UserEntity::getProductSku,
//                                Collectors.summingLong(item -> Optional.ofNullable(item.getSaleQuantity()).orElse(0))
//                        )
//                );
//
//        ArrayList<LargeSaleExcelEntity> exportList = new ArrayList<>();
//
//
//        for (List<String> subSkuList: CollUtil.split(skuSaleList.keySet(), 5000))
//        {
//            String params = subSkuList.stream().map(e -> "'" + e + "'").collect(Collectors.joining(","));
//
//            Map<String, UserEntity> productMap = userMapper.queryBySql("SELECT product_id as id, product_sku, product_title, product_feature, category_id, listing_status, completion_date FROM t_product WHERE product_sku IN (" + params + ");")
//                    .stream().collect(Collectors.toMap(UserEntity::getProductSku, Function.identity(), (v1, v2) -> v1));
//
//            Map<String, UserEntity> saleMap = userMapper.queryBySql("SELECT sku as product_sku, yesterday_sales, least_thirty_sales, least_three_sales, least_seven_sales, least_fifteen_sales, least_sixty_sales, history_sales  FROM t_sales WHERE sku IN (" + params + ");")
//                    .stream().collect(Collectors.toMap(UserEntity::getProductSku, Function.identity(), (v1, v2) -> v1));
//
//            Map<String, UserEntity> stockMap = userMapper.queryBySql("SELECT sku as product_sku, in_transit_stock, stock, stockout  FROM t_stock WHERE sku IN (" + params + ");")
//                    .stream().collect(Collectors.toMap(UserEntity::getProductSku, Function.identity(), (v1, v2) -> v1));
//
//            Map<Integer, List<UserEntity>> purchasePriceMap = new HashMap<>();
//            String ids = productMap.values().stream().map(UserEntity::getId).map(String::valueOf).collect(Collectors.joining(","));
//            if (StringUtils.isNotBlank(ids))
//            {
//                purchasePriceMap = userMapper.queryBySql("SELECT product_id as id, priority, purchase_price FROM t_product_supplier WHERE product_id IN (" + ids + ");")
//                        .stream().collect(Collectors.groupingBy(UserEntity::getId));
//            }
//
//            for (String sku : subSkuList)
//            {
//                LargeSaleExcelEntity largeSaleExcelEntity = new LargeSaleExcelEntity();
//
//                largeSaleExcelEntity.setSku(sku);

    /// /                largeSaleExcelEntity.setLargeSale(skuSaleList.get(sku));
//
//                UserEntity product = productMap.get(sku);
//                if (product != null)
//                {
//                    largeSaleExcelEntity.setTitle(product.getProductTitle());
//                    largeSaleExcelEntity.setLabel(product.getProductFeature());
//
//                    Integer listingStatus = product.getListingStatus();
//                    if (listingStatus != null)
//                    {
//                        switch (listingStatus)
//                        {
//                            case 1:
//                                largeSaleExcelEntity.setListingStatus("上架");
//                                break;
//                            case 2:
//                                largeSaleExcelEntity.setListingStatus("停产下架");
//                                break;
//                            case 6:
//                                largeSaleExcelEntity.setListingStatus("节日下架");
//                                break;
//                            case 7:
//                                largeSaleExcelEntity.setListingStatus("季节下架");
//                                break;
//                        }
//                    }
//
//                    if (product.getCompletionDate() != null)
//                    {
//                        String completion = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(product.getCompletionDate());
//                        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
//                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//                        Date date1 = null;
//                        Date date2 = null;
//                        Long l = 0L;
//                        try
//                        {
//                            date1 = formatter.parse(completion);
//                            date2 = formatter.parse(now);
//                            l = (date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24);
//                        }
//                        catch (ParseException e)
//                        {
//                            e.printStackTrace();
//                        }
//                        String time = l.intValue() > 365 * 3 ? "超过3年" : (l.intValue() > 365 * 2 ? "3年内" : (l.intValue() > 365 ? "2年内" : (l.intValue() > 182 ? "1年内" : (l.intValue() > 90 ? "半年内" : "3个月内"))));
//                        largeSaleExcelEntity.setCompletionDate(time);
//                    }
//
//                    Integer categoryId = product.getCategoryId();
//
//                    if (categoryId != null)
//                    {
//                        UserEntity c3 = findCategoryByCid(categoryList, categoryId);
//                        if (c3 != null)
//                        {
//                            largeSaleExcelEntity.setClassify3(c3.getCategoryName());
//                            UserEntity c2 = findCategoryByCid(categoryList, c3.getParentId());
//                            if (c2 != null)
//                            {
//                                largeSaleExcelEntity.setClassify2(c2.getCategoryName());
//                                UserEntity c1 = findCategoryByCid(categoryList, c2.getParentId());
//                                if (c1 != null)
//                                {
//                                    largeSaleExcelEntity.setClassify1(c1.getCategoryName());
//                                }
//                            }
//                        }
//                    }
//
//                    List<UserEntity> supplierList = purchasePriceMap.get(product.getId());
//                    if (CollUtil.isNotEmpty(supplierList))
//                    {
//                        supplierList.stream()
//                                .filter(user -> user.getPriority() != null && user.getPurchasePrice() != null)
//                                .min(Comparator.comparing(UserEntity::getPriority))
//                                .map(UserEntity::getPurchasePrice)
//                                .ifPresent(largeSaleExcelEntity::setPrice);
//                    }
//                }
//
//                UserEntity sale = saleMap.get(sku);
//                if (sale != null)
//                {
//                    largeSaleExcelEntity.setSales(sale.getYesterdaySales());
//                    largeSaleExcelEntity.setSales3(sale.getLeastThreeSales());
//                    largeSaleExcelEntity.setSales7(sale.getLeastSevenSales());
//                    largeSaleExcelEntity.setSales15(sale.getLeastFifteenSales());
//                    largeSaleExcelEntity.setSales30(sale.getLeastThirtySales());
//                    largeSaleExcelEntity.setSales60(sale.getLeastSixtySales());
//                    largeSaleExcelEntity.setSalesHis(sale.getHistorySales());
//                }
//
//                UserEntity stock = stockMap.get(sku);
//                if (stock != null)
//                {
//                    largeSaleExcelEntity.setInTransitStock(stock.getInTransitStock());
//                    largeSaleExcelEntity.setStock(stock.getStock());
//                    largeSaleExcelEntity.setStockOut(stock.getStockout());
//                }
//
//                exportList.add(largeSaleExcelEntity);
//            }
//        }
//
//        EasyExcel.write("C:/Users/wumingjie/Desktop/昨日SKU销量信息.xlsx", LargeSaleExcelEntity.class)
//                .sheet("昨日SKU销量信息")
//                .doWrite(exportList);
//    }

    // 根据 cid 查找分类
//    private static UserEntity findCategoryByCid(List<UserEntity> cList, int cid)
//    {
//        for (UserEntity category : cList)
//        {
//            if (category.getId() == cid)
//            {
//                return category;
//            }
//        }
//        return null;
//    }

//    @Test
//    public void getAliasSkuSale()
//    {
//
//        // 字段较少，直接获取所有订单
//        List<UserEntity> orderList = userMapper.queryBySql("SELECT order_id, platform_order_id FROM t_order WHERE creation_date > '2024-01-01 00:00:00' AND status = 17");
//
//
//        // 按一万一批获取订单系统SKU与平台SKU
//        for (List<UserEntity> subOrderList : CollUtil.split(orderList, 10000))
//        {
//            // 查询系统SKU
//            Map<String, UserEntity> itemMap = userMapper.queryBySql("SELECT item_id, order_id as platform_order_id, product_sku FROM t_order_item WHERE order_id IN (" + subOrderList.stream().map(e -> "'" + e + "'").collect(Collectors.joining(",")) + ");")
//                    .stream().collect(Collectors.toMap(UserEntity::getPlatformOrderId, Function.identity()));
//
//            // 查询平台SKU
//            String itemQueryCondition = itemMap.values().stream().map(UserEntity::getItemId).map(String::valueOf).collect(Collectors.joining(","));
//            Map<Integer, String> itemExtendMap = userMapper.queryBySql("SELECT item_id, platform_sku FROM t_order_item_extend WHERE item_id IN (" + itemQueryCondition + ");")
//                    .stream().collect(Collectors.toMap(UserEntity::getItemId, UserEntity::getPlatformSku));
//
//            // 查询别名映射
//            Map<String, String> skuMap = skuMapService.getByPlatformSku(itemExtendMap.values()).stream().collect(Collectors.toMap(SkuMapDTO::getPlatformSku, SkuMapDTO::getSystemSku, (v1, v2) -> v1));
//
//            // 填充系统sku与平台SKU
//            for (UserEntity userEntity : subOrderList)
//            {
//                String platformOrderId = userEntity.getPlatformOrderId();
//                UserEntity item = itemMap.get(platformOrderId);
//
//                if (item != null)
//                {
//                    userEntity.setItemId(item.getItemId());
//                    userEntity.setProductSku(item.getProductSku());
//                    userEntity.setPlatformSku(itemExtendMap.get(item.getItemId()));
//                }
//
//
//            }
//
//            // 获取系统SKU对应的平台SKU
//        }
//    }


//    @Test
//    public void getNoStockMainSku()
//    {
//        List<UserEntity> productList = userMapper.queryBySql("SELECT product_id as id, product_sku as sku, product_developer as dev FROM t_product WHERE t_product.creation_date < '2024-05-31 23:59:59' AND listing_status = 1 AND (is_variation = 0 OR is_multi_attr = 0);");
//
//        ArrayList<DieOutExcelEntity> exportList = new ArrayList<>();
//
//        for (List<UserEntity> list : CollUtil.split(productList, 300))
//        {
//
//            List<String> skuList = list.stream().map(UserEntity::getSku).filter(StringUtils::isNotBlank).map(e -> "sku LIKE '" + e + "%'").collect(Collectors.toList());
//
//            List<UserEntity> stockList = userMapper.queryBySql("SELECT sku, stock, withhold_stock, in_transit_stock FROM t_stock WHERE " + String.join(" OR ", skuList));
//
//            ArrayList<String> filterSkuList = new ArrayList<>();
//
//            list.removeIf(parentProduct -> {
//                String sku = parentProduct.getSku();
//                Optional<UserEntity> first = stockList.stream().filter(e -> e.getSku().startsWith(sku))
//                        .filter(v -> v.getStock() != 0 && v.getWithholdStock() != 0 && v.getInTransitStock() != 0)
//                        .findFirst();
//
//                if (!first.isPresent())
//                {
//                    filterSkuList.add(sku);
//                    return false;
//                }
//                return true;
//            });
//
//            if (filterSkuList.isEmpty())
//            {
//                continue;
//            }
//
//            List<UserEntity> saleList = userMapper.queryBySql("SELECT sku,least_thirty_sales, least_sixty_sales, least_ninety_sales, history_sales  FROM t_sales WHERE " + filterSkuList.stream().filter(StringUtils::isNotBlank).map(e -> "sku LIKE '" + e + "%'").collect(Collectors.joining(" OR ")));
//
//            for (UserEntity product : list)
//            {
//                String sku = product.getSku();
//
//                int leastThirtySales = 0;
//                int leastSixtySales = 0;
//                int leastNinetySales = 0;
//                int historySales = 0;
//
//                List<UserEntity> subSalesList = saleList.stream()
//                        .filter(e -> e.getSku().startsWith(sku))
//                        .collect(Collectors.toList());
//
//                for (UserEntity e : subSalesList)
//                {
//                    if (e.getLeastThirtySales() != null)
//                    {
//                        leastThirtySales += e.getLeastThirtySales();
//                    }
//                    if (e.getLeastSixtySales() != null)
//                    {
//                        leastSixtySales += e.getLeastSixtySales();
//                    }
//                    if (e.getLeastNinetySales() != null)
//                    {
//                        leastNinetySales += e.getLeastNinetySales();
//                    }
//                    if (e.getHistorySales() != null)
//                    {
//                        historySales += e.getHistorySales();
//                    }
//                }
//
//                if (historySales > 100)
//                {
//                    continue;
//                }
//
//                DieOutExcelEntity dieOutExcelEntity = new DieOutExcelEntity();
//                dieOutExcelEntity.setId(product.getId());
//                dieOutExcelEntity.setSku(product.getSku());
//                dieOutExcelEntity.setDevId(product.getDev());
//                dieOutExcelEntity.setSales30(leastThirtySales);
//                dieOutExcelEntity.setSales60(leastSixtySales);
//                dieOutExcelEntity.setSales90(leastNinetySales);
//                dieOutExcelEntity.setSalesHis(historySales);
//
//                exportList.add(dieOutExcelEntity);
//            }
//        }
//
//        for (List<DieOutExcelEntity> subExportList : CollUtil.split(exportList, 2000))
//        {
//            String idList = subExportList.stream().map(DieOutExcelEntity::getId).map(String::valueOf).collect(Collectors.joining(","));
//            String userIdList = subExportList.stream().map(DieOutExcelEntity::getDevId).map(String::valueOf).collect(Collectors.joining(","));
//
//
//            Map<Integer, BigDecimal> priceMap = userMapper.queryBySql("SELECT product_id as id, min(purchase_price) as purchase_price FROM t_product_supplier WHERE product_id IN (" + idList + ") GROUP BY product_id;")
//                    .stream().collect(Collectors.toMap(UserEntity::getId, UserEntity::getPurchasePrice));
//
//            Map<Integer, UserEntity> userMap = userMapper.queryBySql("SELECT user_id as id, job_number as devNumber, name as devName, department_id FROM t_user WHERE user_id IN (" + userIdList + ");")
//                    .stream().collect(Collectors.toMap(UserEntity::getId, Function.identity()));
//
//            String deptIdList = userMap.values().stream().map(UserEntity::getDepartmentId).map(String::valueOf).collect(Collectors.joining(","));
//
//            Map<Integer, String> deptMap = userMapper.queryBySql("SELECT department_id, department_name FROM t_department WHERE t_department.department_id IN (" + deptIdList + ");")
//                    .stream().collect(Collectors.toMap(UserEntity::getDepartmentId, UserEntity::getDepartmentName));
//
//            subExportList.forEach(e -> {
//
//                UserEntity userEntity = userMap.get(e.getDevId());
//
//                if (userEntity != null)
//                {
//                    e.setDev("[" + userEntity.getDevNumber() + "]" + userEntity.getDevName());
//
//                    e.setDept(deptMap.get(userEntity.getDepartmentId()));
//                }
//
//                e.setPrice(priceMap.get(e.getId()));
//            });
//        }
//
//
//        EasyExcel.write("C:/Users/wumingjie/Desktop/淘汰SKU.xlsx", ExportExcelEntity.class)
//                .sheet("in-stock-amount")
//                .doWrite(exportList);
//    }

//    @Test
//    public void exportSupplierInStockAmount()
//    {
////        List<UserEntity> supplierList = userMapper.queryBySql("SELECT supplier_id as id, supplier_name, supplier_number FROM t_supplier WHERE supplier_id = 109;");
//        List<UserEntity> supplierList = userMapper.queryBySql("SELECT supplier_id as id, supplier_name, supplier_number FROM t_supplier;");
//
//        List<UserEntity> categoryList = userMapper.queryBySql("SELECT category_id as id, parent_id, category_name FROM t_product_category;");
//
////        List<UserEntity> orderList = userMapper.queryBySql("SELECT supplier_id as id, purchase_order FROM t_purchase_order WHERE t_purchase_order.creation_date > '2023-01-01 00:00:00' AND order_status IN (2, 3, 4, 5, 7, 8) AND supplier_id = 109;");
//        List<UserEntity> orderList = userMapper.queryBySql("SELECT supplier_id as id, purchase_order FROM t_purchase_order WHERE t_purchase_order.creation_date > '2023-01-01 00:00:00' AND order_status IN (2, 3, 4, 5, 7, 8);");
//
//        Map<Integer, List<UserEntity>> orderMap = orderList.stream().filter(e -> e.getId() != null).collect(Collectors.groupingBy(UserEntity::getId));
//
//        Map<Integer, String> categoryMap = categoryList.stream().collect(Collectors.toMap(UserEntity::getId, UserEntity::getCategoryName));
//
//        ArrayList<ExportExcelEntity> dataList = new ArrayList<>();
//
//        String inStockSql = "SELECT t_in_stock_item.sku as sku, (ifnull(quantity, 0) * ifnull(price, 0)) as amount\n" +
//                "FROM t_in_stock_item\n" +
//                "WHERE in_id IN (SELECT in_id\n" +
//                "                FROM t_in_stock\n" +
//                "                WHERE purchase_order IN (%s));";
//
//        String skuTypeSql = "SELECT product_sku as sku, category_id FROM t_product WHERE product_sku IN (%s);";
//        for (UserEntity supplier : supplierList)
//        {
//            List<UserEntity> purchaseOrderList = orderMap.get(supplier.getId());
//            if (CollUtil.isEmpty(purchaseOrderList))
//            {
//                System.err.println(supplier.getSupplierName() + "没有采购单数据");
//                continue;
//            }
//
//            String purchaseorder = purchaseOrderList.stream().map(e -> "'" + e.getPurchaseOrder() + "'").collect(Collectors.joining(","));
//
//            List<UserEntity> inStockList = userMapper.queryBySql(String.format(inStockSql, purchaseorder));
//
//            if (CollUtil.isEmpty(inStockList))
//            {
//                continue;
//            }
//
//            Set<String> skuList = inStockList.stream().map(UserEntity::getSku).map(e -> "'" + e + "'").collect(Collectors.toSet());
//
//            Map<String, Integer> skuTypeMap = userMapper.queryBySql(String.format(skuTypeSql, String.join(",", skuList)))
//                    .stream()
//                    .collect(Collectors.toMap(UserEntity::getSku, UserEntity::getCategoryId));
//
//
//            for (UserEntity inStock : inStockList)
//            {
//                Integer category = skuTypeMap.get(inStock.getSku());
//                if (category == null)
//                {
//                    System.err.println(inStock.getSku() + "找不到对应的分类");
//                }
//
//                inStock.setCategoryName(categoryMap.get(category));
//                inStock.setCategoryId(category);
//            }
//
//            Map<Integer, Double> categoryAmountMap = inStockList.stream()
//                    .filter(e -> e.getCategoryId() != null)
//                    .collect(Collectors.groupingBy(UserEntity::getCategoryId, Collectors.summingDouble(UserEntity::getAmount)))
//                    .entrySet()
//                    .stream()
//                    .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
//                    .limit(5)
//                    .collect(Collectors.toMap(
//                            Map.Entry::getKey,
//                            Map.Entry::getValue,
//                            (e1, e2) -> e1,
//                            LinkedHashMap::new
//                    ));
//
//            categoryAmountMap.forEach((k, v) -> {
//
//                ExportExcelEntity excel = new ExportExcelEntity();
//                excel.setSupplierNumber(supplier.getSupplierNumber());
//                excel.setSupplierName(supplier.getSupplierName());
//                excel.setAmount(v);
//                UserEntity topLevelCategory = findTopLevelCategory(categoryList, k);
//                excel.setCategoryPath(topLevelCategory == null ? "该分类在系统中已不存在" : topLevelCategory.getCategoryName() + "->" + categoryMap.get(k));
//
//                dataList.add(excel);
//            });
//        }
//
//        EasyExcel.write("C:/Users/wumingjie/Desktop/supplier-in-stock-amount(ExcludingShippingCosts).xlsx", ExportExcelEntity.class)
//                .sheet("in-stock-amount")
//                .doWrite(dataList);
//    }
//
    // 递归查找顶级分类
//    public static UserEntity findTopLevelCategory(List<UserEntity> cList, int currentCid) {
//        // 查找当前分类
//        UserEntity currentCategory = findCategoryByCid(cList, currentCid);
//        if (currentCategory == null) {
//            return null; // 如果没有找到该分类，返回 null
//        }
//
//        // 如果该分类的父分类是 -1，则当前分类是顶级分类
//        if (currentCategory.getParentId() == -1) {
//            return currentCategory;
//        }
//
//        // 否则，递归查找其父分类
//        return findTopLevelCategory(cList, currentCategory.getParentId());
//    }

//    @Test
//    public void getSupplierSkuList()
//    {
//        List<SupplierExcelEntity> supplierExcelEntityList = new ArrayList<>();
//
//        EasyExcel.read("C:/Users/wumingjie/Downloads/合并代采.xlsx", SupplierExcelEntity.class, new DataListener<>(supplierExcelEntityList))
//                .sheet()
//                // 在 sheet 方法之后， 在 doRead方法之前都是设置ReadSheet的参数
//                .doRead();
//
//        String supplierNumberList = supplierExcelEntityList.stream().map(SupplierExcelEntity::getSupplierNumber).filter(StrUtil::isNotBlank).map(e -> "'" + e + "'").collect(Collectors.joining(","));
//
//        List<UserEntity> supplierList = userMapper.queryBySql("SELECT supplier_id as id, supplier_number FROM t_supplier WHERE supplier_number IN (" + supplierNumberList + ");");
//
//        Map<Integer, String> supplierMap = supplierList.stream().collect(Collectors.toMap(UserEntity::getId, UserEntity::getSupplierNumber));
//
//        Set<Integer> supplierIdSet = supplierMap.keySet();
//
//
//        ArrayList<ExportExcelEntity> exportList = new ArrayList<>();
//
//        for (List<Integer> subIdList : CollUtil.split(supplierIdSet, 10))
//        {
//            List<UserEntity> supplierProductList = userMapper.queryBySql(
//                    "SELECT product_id as id, purchase_address, supplier_id FROM t_product_supplier WHERE supplier_id IN (" + subIdList.stream().map(String::valueOf).collect(Collectors.joining(",")) + ")"
//            );
//
//            Map<Integer, String> skuMap = userMapper.queryBySql(
//                    "SELECT product_id as id, product_sku as sku FROM t_product WHERE product_id IN (" + supplierProductList.stream().map(UserEntity::getId).map(String::valueOf).collect(Collectors.joining(",")) + ") AND listing_status = 1"
//            ).stream().collect(Collectors.toMap(UserEntity::getId, UserEntity::getSku));
//
//            String skuList = skuMap.values().stream().map(e -> "'" + e + "'").collect(Collectors.joining(","));
//
//            Map<String, Integer> saleMap = new HashMap<>();
//
//            try
//            {
//                saleMap = userMapper.queryBySql(
//                        "SELECT sku, least_seven_sales FROM t_sales WHERE sku IN (" + skuList + ")"
//                ).stream().peek(e -> {
//                    if (e.getLeastSevenSales() == null)
//                    {
//                        e.setLeastSevenSales(0);
//                    }
//                }).collect(Collectors.toMap(UserEntity::getSku, UserEntity::getLeastSevenSales));
//            }
//            catch (Exception e)
//            {
//                e.printStackTrace();
//            }
//
//            for (UserEntity userEntity : supplierProductList)
//            {
//                if (!skuMap.containsKey(userEntity.getId()))
//                {
//                    continue;
//                }
//
//                String sku = skuMap.get(userEntity.getId());
//
//                ExportExcelEntity exportExcelEntity = new ExportExcelEntity();
//                exportExcelEntity.setSupplierNumber(supplierMap.get(userEntity.getSupplierId()));
//                exportExcelEntity.setSku(sku);
//                exportExcelEntity.setAddress(exportExcelEntity.getSupplierNumber());
//                exportExcelEntity.setSales7(saleMap.get(sku));
//
//                exportList.add(exportExcelEntity);
//            }
//        }
//
//        EasyExcel.write("C:/Users/wumingjie/Desktop/合并代采SKU.xlsx", ExportExcelEntity.class).sheet("合并代采SKU").doWrite(exportList);
//    }


//    @Test
//    public void getHotSkuSupplier()
//    {
//        List<UserEntity> productList = userMapper.queryBySql("SELECT product_sku, parent_sku as product_sku FROM t_product WHERE product_feature LIKE '%爆款%';");
//
//        ArrayList<Product> list = new ArrayList<>();
//
//        String sql = "SELECT product_sku, concat('[', username, '] ', name) as buyer, group_concat(supplier_number) as supplierNo, group_concat(supplier_name) as supplierName\n" +
//                "FROM t_product\n" +
//                "         LEFT JOIN t_user ON t_product.product_buyer = t_user.user_id\n" +
//                "         LEFT JOIN t_product_supplier ON t_product.product_id = t_product_supplier.product_id\n" +
//                "         LEFT JOIN t_supplier ON t_product_supplier.supplier_id = t_supplier.supplier_id\n" +
//                "WHERE product_sku LIKE '%s'\n" +
//                "GROUP BY t_product.product_sku;";
//
//        for (UserEntity userEntity : productList)
//        {
//            String sku = StringUtils.isBlank(userEntity.getParentSku()) ? userEntity.getProductSku() : userEntity.getParentSku();
//
//            Product product = new Product();
//            product.setSku(sku);
//            list.add(product);
//
//            List<UserEntity> itemList = userMapper.queryBySql(String.format(sql, sku + '%'));
//
//            Optional<UserEntity> first = itemList.stream().filter(i -> i.getProductSku().equals(sku)).findFirst();
//
//            if (!first.isPresent())
//            {
//                continue;
//            }
//
//            UserEntity userEntity1 = first.get();
//
//            product.setBuyer(userEntity1.getBuyer());
//            product.setSupplierNo(userEntity1.getSupplierNo());
//            product.setSupplier(userEntity1.getSupplierName());
//
//            for (UserEntity entity : itemList)
//            {
//                if (entity.getProductSku().equals(sku))
//                {
//                    continue;
//                }
//
//                Product sproduct = new Product();
//                sproduct.setSku(entity.getProductSku());
//                sproduct.setBuyer(entity.getBuyer());
//                sproduct.setSupplierNo(entity.getSupplierNo());
//                sproduct.setSupplier(entity.getSupplierName());
//                list.add(sproduct);
//            }
//
//            if (itemList != null && !itemList.isEmpty())
//            {
//                StringBuilder skuJoin = new StringBuilder();
//                StringBuilder buyerJoin = new StringBuilder();
//                StringBuilder supplierNoJoin = new StringBuilder();
//                StringBuilder supplierNameJoin = new StringBuilder();
//                for (UserEntity entity : itemList)
//                {
//                    skuJoin.append(",").append(entity.getProductSku());
//
//
//                    String buyer = entity.getBuyer();
//                    if (StringUtils.isNotBlank(buyer))
//                    {
//                        buyerJoin.append(",").append(buyer);
//                    }
//
//                    String supplierNo = entity.getSupplierNo();
//                    if (StringUtils.isNotBlank(supplierNo))
//                    {
//                        supplierNoJoin.append(",").append(supplierNo);
//                    }
//
//                    String supplierName = entity.getSupplierName();
//                    if (StringUtils.isNotBlank(supplierName))
//                    {
//                        supplierNameJoin.append(",").append(supplierName);
//                    }
//
//
//                }
//
//                product.setSubSku(Arrays.stream(skuJoin.toString().split(",")).filter(StringUtils::isNotBlank).distinct().collect(Collectors.joining(",")));
//                product.setBuyer(Arrays.stream(buyerJoin.toString().split(",")).filter(StringUtils::isNotBlank).distinct().collect(Collectors.joining(",")));
//                product.setSupplierNo(Arrays.stream(supplierNoJoin.toString().split(",")).filter(StringUtils::isNotBlank).distinct().collect(Collectors.joining(",")));
//                product.setSupplier(Arrays.stream(supplierNameJoin.toString().split(",")).filter(StringUtils::isNotBlank).distinct().collect(Collectors.joining(",")));
//            }
//        }
//
//        EasyExcel.write("C:/Users/wumingjie/Desktop/爆款采购与供应商信息.xlsx", Product.class).sheet("模板").doWrite(list);
//    }

//    @Test
//    public void buildSql()
//    {
//        List<UserEntity> teamList = userMapper.queryBySql("SELECT id, account as `accountSite`, platform FROM t_bind_team;");
//
//        String accountList = teamList.stream().map(e -> "'" + e.getAccount() + "'").collect(Collectors.joining(","));
//
//        List<UserEntity> platformList = userMapper.queryBySql("SELECT account as `accountSite`, site as `sellSite`, platform, `group` as `businessId` FROM v_seller_account_beneficiary WHERE account IN (" + accountList +");");
//
//        List<String> updateList = new ArrayList<>(2000);
//
//        String updateSql = "UPDATE t_bind_team SET business_dept = %d WHERE id = %d;";
//        for (UserEntity userEntity : teamList)
//        {
//            Optional<Integer> first = platformList.stream()
//                    .filter(e -> {
//                        if (!e.getAccountSite().equals(userEntity.getAccount()))
//                        {
//                            return false;
//                        }
//
//                        if (!e.getPlatform().equals(userEntity.getPlatform()))
//                        {
//                            return false;
//                        }
//
//                        if (userEntity.getPlatform() == 3)
//                        {
//                            AmazonSiteEnum match = AmazonSiteEnum.match(userEntity.getSite());
//
//                            if (!match.code().equalsIgnoreCase(e.getSellSite()))
//                            {
//                                return false;
//                            }
//                        }
//                        else if (userEntity.getPlatform() == 18)
//                        {
//                            if (!userEntity.getSite().equalsIgnoreCase(e.getSellSite()))
//                            {
//                                return false;
//                            }
//                        }
//
//                        return true;
//                    })
//                    .map(UserEntity::getBusinessId)
//                    .findFirst();
//
//
//            if (!first.isPresent())
//            {
//                System.err.println("找不到对应的平台：" + userEntity.getAccountSite());
//                continue;
//            }
//
//            updateList.add(String.format(updateSql, first.get(), userEntity.getId()));
//        }
//
//        System.out.println(String.join("", updateList));
//    }

//    @Test
//    public void platform()
//    {
//        List<UserEntity> teamList = userMapper.queryBySql("SELECT id, account as `accountSite`, platform FROM t_bind_team WHERE platform = 0;");
//
//        String accountList = teamList.stream().map(e -> "'" + e.getAccount() + "'").collect(Collectors.joining(","));
//
//        List<UserEntity> platformList = userMapper.queryBySql("SELECT account as `accountSite`, site, platform FROM v_seller_account_beneficiary WHERE account IN (" + accountList +");");
//
//        List<String> updateList = new ArrayList<>(1600);
//        String updateSql = "UPDATE t_bind_team SET platform = %d WHERE id = %d;";
//        for (UserEntity userEntity : teamList)
//        {
//            Optional<Integer> first = platformList.stream()
//                    .filter(e -> e.getAccountSite().equals(userEntity.getAccount()))
//                    .map(UserEntity::getPlatform)
//                    .distinct()
//                    .findFirst();
//
//            if (!first.isPresent())
//            {
//                System.err.println("找不到对应的平台：" + userEntity.getAccountSite());
//                continue;
//            }
//            updateList.add(String.format(updateSql, first.get(), userEntity.getId()));
//        }
//
//        System.out.println(String.join("", updateList));
//    }
//
//    @Test
//    public void insertData() {
//
//        List<Product> products = new ArrayList<>();
//
//        EasyExcel.read("C:/Users/wumingjie/Downloads/上架服装主SKU11.8.xlsx", Product.class, new DataListener(products))
//                .sheet()
//                // 在 sheet 方法之后， 在 doRead方法之前都是设置ReadSheet的参数
//                .doRead();
//
//        List<String> skuList = products.stream().map(Product::getSku).filter(StringUtils::isNoneBlank).collect(Collectors.toList());
//
//
//        HashMap<String, String> fMap = new HashMap<>();
//
//        for (List<String> subSkuList : ListUtils.partition(skuList, 3000))
//        {
//            List<UserEntity> list = userMapper.querySkuFeatureBySku(subSkuList);
//            for (UserEntity map : list)
//            {
//                fMap.put(map.getName(), map.getSex());
//            }
//        }
//
//        products.forEach(product -> product.setFeature(fMap.get(product.getSku())));
//
//        System.out.println(products);
//
//        EasyExcel.write("C:/Users/wumingjie/Desktop/上架服装主SKU.xlsx", Product.class).sheet("模板").doWrite(products);
//    }
}
