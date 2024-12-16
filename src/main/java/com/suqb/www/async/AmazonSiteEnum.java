package com.suqb.www.async;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum AmazonSiteEnum
{
    BR("A2Q3Y263D00KWC", "巴西 BR", "https://mws.amazonservices.com", "https://sellercentral.amazon.com/apps/authorize/consent", "Amazon.br", "pt", "BRL", "NA", "https://sellingpartnerapi-na.amazon.com", "us-east-1", "https://api.amazon.com/auth/o2/token", "https://advertising-api.amazon.com", "as2", "us_footwear_size_system"),
    CA("A2EUQ1WTGCTBG2", "加拿大 CA", "https://mws.amazonservices.ca", "https://sellercentral.amazon.com/apps/authorize/consent", "Amazon.ca", "", "CAD", "NA", "https://sellingpartnerapi-na.amazon.com", "us-east-1", "https://api.amazon.com/auth/o2/token", "https://advertising-api.amazon.com", "as1", "us_footwear_size_system"),
    MX("A1AM78C64UM0Y8", "墨西哥 MX", "https://mws.amazonservices.com.mx", "https://sellercentral.amazon.com/apps/authorize/consent", "Amazon.com.mx", "es", "MXN", "NA", "https://sellingpartnerapi-na.amazon.com", "us-east-1", "https://api.amazon.com/auth/o2/token", "https://advertising-api.amazon.com", "as1", "us_footwear_size_system"),
    US("ATVPDKIKX0DER", "美国 US", "https://mws.amazonservices.com", "https://sellercentral.amazon.com/apps/authorize/consent", "Amazon.com", "", "USD", "NA", "https://sellingpartnerapi-na.amazon.com", "us-east-1", "https://api.amazon.com/auth/o2/token", "https://advertising-api.amazon.com", "as1", "us_footwear_size_system"),
    AE("A2VIGQ35RCS4UG", "阿联酋 AE", "https://mws.amazonservices.ae", "https://sellercentral.amazon.ae/apps/authorize/consent", "Amazon.ae", "ar", "AED", "EU", "https://sellingpartnerapi-eu.amazon.com", "eu-west-1", "https://api.amazon.co.uk/auth/o2/token", "https://advertising-api-eu.amazon.com", "as4", "eu_footwear_size_system"),
    DE("A1PA6795UKMFR9", "德国 DE", "https://mws.amazonservices.de", "https://sellercentral-europe.amazon.com/apps/authorize/consent", "Amazon.de", "de", "EUR", "EU", "https://sellingpartnerapi-eu.amazon.com", "eu-west-1", "https://api.amazon.co.uk/auth/o2/token", "https://advertising-api-eu.amazon.com", "as3", "eu_footwear_size_system"),
    EG("ARBP9OOSHTCHU", "埃及 EG", "https://mws-eu.amazonservices.com", "https://sellercentral-europe.amazon.com/apps/authorize/consent", "Amazon.eg", "ar", "EGP", "EU", "https://sellingpartnerapi-eu.amazon.com", "eu-west-1", "https://api.amazon.co.uk/auth/o2/token", "https://advertising-api-eu.amazon.com", (String) null, "eu_footwear_size_system"),
    ES("A1RKKUPIHCS9HS", "西班牙 ES", "https://mws-eu.amazonservices.com", "https://sellercentral-europe.amazon.com/apps/authorize/consent", "Amazon.es", "es", "EUR", "EU", "https://sellingpartnerapi-eu.amazon.com", "eu-west-1", "https://api.amazon.co.uk/auth/o2/token", "https://advertising-api-eu.amazon.com", "as4", "eu_footwear_size_system"),
    FR("A13V1IB3VIYZZH", "法国 FR", "https://mws.amazonservices.fr", "https://sellercentral-europe.amazon.com/apps/authorize/consent", "Amazon.fr", "fr", "EUR", "EU", "https://sellingpartnerapi-eu.amazon.com", "eu-west-1", "https://api.amazon.co.uk/auth/o2/token", "https://advertising-api-eu.amazon.com", "as4", "eu_footwear_size_system"),
    BE("AMEN7PMS3EDWL", "比利时 BE", "https://mws-eu.amazonservices.com", "https://sellercentral-europe.amazon.com/apps/authorize/consent", "Amazon.com.be", "fr", "EUR", "EU", "https://sellingpartnerapi-eu.amazon.com", "eu-west-1", "https://api.amazon.co.uk/auth/o2/token", "https://advertising-api-eu.amazon.com", (String) null, "eu_footwear_size_system"),
    UK("A1F83G8C2ARO7P", "英国 UK", "https://mws.amazonservices.co.uk", "https://sellercentral-europe.amazon.com/apps/authorize/consent", "Amazon.co.uk", "", "GBP", "EU", "https://sellingpartnerapi-eu.amazon.com", "eu-west-1", "https://api.amazon.co.uk/auth/o2/token", "https://advertising-api-eu.amazon.com", "as8", "uk_footwear_size_system"),
    IN("A21TJRUUN4KGV", "印度 IN", "https://mws.amazonservices.in", "https://sellercentral-europe.amazon.com/apps/authorize/consent", "Amazon.in", "", "INR", "EU", "https://sellingpartnerapi-eu.amazon.com", "eu-west-1", "https://api.amazon.co.uk/auth/o2/token", "https://advertising-api-eu.amazon.com", "as5", "uk_footwear_size_system"),
    IT("APJ6JRA9NG5V4", "意大利 IT", "https://mws.amazonservices.it", "https://sellercentral-europe.amazon.com/apps/authorize/consent", "Amazon.it", "it", "EUR", "EU", "https://sellingpartnerapi-eu.amazon.com", "eu-west-1", "https://api.amazon.co.uk/auth/o2/token", "https://advertising-api-eu.amazon.com", "as6", "eu_footwear_size_system"),
    NL("A1805IZSGTT6HS", "荷兰 NL", "https://mws-eu.amazonservices.com", "https://sellercentral-europe.amazon.com/apps/authorize/consent", "Amazon.nl", "", "EUR", "EU", "https://sellingpartnerapi-eu.amazon.com", "eu-west-1", "https://api.amazon.co.uk/auth/o2/token", "https://advertising-api-eu.amazon.com", "as3", "eu_footwear_size_system"),
    PL("A1C3SOZRARQ6R3", "波兰 PL", "https://mws-eu.amazonservices.com", "https://sellercentral-europe.amazon.com/apps/authorize/consent", "Amazon.pl", "", "PLN", "EU", "https://sellingpartnerapi-eu.amazon.com", "eu-west-1", "https://api.amazon.co.uk/auth/o2/token", "https://advertising-api-eu.amazon.com", "as3", "eu_footwear_size_system"),
    SA("A17E79C6D8DWNP", "沙特阿拉伯 SA", "https://mws-eu.amazonservices.com", "https://sellercentral.amazon.ae/apps/authorize/consent", "Amazon.sa", "ar", "SAR", "SA", "https://sellingpartnerapi-eu.amazon.com", "eu-west-1", "https://api.amazon.co.uk/auth/o2/token", "https://advertising-api-eu.amazon.com", "as4", "eu_footwear_size_system"),
    SE("A2NODRKZP88ZB9", "瑞典 SE", "https://mws-eu.amazonservices.com", "https://sellercentral-europe.amazon.com/apps/authorize/consent", "Amazon.se", "", "SEK", "EU", "https://sellingpartnerapi-eu.amazon.com", "eu-west-1", "https://api.amazon.co.uk/auth/o2/token", "https://advertising-api-eu.amazon.com", "as3", "eu_footwear_size_system"),
    TR("A33AVAJ2PDY3EV", "土耳其 TR", "https://mws-eu.amazonservices.com", "https://sellercentral-europe.amazon.com/apps/authorize/consent", "Amazon.tr", "tr", "TRY", "EU", "https://sellingpartnerapi-eu.amazon.com", "eu-west-1", "https://api.amazon.co.uk/auth/o2/token", "https://advertising-api-eu.amazon.com", (String) null, "eu_footwear_size_system"),
    SG("A19VAU5U5O7RUS", "新加坡 SG", "https://mws-fe.amazonservices.com", "https://sellercentral.amazon.sg/apps/authorize/consent", "Amazon.sg", "", "SGD", "FE", "https://sellingpartnerapi-fe.amazon.com", "us-west-2", "https://api.amazon.co.jp/auth/o2/token", "https://advertising-api-fe.amazon.com", (String) null, "us_footwear_size_system"),
    AU("A39IBJ37TRP1C6", "澳大利亚 AU", "https://mws.amazonservices.com.au", "https://sellercentral.amazon.com.au/apps/authorize/consent", "Amazon.com.au", "", "AUD", "FE", "https://sellingpartnerapi-fe.amazon.com", "us-west-2", "https://api.amazon.co.jp/auth/o2/token", "https://advertising-api-fe.amazon.com", "as1", "us_footwear_size_system"),
    JP("A1VC38T7YXB528", "日本 JP", "https://mws.amazonservices.jp", "https://sellercentral-japan.amazon.com/apps/authorize/consent", "Amazon.co.jp", "ja", "JPY", "FE", "https://sellingpartnerapi-fe.amazon.com", "us-west-2", "https://api.amazon.co.jp/auth/o2/token", "https://advertising-api-fe.amazon.com", "as7", "jp_footwear_size_system");

    private String code;
    private String display;
    private String serviceUrl;
    private String authUrl;
    private String marketplaceName;
    private String language;
    private String currency;
    private String region;
    private String spServiceUrl;
    private String spRegion;
    private String adAuthUrl;
    private String adServiceUrl;
    private String sizeSystem;
    private String footwearSizeSystem;

    private AmazonSiteEnum(String code, String display, String serviceUrl, String authUrl, String marketplaceName, String language, String currency, String region, String spServiceUrl, String spRegion, String adAuthUrl, String adServiceUrl, String sizeSystem, String footwearSizeSystem)
    {
        this.code = code;
        this.display = display;
        this.serviceUrl = serviceUrl;
        this.authUrl = authUrl;
        this.marketplaceName = marketplaceName;
        this.language = language;
        this.currency = currency;
        this.region = region;
        this.spServiceUrl = spServiceUrl;
        this.spRegion = spRegion;
        this.adAuthUrl = adAuthUrl;
        this.adServiceUrl = adServiceUrl;
        this.sizeSystem = sizeSystem;
        this.footwearSizeSystem = footwearSizeSystem;
    }

    public static AmazonSiteEnum build(String code)
    {
        return (AmazonSiteEnum) Arrays.stream(values()).filter((type) ->
        {
            return type.code.equalsIgnoreCase(code);
        }).findAny().orElse((AmazonSiteEnum) null);
    }

    public static AmazonSiteEnum buildByDisplay(String display)
    {
        return (AmazonSiteEnum) Arrays.stream(values()).filter((type) ->
        {
            return type.display.toUpperCase().contains(display.toUpperCase());
        }).findAny().orElse((AmazonSiteEnum) null);
    }

    public static AmazonSiteEnum buildByMarketplaceName(String marketplaceName)
    {
        return (AmazonSiteEnum) Arrays.stream(values()).filter((type) ->
        {
            return type.marketplaceName.equalsIgnoreCase(marketplaceName);
        }).findAny().orElse((AmazonSiteEnum) null);
    }

    public static AmazonSiteEnum buildByCurrency(String currency)
    {
        List<AmazonSiteEnum> types = (List) Arrays.stream(values()).filter((type) ->
        {
            return type.currency.equalsIgnoreCase(currency);
        }).collect(Collectors.toList());
        return types.size() == 1 ? (AmazonSiteEnum) types.get(0) : null;
    }

    public static AmazonSiteEnum match(String match)
    {
        if (match != null && !"".equals(match))
        {
            if ("om".equals(match))
            {
                return US;
            }
            else
            {
                AmazonSiteEnum[] var1 = values();
                int var2 = var1.length;

                int var3;
                AmazonSiteEnum value;
                for (var3 = 0; var3 < var2; ++var3)
                {
                    value = var1[var3];
                    if (value.code.equals(match))
                    {
                        return value;
                    }
                }

                var1 = values();
                var2 = var1.length;

                for (var3 = 0; var3 < var2; ++var3)
                {
                    value = var1[var3];
                    if (value.name().equalsIgnoreCase(match))
                    {
                        return value;
                    }
                }

                var1 = values();
                var2 = var1.length;

                for (var3 = 0; var3 < var2; ++var3)
                {
                    value = var1[var3];
                    if (value.marketplaceName.equalsIgnoreCase(match))
                    {
                        return value;
                    }
                }

                var1 = values();
                var2 = var1.length;

                for (var3 = 0; var3 < var2; ++var3)
                {
                    value = var1[var3];
                    if (value.marketplaceName.endsWith(match))
                    {
                        return value;
                    }
                }

                var1 = values();
                var2 = var1.length;

                for (var3 = 0; var3 < var2; ++var3)
                {
                    value = var1[var3];
                    if (value.currency.equalsIgnoreCase(match))
                    {
                        return value;
                    }
                }

                var1 = values();
                var2 = var1.length;

                for (var3 = 0; var3 < var2; ++var3)
                {
                    value = var1[var3];
                    if (value.display.contains(match))
                    {
                        return value;
                    }
                }

                return null;
            }
        }
        else
        {
            return null;
        }
    }

    public String code()
    {
        return this.code;
    }

    public String display()
    {
        return this.display;
    }

    public String serviceUrl()
    {
        return this.serviceUrl;
    }

    public String authUrl()
    {
        return this.authUrl;
    }

    public String marketplaceName()
    {
        return this.marketplaceName;
    }

    public String language()
    {
        return this.language;
    }

    public String currency()
    {
        return this.currency;
    }

    public String region()
    {
        return this.region;
    }

    public String spServiceUrl()
    {
        return this.spServiceUrl;
    }

    public String spRegion()
    {
        return this.spRegion;
    }

    public String adAuthUrl()
    {
        return this.adAuthUrl;
    }

    public String adServiceUrl()
    {
        return this.adServiceUrl;
    }

    public String sizeSystem()
    {
        return this.sizeSystem;
    }

    public String footwearSizeSystem()
    {
        return this.footwearSizeSystem;
    }
}
