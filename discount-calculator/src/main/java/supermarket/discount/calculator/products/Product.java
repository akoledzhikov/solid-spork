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


    @Override
    public String toString()
    {
        return "Product [name=" + name + ", category=" + category + ", pricePerUnit=" + pricePerUnit
               + ", soldWholeUnit=" + soldWholeUnit + "]";
    }


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((category == null) ? 0 : category.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((pricePerUnit == null) ? 0 : pricePerUnit.hashCode());
        result = prime * result + (soldWholeUnit ? 1231 : 1237);
        return result;
    }


    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product other = (Product)obj;
        if (category != other.category)
            return false;
        if (name == null)
        {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        if (pricePerUnit == null)
        {
            if (other.pricePerUnit != null)
                return false;
        }
        else if (!pricePerUnit.equals(other.pricePerUnit))
            return false;
        if (soldWholeUnit != other.soldWholeUnit)
            return false;
        return true;
    }
}
