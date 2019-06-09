package supermarket.discount.calculator.cart;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import supermarket.discount.calculator.products.Product;
import supermarket.discount.calculator.products.ProductsLoader;


public class ShoppingCartParser
{
    private static final Logger LOG = Logger.getLogger(ShoppingCartParser.class);

    // TODO not very testable.
    private static final ProductsLoader pl = new ProductsLoader();


    public static List<ShoppingCartItem> parse(List<String> cartAsStrings)
    {
        LOG.debug("Parsing : " + String.join(",", cartAsStrings));
        List<ShoppingCartItem> result = new ArrayList<>(cartAsStrings.size());
        for (String itemAsString : cartAsStrings)
        {
            int idxLastSpace = itemAsString.lastIndexOf(" ");
            String productAsString = itemAsString.substring(0, idxLastSpace).trim();
            String unitsAsString = itemAsString.substring(idxLastSpace).trim();

            Product product = pl.getProduct(productAsString);
            if (product == null)
            {
                throw new IllegalArgumentException("Unknown product: " + product);
            }

            // of course, we are not going to round in customer's favor.
            BigDecimal units = new BigDecimal(unitsAsString).setScale(2, RoundingMode.UP);
            if (product.isSoldWholeUnit() && units.stripTrailingZeros().scale() > 0) {
                throw new IllegalArgumentException(product.getName() + " is sold only in whole units");
            }
            
            long id = ShoppingCartItemIdGen.nextId();
            ShoppingCartItem item = new ShoppingCartItem(id, product, units);
            result.add(item);
        }

        return Collections.unmodifiableList(result);
    }
}
