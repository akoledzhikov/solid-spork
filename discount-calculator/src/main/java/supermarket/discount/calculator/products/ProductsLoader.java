package supermarket.discount.calculator.products;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import supermarket.discount.calculator.promotions.loader.PromotionLoadException;


public class ProductsLoader
{
    private String productsFileLocation = System.getProperty("products.file.location",
                                                             "src/main/resources/defaultProducts.prod");

    private Map<String, Product> products;


    public ProductsLoader()
    {
        super();
    }


    public ProductsLoader(String productsFileLocation)
    {
        super();
        this.productsFileLocation = productsFileLocation;
    }


    public Product getProduct(String name)
    {
        if (products == null)
        {
            products = loadProducts();
        }

        return products.get(name);
    }


    public void refreshProducts()
    {
        products = null;
    }


    private Map<String, Product> loadProducts()
    {
        try (Stream<String> stream = Files.lines(Paths.get(productsFileLocation)))
        {
            Map<String, Product> result = stream.filter(line -> !line.startsWith("#"))
                                                .map(ProductFactory::productFromString)
                                                .collect(Collectors.toMap(x -> x.getName(), x -> x));
            return result;
        }
        catch (IOException e)
        {
            throw new PromotionLoadException("Exception when loading products from file "
                                             + productsFileLocation, e);
        }
    }
}
