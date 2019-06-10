package supermarket.discount.calculator.promotions;




import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import supermarket.discount.calculator.products.Product;
import supermarket.discount.calculator.promotions.PercentileDiscountPromotion;


public class PercentileDiscountPromotionTest
{
    @Test(expected = IllegalArgumentException.class)
    public void testHundredAndTenPercentDiscount()
    {
        PercentileDiscountPromotion.fromString("@Pepsi@ discount 110%");
    }

    @Test
    public void testProductParse()
    {
        PercentileDiscountPromotion promotion = PercentileDiscountPromotion.fromString("@Pepsi@ discount 50%");
        Assert.assertEquals(Product.fromString("Pepsi"), promotion.getProduct());
        Assert.assertEquals(new BigDecimal("50"), promotion.getDiscountPercent());
    }

}
