package supermarket.discount.calculator.products;


import java.math.BigDecimal;


public class Product
{
    private static final ProductsLoader pl = new ProductsLoader();


    public static Product fromString(String productAsString)
    {
        return pl.getProduct(productAsString);
    }

    private final String name;

    private final Category category;

    private final BigDecimal pricePerUnit;

    private final boolean soldWholeUnit;


    public Product(String name, Category category, BigDecimal pricePerUnit, boolean soldWholeUnit)
    {
        super();
        this.name = name;
        this.category = category;
        this.pricePerUnit = pricePerUnit;
        this.soldWholeUnit = soldWholeUnit;
    }


    public String getName()
    {
        return name;
    }


    public Category getCategory()
    {
        return category;
    }


    public BigDecimal getPricePerUnit()
    {
        return pricePerUnit;
    }


    public boolean isSoldWholeUnit()
    {
        return soldWholeUnit;
    }
}
