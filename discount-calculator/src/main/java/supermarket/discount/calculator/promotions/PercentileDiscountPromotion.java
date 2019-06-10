package supermarket.discount.calculator.promotions;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import supermarket.discount.calculator.cart.ShoppingCartItem;
import supermarket.discount.calculator.products.Category;
import supermarket.discount.calculator.products.Product;
import supermarket.discount.calculator.promotions.matcher.PromotionMatch;


public class PercentileDiscountPromotion
    implements Promotion
{
    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

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
        BigDecimal discountPercent = new BigDecimal(m.group("discount"));
        validateDiscountPercent(discountPercent);
        discountPercent = discountPercent.divide(ONE_HUNDRED, 2, RoundingMode.DOWN);
        PercentileDiscountPromotion result = new PercentileDiscountPromotion(product,
                                                                             category,
                                                                             discountPercent);
        LOG.debug("Result : " + result);
        return result;
    }


    private static void validateDiscountPercent(BigDecimal discountPercent)
    {
        if (discountPercent.intValueExact() >= 100)
        {
            throw new IllegalArgumentException("Discount cannot be 100% or more!");
        }
    }

    private final Product product;

    private final Category category;

    private final BigDecimal discountPercent;


    public PercentileDiscountPromotion(Product product, Category category, BigDecimal discountPercent)
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


    public BigDecimal getDiscountPercent()
    {
        return discountPercent;
    }


    @Override
    public List<PromotionMatch> applyPromotion(List<ShoppingCartItem> cart)
    {
        // get all items that are part of this promotion - for example, Cokes or SOFT_DRINKs
        List<PromotionMatch> result = cart.stream()
                                          .filter(item -> item.getProduct().equals(product)
                                                          || item.getProduct().getCategory().equals(category))
                                          .map(this::toMatch)
                                          .collect(Collectors.toList());

        return result;
    }


    private PromotionMatch toMatch(ShoppingCartItem item)
    {
        BigDecimal moneySaved = item.getCost().multiply(discountPercent).stripTrailingZeros();
        return new PromotionMatch(this, moneySaved, Collections.singletonList(item));
    }


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((category == null) ? 0 : category.hashCode());
        result = prime * result + ((discountPercent == null) ? 0 : discountPercent.hashCode());
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
        if (discountPercent == null)
        {
            if (other.discountPercent != null)
                return false;
        }
        else if (!discountPercent.equals(other.discountPercent))
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


    @Override
    public String toString()
    {
        return "PercentileDiscountPromotion [product=" + product + ", category=" + category
               + ", discountPercent=" + discountPercent + "]";
    }
}
