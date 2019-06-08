package supermarket.discount.calculator.promotions;


import java.math.BigDecimal;
import java.util.List;


public interface Promotion
{
    public BigDecimal applyPromotion(List theBasketList);
}
