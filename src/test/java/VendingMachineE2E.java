import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class VendingMachineE2E {

    @Test
    void shouldCancelRequestE2E(){
        VendingMachine vm = new VendingMachine();

        setupE2E(vm);

        vm.selectProduct(1);
        assertEquals(Product.COKE, vm.getSelectedProduct());

        vm.cancelRequest();

        assertNull(vm.getSelectedProduct());
        assertEquals(0, vm.getInsertedAmount());
        int actualSumInVendingSafe = TestUtils.getSafeSum(vm);
        assertEquals(0, actualSumInVendingSafe);
    }

    @Test
    void shouldReturnProductE2E() {
        VendingMachine vm = new VendingMachine();
        setupE2E(vm);

        // sell one soda
        vm.selectProduct(3);
        assertEquals(Product.SODA, vm.getSelectedProduct());

        vm.returnProduct();

        assertEquals(0, vm.getInsertedAmount());
        assertEquals(9, vm.getProducts().get(Product.SODA));
        assertNull(vm.getSelectedProduct());

        Integer actualSumInVendingSafe = TestUtils.getSafeSum(vm);
        assertEquals(45, actualSumInVendingSafe);

        // sell another soda
        vm.acceptCoins(Coin.QUARTER);
        vm.acceptCoins(Coin.DIME);
        vm.acceptCoins(Coin.DIME);
        vm.selectProduct(3);
        assertEquals(Product.SODA, vm.getSelectedProduct());

        vm.returnProduct();

        assertEquals(0, vm.getInsertedAmount());
        assertEquals(8, vm.getProducts().get(Product.SODA));
        assertNull(vm.getSelectedProduct());

        actualSumInVendingSafe = TestUtils.getSafeSum(vm);
        assertEquals(90, actualSumInVendingSafe);
    }

    private void setupE2E(VendingMachine vm) {
        assertEquals(0, vm.getInsertedAmount());
        assertEquals(3, vm.getProducts().size());
        for (Product product : vm.getProducts().keySet())
            assertEquals(10, vm.getProducts().get(product));
        assertEquals(0, TestUtils.getSafeSum(vm));

        vm.acceptCoins(Coin.QUARTER);
        vm.acceptCoins(Coin.QUARTER);
        assertEquals(50, vm.getInsertedAmount());
        assertEquals(2, vm.getVendingMachineOverallCoins().get(Coin.QUARTER));

        vm.acceptCoins(Coin.DIME);
        vm.acceptCoins(Coin.DIME);
        vm.acceptCoins(Coin.DIME);
        assertEquals(80, vm.getInsertedAmount());
        assertEquals(3, vm.getVendingMachineOverallCoins().get(Coin.DIME));

        vm.acceptCoins(Coin.NICKEL);
        vm.acceptCoins(Coin.NICKEL);
        vm.acceptCoins(Coin.NICKEL);
        vm.acceptCoins(Coin.NICKEL);
        assertEquals(100, vm.getInsertedAmount());
        assertEquals(4, vm.getVendingMachineOverallCoins().get(Coin.NICKEL));

        vm.acceptCoins(Coin.PENNY);
        vm.acceptCoins(Coin.PENNY);
        vm.acceptCoins(Coin.PENNY);
        vm.acceptCoins(Coin.PENNY);
        assertEquals(104, vm.getInsertedAmount());
        assertEquals(4, vm.getVendingMachineOverallCoins().get(Coin.PENNY));
    }
}