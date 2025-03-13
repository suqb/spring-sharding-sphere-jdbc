package com.suqb.www.domain;

import com.suqb.www.util.CommonUtils;

import java.io.Serializable;
import java.sql.Timestamp;

public class Sales implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     *
     * This field corresponds to the database column t_sales.sales_id
     *
     * @mbggenerated Tue Jan 20 21:15:05 CST 2015
     */
    private Integer salesId;

    /**
     *
     * This field corresponds to the database column t_sales.sku
     *
     * @mbggenerated Tue Jan 20 21:15:05 CST 2015
     */
    private String sku;

    private Integer departmentId;

    private String departmentName;

    /**
     * 今天销量
     */
    private Integer todaySales;

    /**
     * 昨天销量
     * This field corresponds to the database column t_sales.yesterday_sales
     *
     * @mbggenerated Tue Jan 20 21:15:05 CST 2015
     */
    private Integer yesterdaySales;

    /**
     * 最近2天销量
     */
    private Integer leastTwoSales;

    /**
     * 最近3天销量
     */
    private Integer leastThreeSales;

    /**
     * 最近7天销量
     * This field corresponds to the database column t_sales.least_seven_sales
     *
     * @mbggenerated Tue Jan 20 21:15:05 CST 2015
     */
    private Integer leastSevenSales;

    /**
     * 屏蔽销量取屏蔽之前那周的销量
     */
    private Integer oldLeastSevenSales;

    /**
     * 最近15天销量
     *
     * @mbggenerated Tue Jan 20 21:15:05 CST 2015
     */
    private Integer leastFifteenSales;

    /**
     * 最近30天销量
     *
     * @mbggenerated Tue Jan 20 21:15:05 CST 2015
     */
    private Integer leastThirtySales;

    /**
     * 最近60天销量
     */
    private Integer leastSixtySales;

    /**
     * 最近90天销量
     */
    private Integer leastNinetySales;

    /**
     * 近半年销量
     */
    private Integer leastSixMonthSales;

    /**
     * 历史销量
     */
    private Integer historySales;

    /**
     *
     * 前面第一天销量
     *
     * @mbggenerated Mon Feb 27 14:14:39 CST 2017
     */
    private Integer before1thDay;

    /**
     *
     * This field corresponds to the database column t_sales.before_2th_day
     *
     * @mbggenerated Mon Feb 27 14:14:39 CST 2017
     */
    private Integer before2thDay;

    /**
     *
     * This field corresponds to the database column t_sales.before_3th_day
     *
     * @mbggenerated Mon Feb 27 14:14:39 CST 2017
     */
    private Integer before3thDay;

    /**
     *
     * This field corresponds to the database column t_sales.before_4th_day
     *
     * @mbggenerated Mon Feb 27 14:14:39 CST 2017
     */
    private Integer before4thDay;

    /**
     *
     * This field corresponds to the database column t_sales.before_5th_day
     *
     * @mbggenerated Mon Feb 27 14:14:39 CST 2017
     */
    private Integer before5thDay;

    /**
     *
     * This field corresponds to the database column t_sales.before_6th_day
     *
     * @mbggenerated Mon Feb 27 14:14:39 CST 2017
     */
    private Integer before6thDay;

    /**
     *
     * This field corresponds to the database column t_sales.before_7th_day
     *
     * @mbggenerated Mon Feb 27 14:14:39 CST 2017
     */
    private Integer before7thDay;

    /**
     *
     * This field corresponds to the database column t_sales.last_update_date
     *
     * @mbggenerated Tue Jan 20 21:15:05 CST 2015
     */
    private Timestamp lastUpdateDate;

    private Timestamp creationDate;

    /**
     * 买家邮编
     */
    private String buyerPostCode;

    /**
     * 仓库
     */
    private String warehouseName;

    /**
     * 建议3天销量(排除违规后的)
     */
    private Integer suggestThreeSales;

    /**
     * 建议7天销量(排除违规后的)
     */
    private Integer suggestSevenSales;

    /**
     * 建议15天销量(排除违规后的)
     */
    private Integer suggestFifteenSales;

    /**
     * 建议30天销量(排除违规后的)
     */
    private Integer suggestThirtySales;

    /**
     * 新预测销量
     */
    private Double newSuggestSales;

    private String supplierNo;

    /**
     * 标识模拟新平均销量标识
     */
    private boolean imitateAvgSalesFlag = false;

    /**
     * 缺货数量
     */
    private Integer stockout;

    /**
     * 预计日销量
     */
    private Double estimateDailySales;

    /**
     * 备货天数
     */
    private Integer stockDay;

    /**
     * 版型编号
     * @return
     */
    private String clothingTypeNumber;

    public String getClothingTypeNumber()
    {
        return clothingTypeNumber;
    }

    public void setClothingTypeNumber(String clothingTypeNumber)
    {
        this.clothingTypeNumber = clothingTypeNumber;
    }

    public Integer getSalesId()
    {
        return salesId;
    }

    public void setSalesId(Integer salesId)
    {
        this.salesId = salesId;
    }

    public String getSku()
    {
        return sku;
    }

    public void setSku(String sku)
    {
        this.sku = sku;
    }

    public Integer getDepartmentId()
    {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId)
    {
        this.departmentId = departmentId;
    }

    public String getDepartmentName()
    {
        return departmentName;
    }

    public void setDepartmentName(String departmentName)
    {
        this.departmentName = departmentName;
    }

    public Integer getTodaySales()
    {
        return todaySales;
    }

    public void setTodaySales(Integer todaySales)
    {
        this.todaySales = todaySales;
    }

    public Integer getYesterdaySales()
    {
        return yesterdaySales;
    }

    public void setYesterdaySales(Integer yesterdaySales)
    {
        this.yesterdaySales = yesterdaySales;
    }

    public Integer getLeastThreeSales()
    {
        return leastThreeSales;
    }

    /**
     * 3天平均销量
     *
     * <p>TODO 方法功能描述
     *
     * @return
     * @return Integer
     */
    public double getLeastThreeAverageSales()
    {
        int leastThreeAverageSales = leastThreeSales == null ? 0 : leastThreeSales;
        double calculated = leastThreeAverageSales / 3.0;

        return CommonUtils.scaleTwo(calculated);
    }

    /**
     * 2天平均销量
     *
     * <p>TODO 方法功能描述
     *
     * @return
     * @return Integer
     */
    public double getLeastTwoAverageSales()
    {
        int leastTwoAverageSales = leastTwoSales == null ? 0 : leastTwoSales;
        double calculated = leastTwoAverageSales / 2.0;

        return CommonUtils.scaleTwo(calculated);
    }


    public void setLeastThreeSales(Integer leastThreeSales)
    {
        this.leastThreeSales = leastThreeSales;
    }

    public Integer getLeastSevenSales()
    {
        return leastSevenSales;
    }

    /**
     * 7天平均销量
     *
     * <p>TODO 方法功能描述
     *
     * @return
     * @return Integer
     */
    public double getLeastSevenAverageSales()
    {
        int leastSevenAverageSales = leastSevenSales == null ? 0 : leastSevenSales;
        double calculated = leastSevenAverageSales / 7.0;

        return CommonUtils.scaleTwo(calculated);
    }

    public double getSuggestThreeAverageSales()
    {
        int suggestThreeAverageSales = suggestThreeSales == null ? 0 : suggestThreeSales;
        double calculated = suggestThreeAverageSales / 3.0;

        return CommonUtils.scaleTwo(calculated);
    }

    public double getSuggestSevenAverageSales()
    {
        int suggestSevenAverageSales = suggestSevenSales == null ? 0 : suggestSevenSales;
        double calculated = suggestSevenAverageSales / 7.0;

        return CommonUtils.scaleTwo(calculated);
    }

    public void setLeastSevenSales(Integer leastSevenSales)
    {
        this.leastSevenSales = leastSevenSales;
    }

    public Integer getLeastFifteenSales()
    {
        return leastFifteenSales;
    }

    /**
     * 15天平均销量
     *
     * <p>TODO 方法功能描述
     *
     * @return
     * @return Integer
     */
    public double getLeastFifteenAverageSales()
    {
        int leastFifteenAverageSales = leastFifteenSales == null ? 0 : leastFifteenSales;
        return CommonUtils.scaleTwo(leastFifteenAverageSales / 15.0);
    }

    public double getSuggestFifteenAverageSales()
    {
        int suggestFifteenAverageSales = suggestFifteenSales == null ? 0 : suggestFifteenSales;
        return CommonUtils.scaleTwo(suggestFifteenAverageSales / 15.0);
    }

    public void setLeastFifteenSales(Integer leastFifteenSales)
    {
        this.leastFifteenSales = leastFifteenSales;
    }

    public Integer getLeastThirtySales()
    {
        return leastThirtySales;
    }

    public Integer getLeastSixMonthSales()
    {
        return leastSixMonthSales;
    }

    public void setLeastSixMonthSales(Integer leastSixMonthSales)
    {
        this.leastSixMonthSales = leastSixMonthSales;
    }

    public Integer getHistorySales()
    {
        return historySales;
    }

    public void setHistorySales(Integer historySales)
    {
        this.historySales = historySales;
    }

    public Integer getBefore1thDay()
    {
        return before1thDay;
    }

    public void setBefore1thDay(Integer before1thDay)
    {
        this.before1thDay = before1thDay;
    }

    public Integer getBefore2thDay()
    {
        return before2thDay;
    }

    public void setBefore2thDay(Integer before2thDay)
    {
        this.before2thDay = before2thDay;
    }

    public Integer getBefore3thDay()
    {
        return before3thDay;
    }

    public void setBefore3thDay(Integer before3thDay)
    {
        this.before3thDay = before3thDay;
    }

    public Integer getBefore4thDay()
    {
        return before4thDay;
    }

    public void setBefore4thDay(Integer before4thDay)
    {
        this.before4thDay = before4thDay;
    }

    public Integer getBefore5thDay()
    {
        return before5thDay;
    }

    public void setBefore5thDay(Integer before5thDay)
    {
        this.before5thDay = before5thDay;
    }

    public Integer getBefore6thDay()
    {
        return before6thDay;
    }

    public void setBefore6thDay(Integer before6thDay)
    {
        this.before6thDay = before6thDay;
    }

    public Integer getBefore7thDay()
    {
        return before7thDay;
    }

    public void setBefore7thDay(Integer before7thDay)
    {
        this.before7thDay = before7thDay;
    }

    /**
     * 30天平均销量
     *
     * <p>TODO 方法功能描述
     *
     * @return
     * @return Integer
     */
    public double getLeastThirtyAverageSales()
    {
        int leastThirtyAverageSales = leastThirtySales == null ? 0 : leastThirtySales;
        return CommonUtils.scaleTwo(leastThirtyAverageSales / 30.0);
    }

    public double getSuggestThirtyAverageSales()
    {
        int suggestThirtyAverageSales = suggestThirtySales == null ? 0 : suggestThirtySales;
        return CommonUtils.scaleTwo(suggestThirtyAverageSales / 30.0);
    }

    public void setLeastThirtySales(Integer leastThirtySales)
    {
        this.leastThirtySales = leastThirtySales;
    }

    public Timestamp getLastUpdateDate()
    {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Timestamp lastUpdateDate)
    {
        this.lastUpdateDate = lastUpdateDate;
    }


    public String getBuyerPostCode()
    {
        return buyerPostCode;
    }

    public void setBuyerPostCode(String buyerPostCode)
    {
        this.buyerPostCode = buyerPostCode;
    }

    public String getWarehouseName()
    {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName)
    {
        this.warehouseName = warehouseName;
    }

    public Integer getSuggestThreeSales()
    {
        return suggestThreeSales;
    }

    public void setSuggestThreeSales(Integer suggestThreeSales)
    {
        this.suggestThreeSales = suggestThreeSales;
    }

    public Integer getSuggestSevenSales()
    {
        return suggestSevenSales;
    }

    public void setSuggestSevenSales(Integer suggestSevenSales)
    {
        this.suggestSevenSales = suggestSevenSales;
    }

    public Integer getSuggestFifteenSales()
    {
        return suggestFifteenSales;
    }

    public void setSuggestFifteenSales(Integer suggestFifteenSales)
    {
        this.suggestFifteenSales = suggestFifteenSales;
    }

    public Integer getSuggestThirtySales()
    {
        return suggestThirtySales;
    }

    public void setSuggestThirtySales(Integer suggestThirtySales)
    {
        this.suggestThirtySales = suggestThirtySales;
    }

    public Double getNewSuggestSales()
    {
        return newSuggestSales;
    }

    public void setNewSuggestSales(Double newSuggestSales)
    {
        this.newSuggestSales = newSuggestSales;
    }

    public Integer getOldLeastSevenSales()
    {
        return oldLeastSevenSales;
    }

    public void setOldLeastSevenSales(Integer oldLeastSevenSales)
    {
        this.oldLeastSevenSales = oldLeastSevenSales;
    }

    public String getSupplierNo()
    {
        return supplierNo;
    }

    public void setSupplierNo(String supplierNo)
    {
        this.supplierNo = supplierNo;
    }

    public Timestamp getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate)
    {
        this.creationDate = creationDate;
    }

    public Integer getLeastSixtySales()
    {
        return leastSixtySales;
    }

    public void setLeastSixtySales(Integer leastSixtySales)
    {
        this.leastSixtySales = leastSixtySales;
    }

    public boolean isImitateAvgSalesFlag()
    {
        return imitateAvgSalesFlag;
    }

    public void setImitateAvgSalesFlag(boolean imitateAvgSalesFlag)
    {
        this.imitateAvgSalesFlag = imitateAvgSalesFlag;
    }

    public Integer getLeastTwoSales()
    {
        return leastTwoSales;
    }

    public void setLeastTwoSales(Integer leastTwoSales)
    {
        this.leastTwoSales = leastTwoSales;
    }


    public Integer getStockout()
    {
        return stockout;
    }

    public void setStockout(Integer stockout)
    {
        this.stockout = stockout;
    }

    public Double getEstimateDailySales()
    {
        return estimateDailySales;
    }

    public void setEstimateDailySales(Double estimateDailySales)
    {
        this.estimateDailySales = estimateDailySales;
    }

    public Integer getStockDay()
    {
        return stockDay;
    }

    public void setStockDay(Integer stockDay)
    {
        this.stockDay = stockDay;
    }

    public Integer getLeastNinetySales()
    {
        return leastNinetySales;
    }

    public void setLeastNinetySales(Integer leastNinetySales)
    {
        this.leastNinetySales = leastNinetySales;
    }

    @Override
    public String toString()
    {
        return "Sales [sku=" + sku + ", yesterdaySales=" + yesterdaySales + ", leastThreeSales=" + leastThreeSales + ", leastSevenSales=" + leastSevenSales + ", leastFifteenSales=" + leastFifteenSales + ", leastThirtySales=" + leastThirtySales + "]";
    }

    /**
     * 半年平均销量
     *
     * @return
     */
    public double getLeastSixMonthAverageSales()
    {
        int leastSixMonthAverageSales = leastSixMonthSales == null ? 0 : leastSixMonthSales;
        return CommonUtils.scaleTwo(leastSixMonthAverageSales / 180.0);
    }

    public Sales(Integer suggestThirtySales, Integer suggestFifteenSales, Integer suggestSevenSales, Integer suggestThreeSales)
    {
        this.suggestThirtySales = suggestThirtySales;
        this.suggestFifteenSales = suggestFifteenSales;
        this.suggestSevenSales = suggestSevenSales;
        this.suggestThreeSales = suggestThreeSales;
    }
}