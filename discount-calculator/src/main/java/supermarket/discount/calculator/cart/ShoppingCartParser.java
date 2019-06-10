package supermarket.discount.calculator.cart;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
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
            if (product.isSoldWholeUnit() && units.stripTrailingZeros().scale() > 0)
            {
                throw new IllegalArgumentException(product.getName() + " is sold only in whole units");
            }

            result.addAll(splitItemIfNeeded(product, units));
        }

        return Collections.unmodifiableList(result);
    }


    private static Collection<ShoppingCartItem> splitItemIfNeeded(Product product, BigDecimal units)
    {
        // Items sold in whole units should come 1 by 1, as the cashier scans them.
        // However, if there are packs (for example, 4 Cokes in one pack), this method will
        // split them into individual items
        if (!product.isSoldWholeUnit())
        {
            long id = ShoppingCartItemIdGen.nextId();
            return Collections.singletonList(new ShoppingCartItem(id, product, units));
        }

        Collection<ShoppingCartItem> result = new LinkedList<>();
        for (int i = 0; i < units.intValueExact(); i++)
        {
            long id = ShoppingCartItemIdGen.nextId();
            result.add(new ShoppingCartItem(id, product, BigDecimal.ONE));
        }

        return result;
    }
}
