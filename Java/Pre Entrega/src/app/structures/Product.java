package app.structures;

public abstract class Product {
    private int stock = 0;

    abstract public String getId();
    abstract public String getName();
    abstract public void setName(String name);
    abstract public int getPrice();
    abstract public void setPrice(int price);

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void addStock(int stock) {
        this.stock += stock;
    }

    public void assertStockAvailable(int requestedQuantity) throws InsufficientStockException {
        if (requestedQuantity > 0 && this.stock < requestedQuantity) {
            throw new InsufficientStockException(
                    String.format("Not enough stock, current: %d, Requested: %d", this.stock, requestedQuantity)
            );
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Product other = (Product) obj;
        return this.getId().equals(other.getId());
    }
}

