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
        List<PromotionCombination> allCombinations = generateAllCombinations(possibleMatches);
        Collections.sort(allCombinations, Collections.reverseOrder());
        for (PromotionCombination group : allCombinations)
        {
            if (!isItemUsedMoreThanOnce(group))
            {
                // the promotion combination that saves the customer the most money
                // and does not include an item participating in 2 promotions at the
                // same time is the one we want.
                return group.promotions;
            }
        }

        throw new IllegalStateException("There should be at least 1 combination of promotions where an item is not used twice");
    }


    private boolean isItemUsedMoreThanOnce(PromotionCombination group)
    {
        Set<ShoppingCartItem> usedItems = new HashSet<>();
        for (PromotionMatch match : group.promotions)
        {
            for (ShoppingCartItem item : match.getItems())
            {
                if (!usedItems.add(item))
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Generates all possible combinations of promotions.
     * For example, if you have promotions 1,2 and 3, this method will generate:
     * [1], [1,2], [1,2,3], [2], [2,3], [3]
     * @param possibleMatches
     * @return
     */
    private List<PromotionCombination> generateAllCombinations(List<PromotionMatch> possibleMatches)
    {
        PromotionMatch[] matchesAsArray = new PromotionMatch[possibleMatches.size()];
        matchesAsArray = possibleMatches.toArray(matchesAsArray);
        List<PromotionCombination> result = new ArrayList<>();
        for (int i = 0; i < matchesAsArray.length; i++) {
            List<PromotionMatch> current = new ArrayList<>();
            for (int j = i; j < matchesAsArray.length; j++) {
                current.add(matchesAsArray[j]);
                result.add(new PromotionCombination(new ArrayList<>(current)));
            }
        }
        
        return result;
    }

    private static final class PromotionCombination
        implements Comparable<PromotionCombination>
    {

        private final List<PromotionMatch> promotions;

        private final BigDecimal totalMoneySaved;


        private PromotionCombination(List<PromotionMatch> promotions)
        {
            super();
            this.promotions = promotions;
            this.totalMoneySaved = promotions.stream()
                                             .map(PromotionMatch::getMoneySaved)
                                             .reduce(BigDecimal.ZERO, BigDecimal::add);
        }


        @Override
        public int compareTo(PromotionCombination o)
        {
            return totalMoneySaved.compareTo(o.totalMoneySaved);
        }
    }
}
