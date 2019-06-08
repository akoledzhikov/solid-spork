package supermarket.discount.calculator.products;


import org.junit.Assert;
import org.junit.Test;


public class ProductsLoaderTest
{
    @Test
    public void testGetCoke()
    {
        ProductsLoader pl = new ProductsLoader();
        Product product = pl.getProduct("Coke");
        Assert.assertEquals("Coke", product.getName());
        Assert.assertEquals(Category.SOFT_DRINK, product.getCategory());
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testMissingProperty() {
        ProductsLoader pl = new ProductsLoader("src/test/resources/PercentileDiscountPromotionTest/products.prod");
        pl.getProduct("Pepsi");
    }
}
