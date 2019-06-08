package supermarket.discount.calculator.promotions.loader;


public class PromotionLoadException
    extends RuntimeException
{
    private static final long serialVersionUID = 20190806L;


    public PromotionLoadException()
    {
        super();
    }


    public PromotionLoadException(String message, Throwable cause)
    {
        super(message, cause);
    }


    public PromotionLoadException(String message)
    {
        super(message);
    }


    public PromotionLoadException(Throwable cause)
    {
        super(cause);
    }
}
