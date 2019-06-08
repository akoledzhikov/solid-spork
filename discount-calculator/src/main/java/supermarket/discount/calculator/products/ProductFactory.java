package supermarket.discount.calculator.products;

public class ProductFactory
{
    // TODO - bad! Cannot be mocked to unit test ProductLoader. Sad :(
    public static Product productFromString(String productAsString) {
        String[] split = productAsString.split(",");
        if (split.length != 4) {
            throw new IllegalArgumentException("Malformed product definition: " + productAsString);
        }
        
        return null;
    }
}
