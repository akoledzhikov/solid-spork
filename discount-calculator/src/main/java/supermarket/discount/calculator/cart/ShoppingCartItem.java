package supermarket.discount.calculator.cart;


import java.math.BigDecimal;

import supermarket.discount.calculator.products.Product;


public class ShoppingCartItem
{
    private long id;

    private Product product;

    private BigDecimal units;


    public ShoppingCartItem(long id, Product product, BigDecimal units)
    {
        super();
        this.id = id;
        this.product = product;
        this.units = units;
    }


    public long getId()
    {
        return id;
    }


    public Product getProduct()
    {
        return product;
    }


    public BigDecimal getUnits()
    {
        return units;
    }
}
