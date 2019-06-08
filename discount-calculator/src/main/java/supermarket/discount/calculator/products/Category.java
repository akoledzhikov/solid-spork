package supermarket.discount.calculator.products;

public enum Category
{
    SNACK, DESSERT, SOFT_DRINK, GROCERY, BEER, WINE, LIQUEUR, MEAT, CHEESE, BREAD, MEAL;
    
    public static Category fromString(String category) {
        if (category == null) {
            return null;
        }
        
        return Category.valueOf(category);
    }
}
