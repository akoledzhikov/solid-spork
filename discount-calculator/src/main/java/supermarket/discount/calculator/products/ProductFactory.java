package supermarket.discount.calculator.products;


import java.math.BigDecimal;

import org.apache.log4j.Logger;


public class ProductFactory
{
    private static final Logger LOG = Logger.getLogger(ProductFactory.class);


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

        String name = split[0].trim();
        Category category = Category.valueOf(split[1].trim());
        BigDecimal pricePerUnit = new BigDecimal(split[2].trim());
        boolean soldWholeUnit = Boolean.valueOf(split[3].trim());

        Product result = new Product(name, category, pricePerUnit, soldWholeUnit);
        LOG.debug("result : " + productAsString);
        return result;
    }
}
