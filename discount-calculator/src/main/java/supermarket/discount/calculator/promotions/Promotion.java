package supermarket.discount.calculator.promotions;


import java.util.List;

import supermarket.discount.calculator.cart.ShoppingCartItem;
import supermarket.discount.calculator.promotions.matcher.PromotionMatch;


public interface Promotion
{
    public List<PromotionMatch> applyPromotion(List<ShoppingCartItem> cart);
}
