package supermarket.discount.calculator.cart;


import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import supermarket.discount.calculator.products.Product;
import supermarket.discount.calculator.products.ProductsLoader;


public class ShoppingCartParserTest
{
    @Test
    public void testSuccessfulParse()
        throws IOException
    {
        List<String> cartAsString = fileToList("src/test/resources/ShoppingCartParserTest/validShoppingCart.cart");
        List<ShoppingCartItem> items = ShoppingCartParser.parse(cartAsString);
        Assert.assertEquals(3, items.size());
        ShoppingCartItem theSandwich = items.get(2);
        ProductsLoader pl = new ProductsLoader();
        Product sandwichProduct = pl.getProduct("Ham Sandwich");
        Assert.assertEquals(sandwichProduct, theSandwich.getProduct());
        Assert.assertEquals(2, theSandwich.getUnits().intValue());
    }


    @Test
    public void testUnitsRounding()
        throws IOException
    {
        List<String> cartAsString = fileToList("src/test/resources/ShoppingCartParserTest/unitRounding.cart");
        List<ShoppingCartItem> items = ShoppingCartParser.parse(cartAsString);
        ShoppingCartItem oranges = items.get(0);
        BigDecimal units = oranges.getUnits();
        Assert.assertEquals(new BigDecimal("3.15"), units);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testMissingProduct()
        throws IOException
    {
        List<String> cartAsString = fileToList("src/test/resources/ShoppingCartParserTest/missingProduct.cart");
        ShoppingCartParser.parse(cartAsString);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testBuyingFractionalPepsi() throws IOException
    {
        List<String> cartAsString = fileToList("src/test/resources/ShoppingCartParserTest/fractionalPepsi.cart");
        ShoppingCartParser.parse(cartAsString);
    }


    private List<String> fileToList(String fileLocation)
        throws IOException
    {
        List<String> list = Files.readAllLines(new File(fileLocation).toPath(), Charset.defaultCharset());
        return list;
    }
}
