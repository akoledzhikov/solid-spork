package supermarket.discount.calculator.products;


import java.math.BigDecimal;


public class Product
{
    public static Product fromString(String productAsString)
    {
        return null;
    }

    private String name;

    private Category category;

    private BigDecimal pricePerUnit;

    private boolean soldWholeUnit = true;


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
