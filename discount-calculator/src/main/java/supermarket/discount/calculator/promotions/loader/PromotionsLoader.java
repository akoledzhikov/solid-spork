package supermarket.discount.calculator.promotions.loader;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import supermarket.discount.calculator.promotions.Promotion;


/**
 * Loads promotions from a file. Not thread safe!
 * 
 * @author user
 */
public class PromotionsLoader
{
    private final String promotionsFileLocation;

    private Set<Promotion> promotions;


    public PromotionsLoader(String promotionsFileLocation)
    {
        super();
        this.promotionsFileLocation = promotionsFileLocation;
    }


    public Set<Promotion> getPromotions()
    {
        if (promotions == null)
        {
            promotions = loadPromotions();
        }

        return promotions;
    }


    public void refreshPromotions()
    {
        promotions = null;
    }


    private Set<Promotion> loadPromotions()
    {
        try (Stream<String> stream = Files.lines(Paths.get(promotionsFileLocation)))
        {
            Set<Promotion> result = stream.filter(line -> !line.startsWith("#"))
                                          .map(PromotionFactory::promotionFromString)
                                          .collect(Collectors.toSet());
            result = Collections.unmodifiableSet(result);
            return result;
        }
        catch (IOException e)
        {
            throw new PromotionLoadException("Exception when loading promotions from file "
                                             + promotionsFileLocation, e);
        }
    }
}
