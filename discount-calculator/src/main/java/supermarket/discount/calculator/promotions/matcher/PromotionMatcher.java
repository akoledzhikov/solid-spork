package supermarket.discount.calculator.promotions.matcher;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import supermarket.discount.calculator.cart.ShoppingCartItem;
import supermarket.discount.calculator.promotions.Promotion;
import supermarket.discount.calculator.promotions.loader.PromotionsLoader;


public class PromotionMatcher
{
    private final PromotionsLoader promoLoader;


    public PromotionMatcher(PromotionsLoader promoLoader)
    {
        super();
        this.promoLoader = promoLoader;
    }


    public List<PromotionMatch> findBestPromotions(List<ShoppingCartItem> cart)
    {
        Set<Promotion> promotions = promoLoader.getPromotions();
        List<PromotionMatch> possibleMatches = new ArrayList<>();
        for (Promotion p : promotions)
        {
            possibleMatches.addAll(p.applyPromotion(cart));
        }

        List<PromotionMatch> result = filterOutCollidingPromotions(possibleMatches);
        return result;
    }


    private List<PromotionMatch> filterOutCollidingPromotions(List<PromotionMatch> possibleMatches)
    {
        List<PromotionGroup> allCombinations = generateAllCombinations(possibleMatches);
        Collections.sort(allCombinations);
        for (PromotionGroup group : allCombinations)
        {
            if (!isItemUsedMoreThanOnce(group))
            {
                return group.promotions;
            }
        }

        throw new IllegalStateException("There should be at least 1 combination of promotions where an item is not used twice");
    }


    private boolean isItemUsedMoreThanOnce(PromotionGroup group)
    {
        Set<ShoppingCartItem> usedItems = new HashSet<>();
        return false;
    }


    private List<PromotionGroup> generateAllCombinations(List<PromotionMatch> possibleMatches)
    {
        // TODO Auto-generated method stub
        return null;
    }

    private static final class PromotionGroup
        implements Comparable<PromotionGroup>
    {

        private final List<PromotionMatch> promotions;

        private final BigDecimal totalMoneySaved;


        private PromotionGroup(List<PromotionMatch> promotions)
        {
            super();
            this.promotions = promotions;
            this.totalMoneySaved = promotions.stream()
                                             .map(PromotionMatch::getMoneySaved)
                                             .reduce(BigDecimal.ZERO, BigDecimal::add);
        }


        @Override
        public int compareTo(PromotionGroup o)
        {
            return totalMoneySaved.compareTo(o.totalMoneySaved);
        }
    }
}
