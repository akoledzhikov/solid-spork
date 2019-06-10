package supermarket.discount.calculator;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import supermarket.discount.calculator.cart.ShoppingCartItem;
import supermarket.discount.calculator.cart.ShoppingCartParser;
import supermarket.discount.calculator.promotions.loader.PromotionsLoader;
import supermarket.discount.calculator.promotions.matcher.PromotionMatch;
import supermarket.discount.calculator.promotions.matcher.PromotionMatcher;


/**
 * Hello world!
 */
public class App
{
    public static void main(String[] args)
    {
        System.out.println("Please enter your purchases.");
        System.out.println("Type DONE when ready.");

        try (Scanner in = new Scanner(System.in))
        {
            String line = "";
            List<String> lines = new ArrayList<>();
            while (true)
            {
                line = in.nextLine();
                if ("DONE".equals(line.trim()))
                {
                    break;
                }
                
                lines.add(line);
            }

            List<ShoppingCartItem> cart = ShoppingCartParser.parse(lines);
            PromotionsLoader pl = new PromotionsLoader("src/main/resources/defaultPromotions.prom");
            PromotionMatcher promotionMatcher = new PromotionMatcher(pl);
            List<PromotionMatch> promotions = promotionMatcher.findBestPromotions(cart);
            System.out.println(promotions);
        }
    }
}
