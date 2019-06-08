package supermarket.discount.calculator.promotions.loader;


import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.regex.Pattern;

import supermarket.discount.calculator.promotions.BuyAndGetSomeFreePromotion;
import supermarket.discount.calculator.promotions.Promotion;


class PromotionFactory
{
    private static final Map<String, Function<String, Promotion>> PATTERN_TO_PROMOTION = new HashMap<>();

    static
    {
        PATTERN_TO_PROMOTION.put(BuyAndGetSomeFreePromotion.getPattern(),
                                 BuyAndGetSomeFreePromotion::fromString);
    }


    static Promotion promotionFromString(String promotionAsString)
    {
        for (Entry<String, Function<String, Promotion>> entry : PATTERN_TO_PROMOTION.entrySet())
        {
            // TODO this can be made more economical - keep a pre-compiled pattern in the map,
            // pass the matcher object directly to the function call
            Pattern pattern = Pattern.compile(entry.getKey());
            if (pattern.matcher(promotionAsString).matches())
            {
                return entry.getValue().apply(promotionAsString);
            }
        }

        throw new PromotionLoadException("Promotion does not match any of the available patterns: "
                                        + promotionAsString);
    }
}
