package supermarket.discount.calculator.cart;

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
    
    public List<ShoppingCartItem> parse(List<String> cartAsStrings) {
        LOG.debug("Parsing : " + String.join(",", cartAsStrings));
        List<ShoppingCartItem> result = new ArrayList<>(cartAsStrings.size());
        for (String itemAsString : cartAsStrings) {
            String split[] = itemAsString.split("\\s");
            if (split.length != 2) {
                throw new IllegalArgumentException("Malformed shopping cart item: " + itemAsString);
            }
            
            Product product = pl.getProduct(split[0]);
        }
        return Collections.unmodifiableList(result);
    }
}
