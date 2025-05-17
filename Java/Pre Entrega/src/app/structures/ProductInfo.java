package app.structures;

public class ProductInfo {
    public final String id;
    public final String name;
    public final int price;
    public final int stock;

    public ProductInfo(String id, String name, int price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    @Override
    public String toString() {
        return String.format("(id=%s, name=%s, price=%d, stock=%d)", id, name, price, stock);
    }
}
