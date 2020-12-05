import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class VendingMachineTest {

    private VendingMachine vm;

    @BeforeEach
    void setup() {
        vm = new VendingMachine();
    }

    @Test
    void shouldHaveFullMachine(){
        assertEquals(0, vm.getInsertedAmount());
        assertEquals(3, vm.getProducts().size());
        for (Product product : vm.getProducts().keySet())
            assertEquals(10, vm.getProducts().get(product));
        assertEquals(0, TestUtils.getSafeSum(vm));
    }

    @ParameterizedTest
    @EnumSource(Coin.class)
    void shouldAcceptCoin(Coin coin) {
        vm.acceptCoins(coin);
        assertEquals(coin.value, vm.getInsertedAmount());
        assertEquals(1, vm.getVendingMachineOverallCoins().get(coin)); // there is one coin of that type
        assertEquals(1, vm.getVendingMachineOverallCoins().size()); // there is only one type of coin
    }

    @ParameterizedTest
    @EnumSource(Product.class)
    void shouldSelectProduct(Product product) {
        vm.selectProduct(product.productCode);
        assertEquals(Product.getByProductCode(product.productCode), vm.getSelectedProduct());
    }

    @Test
    void shouldCancelRequestWhenMachineIsEmpty() {
        vm.cancelRequest();
        assertNull(vm.getSelectedProduct());
        assertEquals(0, vm.getInsertedAmount());
        assertEquals(0, vm.getVendingMachineOverallCoins().size());
    }

    @Test
    void shouldResetVendingMachine() {
        vm.reset();
        assertNull(vm.getSelectedProduct());
        assertEquals(0, vm.getInsertedAmount());
        assertEquals(0, vm.getVendingMachineOverallCoins().size());
    }

    @Test
    void shouldReturnProduct() {
        vm.acceptCoins(Coin.QUARTER);
        vm.selectProduct(Product.COKE.productCode);
        vm.returnProduct();

        assertEquals(0, vm.getInsertedAmount());
        assertEquals(9, vm.getProducts().get(Product.COKE));
        assertNull(vm.getSelectedProduct());

        Integer actualSumInVendingSafe = TestUtils.getSafeSum(vm);
        assertEquals(25, actualSumInVendingSafe);
    }

    @Test
    void shouldNotReturnProductWhenNotEnoughMoneyInserted() {
        vm.acceptCoins(Coin.QUARTER);
        vm.selectProduct(Product.PEPSI.productCode);
        vm.returnProduct();

        assertEquals(25, vm.getInsertedAmount());
        assertEquals(10, vm.getProducts().get(Product.PEPSI));
        assertEquals(Product.PEPSI, vm.getSelectedProduct());

        assertEquals(25, TestUtils.getSafeSum(vm));
    }

    @Test
    void shouldNotReturnProductWhenNoProductSelected() {
        vm.acceptCoins(Coin.QUARTER);
        vm.returnProduct();

        assertEquals(25, vm.getInsertedAmount());
        assertEquals(25, TestUtils.getSafeSum(vm));
    }

    @Test
    void shouldNotReturnWhenRequestingUnavailableProduct() {
        // Sell out cokes (10)
        int i = 10;
        while (i > 0) {
            vm.acceptCoins(Coin.QUARTER);
            vm.selectProduct(1);
            vm.returnProduct();
            i--;
        }

        assertEquals(0, vm.getProducts().get(Product.COKE));
        assertEquals(250, TestUtils.getSafeSum(vm));

        // Try to sell another coke
        vm.acceptCoins(Coin.QUARTER);
        vm.selectProduct(1);

        assertNull(vm.getSelectedProduct());
    }
}
