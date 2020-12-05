import java.util.Scanner;

public class Application {
    // CLI only
    public static void main(String[] args) {
        VendingMachine vm = new VendingMachine();
        Scanner c = new Scanner(System.in);
        System.out.println("Welcome! Our vending machine contents are " + vm.toString());
        System.out.println("Product prices:");
        System.out.println("Coke - " + Product.COKE.cost);
        System.out.println("Pepsi - " + Product.PEPSI.cost);
        System.out.println("Soda - " + Product.SODA.cost);

        printSelectionScreen();

        int input = Integer.parseInt(c.nextLine());

        while (input != 6) {
            switch (input) {
                case 1:
                    System.out.println("Select Coin type: ");
                    System.out.println("25 - Quarter");
                    System.out.println("10 - Dime");
                    System.out.println("5 - Nickel");
                    System.out.println("1 - Penny");
                    int coinValue = Integer.parseInt(c.nextLine());
                    switch (coinValue) {
                        case 25:
                            vm.acceptCoins(Coin.QUARTER);
                            System.out.println(vm);
                            break;
                        case 10:
                            vm.acceptCoins(Coin.DIME);
                            System.out.println(vm);
                            break;
                        case 5:
                            vm.acceptCoins(Coin.NICKEL);
                            System.out.println(vm);
                            break;
                        case 1:
                            vm.acceptCoins(Coin.PENNY);
                            System.out.println(vm);
                            break;
                        default:
                            System.out.println("Invalid selection");
                    }
                    break;
                case 2:
                    System.out.println("Select product: ");
                    System.out.println("1 - Coke");
                    System.out.println("2 - Pepsi");
                    System.out.println("3 - Soda");
                    int productCode = Integer.parseInt(c.nextLine());
                    vm.selectProduct(productCode);
                    System.out.println(vm);
                    break;
                case 3:
                    vm.returnProduct();
                    System.out.println("Please pick up your product");
                    System.out.println(vm);
                    break;
                case 4:
                    vm.cancelRequest();
                    System.out.println(vm);
                    break;
                case 5:
                    vm.reset();
                    System.out.println(vm);
                    break;
                default:
                    System.out.println("Invalid Selection");
            }
            System.out.println("Would you like to continue? (y/n)");
            String doContinue = c.nextLine();
            if (doContinue.equals("y")) {
                printSelectionScreen();
                input = Integer.parseInt(c.nextLine());
            } else {
                input = 6;
            }
        }
    }

    private static void printSelectionScreen() {
        System.out.println("Please select: ");
        System.out.println("1 - to insert coin");
        System.out.println("2 - to select product");
        System.out.println("3 - to return product");
        System.out.println("4 - to cancel request");
        System.out.println("5 - to reset vending machine");
        System.out.println("6 - exit the application");
    }

}
