package supermarket.discount.calculator.promotions;


import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import supermarket.discount.calculator.products.Category;
import supermarket.discount.calculator.products.Product;


public class PercentileDiscountPromotion
    implements Promotion
{
    private static final Logger LOG = Logger.getLogger(PercentileDiscountPromotion.class);

    private static final String PATTERN = "(@(?<product>\\w+)@"
                                          + "|#(?<category>\\w+)#) discount (?<discount>[1-9][0-9]*)%";


    public static String getPattern()
    {
        return PATTERN;
    }


    public static PercentileDiscountPromotion fromString(String promotionAsString)
    {
        // TODO maybe make Promotion abstract class and move all common parsing
        // functionality there?
        LOG.debug("Parsing : " + promotionAsString);
        Pattern p = Pattern.compile(PATTERN);
        Matcher m = p.matcher(promotionAsString);
        m.matches();
        Product product = Product.fromString(m.group("product"));
        Category category = Category.fromString(m.group("category"));
        int discountPercent = Integer.valueOf(m.group("discount"));
        validateDiscountPercent(discountPercent);
        PercentileDiscountPromotion result = new PercentileDiscountPromotion(product,
                                                                             category,
                                                                             discountPercent);
        LOG.debug("Result : " + result);
        return result;
    }


    private static void validateDiscountPercent(int discountPercent)
    {
        if (discountPercent >= 100)
        {
            throw new IllegalArgumentException("Discount cannot be 100% or more!");
        }
    }

    private Product product;

    private Category category;

    private int discountPercent;


    public PercentileDiscountPromotion(Product product, Category category, int discountPercent)
    {
        super();
        this.product = product;
        this.category = category;
        this.discountPercent = discountPercent;
    }


    public Product getProduct()
    {
        return product;
    }


    public Category getCategory()
    {
        return category;
    }


    public int getDiscountPercent()
    {
        return discountPercent;
    }


    @Override
    public BigDecimal applyPromotion(List theBasketList)
    {
        return null;
    }


    @Override
    public String toString()
    {
        return "PercentileDiscountPromotion [product=" + product + ", category=" + category
               + ", discountPercent=" + discountPercent + "]";
    }


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((category == null) ? 0 : category.hashCode());
        result = prime * result + discountPercent;
        result = prime * result + ((product == null) ? 0 : product.hashCode());
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
        PercentileDiscountPromotion other = (PercentileDiscountPromotion)obj;
        if (category != other.category)
            return false;
        if (discountPercent != other.discountPercent)
            return false;
        if (product == null)
        {
            if (other.product != null)
                return false;
        }
        else if (!product.equals(other.product))
            return false;
        return true;
    }
}
