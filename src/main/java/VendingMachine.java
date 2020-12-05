import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class VendingMachine {

    private int insertedAmount; // will store the amount inserted by the customer
    private Product selectedProduct;

    private Map<Product, Integer> products = new HashMap<>(); // stores products and their quantities
    private Map<Coin, Integer> vendingMachineOverallCoins = new HashMap<>(); // all the coins in the vending machine "safe"

    public VendingMachine() {
        this.insertedAmount = 0;
        this.selectedProduct = null;

        // Initialise the vending machine as having 10 products of each type.
        products.put(Product.COKE, 10);
        products.put(Product.PEPSI, 10);
        products.put(Product.SODA, 10);
    }

    public void acceptCoins(Coin coin) {
        this.insertedAmount += coin.value;

        int currentCoinsInserted = vendingMachineOverallCoins.getOrDefault(coin, 0);
        vendingMachineOverallCoins.put(coin, currentCoinsInserted + 1);

        System.out.println("Inserted amount " + insertedAmount);
    }

    // Using productCode for selection as vending machines usually have a numeric code as the input for the product selection.
    public void selectProduct(int productCode) {
        Product product = Product.getByProductCode(productCode);
        if (products.get(product) > 0) {
            this.selectedProduct = product;
            System.out.println("Selected " + this.selectedProduct);
            return;
        }
        System.out.println("Product " + product + " is unavailable");
    }

    public void cancelRequest() {
        giveChange(this.insertedAmount);
        this.selectedProduct = null;
        this.insertedAmount = 0;
        System.out.println("Request cancelled");
    }

    // If not enough coins were inserted,
    // do not return the product but keep everything the same (waiting for more money).
    // If unable to fulfil requirement, customer can cancel the order.
    public void returnProduct() {
        if (selectedProduct == null) {
            System.out.println("Please select a product");
            return;
        }

        int change = insertedAmount - selectedProduct.cost;
        if (this.insertedAmount >= this.selectedProduct.cost) {
            giveChange(change);
            products.put(selectedProduct, products.get(selectedProduct) - 1); // decrease availability of selected product
            this.selectedProduct = null;
            insertedAmount = 0; // reset inserted amount
            System.out.println("Product returned, insertedAmount remaining " + insertedAmount + " change returned " + change);
        } else {
            System.out.println("Not enough money inserted");
        }
    }

    // Assuming reset does nothing other than reinitialising the inserted amount and selected amount.
    // Thinking about this reset from the scenario of vending machine malfunctioning and engineer being called to take a look.
    // Quantities of money in the "safe" and the products should not change in this scenario.
    public void reset() {
        this.insertedAmount = 0;
        this.selectedProduct = null;
    }

    private void giveChange(int amount) {
        int remainingAmountToRemove = amount;
        while (remainingAmountToRemove > 0) {

            // At every step we'll try to return the change in as big coins as possible.
            // If not enough big coins are available in the vending machine, remove as many as we can.
            int couldRemoveCoins = remainingAmountToRemove / 25;
            Integer availableCoins = Optional.ofNullable(vendingMachineOverallCoins.get(Coin.QUARTER)).orElse(0);
            int canRemove = Math.min(couldRemoveCoins, availableCoins);
            vendingMachineOverallCoins.put(Coin.QUARTER, availableCoins - canRemove);
            remainingAmountToRemove -= canRemove * 25;

            couldRemoveCoins = remainingAmountToRemove / 10;
            availableCoins = Optional.ofNullable(vendingMachineOverallCoins.get(Coin.DIME)).orElse(0);
            canRemove = Math.min(couldRemoveCoins, availableCoins);
            vendingMachineOverallCoins.put(Coin.DIME, availableCoins - canRemove);
            remainingAmountToRemove -= canRemove * 10;

            couldRemoveCoins = remainingAmountToRemove / 5;
            availableCoins = Optional.ofNullable(vendingMachineOverallCoins.get(Coin.NICKEL)).orElse(0);
            canRemove = Math.min(couldRemoveCoins, availableCoins);
            vendingMachineOverallCoins.put(Coin.NICKEL, availableCoins - canRemove);
            remainingAmountToRemove -= canRemove * 5;

            // No need to check number of pennies in machine as even if we don't preload the machine with some coins,
            // inputs - outputs (in terms of coins values), should be 0.
            availableCoins = Optional.ofNullable(vendingMachineOverallCoins.get(Coin.PENNY)).orElse(0);
            vendingMachineOverallCoins.put(Coin.PENNY, availableCoins - remainingAmountToRemove);
            remainingAmountToRemove = 0;
        }
    }

    public int getInsertedAmount() {
        return insertedAmount;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public Map<Coin, Integer> getVendingMachineOverallCoins() {
        return vendingMachineOverallCoins;
    }

    @Override
    public String toString() {
        return "VendingMachine{" +
                "insertedAmount=" + insertedAmount +
                ", selectedProduct=" + selectedProduct +
                ", products=" + products +
                ", vendingMachineOverallCoins=" + vendingMachineOverallCoins +
                '}';
    }
}
