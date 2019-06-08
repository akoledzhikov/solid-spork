package supermarket.discount.calculator.promotions;


import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import supermarket.discount.calculator.products.Category;
import supermarket.discount.calculator.products.Product;


public class BuyAndGetSomeFreePromotion
    implements Promotion
{
    private static final Logger LOG = Logger.getLogger(BuyAndGetSomeFreePromotion.class);

    private static final String PATTERN = "(?<originalAmount>[1-9][0-9]*) (@(?<product>\\w+)@"
                                          + "|#(?<category>\\w+)#) for the price of (?<newAmount>[1-9][0-9]*)";


    public static String getPattern()
    {
        return PATTERN;
    }


    public static BuyAndGetSomeFreePromotion fromString(String promotionAsString)
    {
        LOG.debug("Parsing : " + promotionAsString);
        Pattern p = Pattern.compile(PATTERN);
        Matcher m = p.matcher(promotionAsString);
        m.matches();
        int originalAmount = Integer.valueOf(m.group("originalAmount"));
        Product product = Product.fromString(m.group("product"));
        Category category = Category.fromString(m.group("category"));
        int newAmount = Integer.valueOf(m.group("newAmount"));
        BuyAndGetSomeFreePromotion result = new BuyAndGetSomeFreePromotion(originalAmount,
                                                                           product,
                                                                           category,
                                                                           newAmount);
        LOG.debug("Result : " + result);
        return result;
    }

    private int originalAmount;

    private Product product;

    private Category category;

    private int newAmount;


    private BuyAndGetSomeFreePromotion(int originalAmount, Product product, Category category, int newAmount)
    {
        super();
        this.originalAmount = originalAmount;
        this.product = product;
        this.category = category;
        this.newAmount = newAmount;
    }


    public int getOriginalAmount()
    {
        return originalAmount;
    }


    public Product getProduct()
    {
        return product;
    }


    public Category getCategory()
    {
        return category;
    }


    public int getNewAmount()
    {
        return newAmount;
    }


    @Override
    public BigDecimal applyPromotion(List theBasketList)
    {
        return null;
    }


    @Override
    public String toString()
    {
        return "BuyAndGetSomeFreePromotion [originalAmount=" + originalAmount + ", product=" + product
               + ", category=" + category + ", newAmount=" + newAmount + "]";
    }


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((category == null) ? 0 : category.hashCode());
        result = prime * result + newAmount;
        result = prime * result + originalAmount;
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
        BuyAndGetSomeFreePromotion other = (BuyAndGetSomeFreePromotion)obj;
        if (category != other.category)
            return false;
        if (newAmount != other.newAmount)
            return false;
        if (originalAmount != other.originalAmount)
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


    public static void main(String[] args)
    {
        Pattern p = Pattern.compile(PATTERN);
        Matcher matcher = p.matcher("2 @Coke@ for the price of 1");
        System.out.println(matcher.matches());
        System.out.println(matcher.group("originalAmount"));
        // System.out.println(matcher.group(2));
        System.out.println(matcher.group("product"));
        System.out.println(matcher.group("category"));
        System.out.println(matcher.group("newAmount"));
        fromString("2 @Coke@ for the price of 1");
    }
}
