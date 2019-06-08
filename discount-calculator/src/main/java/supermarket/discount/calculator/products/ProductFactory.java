package supermarket.discount.calculator.products;


import java.math.BigDecimal;

import org.apache.log4j.Logger;

import supermarket.discount.calculator.promotions.BuyAndGetSomeFreePromotion;


public class ProductFactory
{
    private static final Logger LOG = Logger.getLogger(BuyAndGetSomeFreePromotion.class);


    // TODO - bad! Cannot be mocked to unit test ProductLoader. Sad :(
    public static Product productFromString(String productAsString)
    {
        LOG.debug("Parsing : " + productAsString);
        String[] split = productAsString.split(",");
        if (split.length != 4)
        {
            // TODO this will break the whole app. Maybe just log an error and continue loading
            // other products?
            throw new IllegalArgumentException("Malformed product definition: " + productAsString);
        }

        String name = split[0];
        Category category = Category.valueOf(split[1]);
        BigDecimal pricePerUnit = new BigDecimal(split[2]);
        boolean soldWholeUnit = Boolean.valueOf(split[3]);

        Product result = new Product(name, category, pricePerUnit, soldWholeUnit);
        LOG.debug("result : " + productAsString);
        return result;
    }
}
