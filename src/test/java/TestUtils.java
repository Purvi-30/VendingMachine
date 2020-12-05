public class TestUtils {
    public static int getSafeSum(VendingMachine vm) {
        int sum = 0;
        for (Coin coin : vm.getVendingMachineOverallCoins().keySet()) {
            sum += vm.getVendingMachineOverallCoins().get(coin) * coin.value;
        }
        return sum;
    }
}

