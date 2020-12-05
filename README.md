**Requirements**

1. Accepts coins of 1,5,10,25 Cents i.e. penny, nickel, dime, and quarter.
2. Allow user to select products Coke(25), Pepsi(35), Soda(45)
3. Allow user to take refund by cancelling the request.
4. Return selected product and remaining change if any.
5. Allow reset operation for vending machine supplier.

**Structure**:

_Coin_ - enum for quarters, dimes, nickels and pennies with their relevant values

_Product_ - enum for Coke, Pepsi, Soda and their product codes and cost

_Application_ - CLI to interact with the vending machine.

_Vending Machine_ - Actual code for inserting coins, selecting product, returning product and cancelling product selection.

**Justification**

Used `int` for representing the amount values as so far the costs are very low and also whole numbers (i.e. 25, 35, 45) and vending machine sizes are limited to most likely no more than 200 odd products. Even in that case, the whole deposited amount wouldn't be bigger than `200 * 45 = 9000` which can easily fit in a `short` value, let alone `int`.