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
    
    public BigDecimal getCost() {
        return units.multiply(product.getPricePerUnit());
    }


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int)(id ^ (id >>> 32));
        return result;
    }


    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ShoppingCartItem other = (ShoppingCartItem)obj;
        if (id != other.id)
            return false;
        return true;
    }
}
