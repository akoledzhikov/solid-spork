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


    public List<ShoppingCartItem> parse(List<String> cartAsStrings)
    {
        LOG.debug("Parsing : " + String.join(",", cartAsStrings));
        List<ShoppingCartItem> result = new ArrayList<>(cartAsStrings.size());
        for (String itemAsString : cartAsStrings)
        {
            String split[] = itemAsString.split("\\s");
            if (split.length != 2)
            {
                throw new IllegalArgumentException("Malformed shopping cart item: " + itemAsString);
            }

            Product product = pl.getProduct(split[0]);
            if (product == null)
            {
                throw new IllegalArgumentException("Unknown product: " + product);
            }

            // of course, we are not going to round in customer's favor.
            BigDecimal units = new BigDecimal(split[1]).setScale(2, RoundingMode.UP);
            long id = ShoppingCartItemIdGen.nextId();
            ShoppingCartItem item = new ShoppingCartItem(id, product, units);
            result.add(item);
        }
        
        return Collections.unmodifiableList(result);
    }
}
