import java.util.HashMap;
import java.util.Map;

public enum Product {
    COKE(1, "Coke", 25),
    PEPSI(2, "Pepsi", 35),
    SODA(3, "Soda", 45);

    public final int productCode;
    public final int cost;
    public final String name;

    private static final Map<Integer, Product> byProductCode  = new HashMap<>();

    static {
        for (Product p : values()) {
           byProductCode.put(p.productCode, p);
        }
    }

    private Product(int productCode, String name, int cost){
        this.productCode = productCode;
        this.cost = cost;
        this.name = name;
    }

    public static Product getByProductCode(int productCode) {
        return byProductCode.get(productCode);
    }

    @Override
    public String toString() {
        return name;
    }
}
