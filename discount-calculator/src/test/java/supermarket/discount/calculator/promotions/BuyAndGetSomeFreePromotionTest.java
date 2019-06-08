package supermarket.discount.calculator.promotions;

import org.junit.Assert;
import org.junit.Test;

import supermarket.discount.calculator.products.Category;
import supermarket.discount.calculator.products.Product;
import supermarket.discount.calculator.promotions.BuyAndGetSomeFreePromotion;


public class BuyAndGetSomeFreePromotionTest
{
    @Test
    public void testParseProductPromotion() {
        BuyAndGetSomeFreePromotion promotion = BuyAndGetSomeFreePromotion.fromString("2 @Coke@ for the price of 1");
        Assert.assertNull(promotion.getCategory());
        Assert.assertEquals(Product.fromString("Coke"), promotion.getProduct());
        Assert.assertEquals(2, promotion.getOriginalAmount());
        Assert.assertEquals(1, promotion.getNewAmount());
    }
    
    @Test
    public void testParseCategoryPromotion() {
        BuyAndGetSomeFreePromotion promotion = BuyAndGetSomeFreePromotion.fromString("2 #SOFT_DRINK# for the price of 1");
        Assert.assertNull(promotion.getProduct());
        Assert.assertEquals(Category.SOFT_DRINK, promotion.getCategory());
        Assert.assertEquals(2, promotion.getOriginalAmount());
        Assert.assertEquals(1, promotion.getNewAmount());
    }
    
    @Test(expected=IllegalStateException.class)
    public void testFailOnNegativeAmount() {
       BuyAndGetSomeFreePromotion.fromString("-2 #SOFT_DRINK# for the price of 1");
    }
    
    @Test(expected=IllegalStateException.class)
    public void testFailOnFractionalAmount() {
       BuyAndGetSomeFreePromotion.fromString("2.5 #SOFT_DRINK# for the price of 1");
    }
}
