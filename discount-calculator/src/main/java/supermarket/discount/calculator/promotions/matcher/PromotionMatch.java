package supermarket.discount.calculator.promotions.matcher;


import java.math.BigDecimal;
import java.util.List;

import supermarket.discount.calculator.cart.ShoppingCartItem;
import supermarket.discount.calculator.promotions.Promotion;


public class PromotionMatch
{
    private final Promotion promotion;

    private final BigDecimal moneySaved;

    private final List<ShoppingCartItem> items;


    public PromotionMatch(Promotion promotion, BigDecimal moneySaved, List<ShoppingCartItem> items)
    {
        super();
        this.promotion = promotion;
        this.moneySaved = moneySaved;
        this.items = items;
    }


    public Promotion getPromotion()
    {
        return promotion;
    }


    public BigDecimal getMoneySaved()
    {
        return moneySaved;
    }


    public List<ShoppingCartItem> getItems()
    {
        return items;
    }


    @Override
    public String toString()
    {
        return "PromotionMatch [promotion=" + promotion + ", moneySaved=" + moneySaved + ", items=" + items
               + "]";
    }
}
