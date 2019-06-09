package supermarket.discount.calculator.promotions;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import supermarket.discount.calculator.cart.ShoppingCartItem;
import supermarket.discount.calculator.products.Category;
import supermarket.discount.calculator.products.Product;
import supermarket.discount.calculator.promotions.matcher.PromotionMatch;

import com.google.common.collect.Sets;


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
        validateAmounts(originalAmount, newAmount);
        BuyAndGetSomeFreePromotion result = new BuyAndGetSomeFreePromotion(originalAmount,
                                                                           product,
                                                                           category,
                                                                           newAmount);
        LOG.debug("Result : " + result);
        return result;
    }


    private static void validateAmounts(int originalAmount, int newAmount)
    {
        if (newAmount > originalAmount)
        {
            throw new IllegalArgumentException("New amount is less than original amount");
        }
    }
    
    // TODO looks handy, might be extracted in a separate class for other promotions to be used.
    private static int compareItems(ShoppingCartItem item1, ShoppingCartItem item2) {
        return item1.getCost().compareTo(item2.getCost());
    }

    private final int originalAmount;

    private final Product product;

    private final Category category;

    private final int newAmount;


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
    public List<PromotionMatch> applyPromotion(List<ShoppingCartItem> cart)
    {
        // get all items that are part of this promotion - for example, Cokes or SOFT_DRINKs
        Set<ShoppingCartItem> applicableItems = cart.stream()
                                                    .filter(item -> item.getProduct().equals(product)
                                                                    || item.getProduct()
                                                                           .getCategory()
                                                                           .equals(category))
                                                    .collect(Collectors.toSet());
        // generate all combinations of those items of order X = originalAmount
        Set<Set<ShoppingCartItem>> combinations = Sets.combinations(applicableItems, originalAmount);
        List<PromotionMatch> result = combinations.stream()
                                                  .map(this::createMatch)
                                                  .collect(Collectors.toList());
        return result;
    }


    private PromotionMatch createMatch(Set<ShoppingCartItem> combination)
    {
        // for each combination, the customer gets the cheapest Y = newAmount items for free.
        // This is the money saved value of the promotion.
        // The free items cannot be used in other promotions
        List<ShoppingCartItem> items = new ArrayList<ShoppingCartItem>(combination);
        items.sort(BuyAndGetSomeFreePromotion::compareItems);
        BigDecimal moneySaved = BigDecimal.ZERO;
        for (int i = 0; i < newAmount; i++) {
            moneySaved = moneySaved.add(items.get(i).getCost());
        }
        
        return new PromotionMatch(this, moneySaved, items);
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
}
