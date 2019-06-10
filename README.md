# solid-spork

Implementation of http://codekata.com/kata/kata01-supermarket-pricing/

Allows definition of promotions using meta-language. For example:

3 @Coke@ for the price of 2
# the @Coke@ defines a concrete product

2 #SNACK# for the price of 1
# #SNACK# defines all products which have SNACK category

@Coke@ discount 10%
# any Coke is 10% cheaper.

If the items in the customer's basket can match multiple promotions (for example, 3 soft drinks for the price of 2, but coke is 10% off), the following rules apply:
1) An item can participate only in 1 promotion
2) The best combination of promotions is selected (the one which saves the customer the most money)

So, for example, if we have:
3 @Coke@ for the price of 2
#SOFT_DRINK# discount 10%

And the Coke costs 1 EUR, if the customer buys 3 cokes, we have:
1) variant 1 - 3 cokes for 2 - the customer saves 1 EUR
2) variant 2 - 3 cokes at 0.9 each - the customer saves 30 cents.

Therefore, variant 1 is selected. 
