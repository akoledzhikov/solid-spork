package supermarket.discount.calculator.cart;

import java.util.concurrent.atomic.AtomicLong;

public class ShoppingCartItemIdGen
{
    private static final AtomicLong ID = new AtomicLong();
    
    public static long nextId() {
        return ID.incrementAndGet();
    }
}
