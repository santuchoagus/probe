package app.server;

import app.structures.Product;
import app.structures.ProductInfo;
import app.structures.ProductNotUniqueOrMissingException;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;

public final class ProductServer {
    private final MessageDigest md = MessageDigest.getInstance("MD5");
    private final ArrayList<Product> data = new ArrayList<Product>();

    public ProductServer() throws NoSuchAlgorithmException {
        System.out.println("Initializing Products");
    }

    public ProductInfo[] ls() {
        ArrayList<ProductInfo> info = new ArrayList<ProductInfo>();

        for (Product prod : data) {
            info.add(new ProductInfo(prod.getId(), prod.getName(), prod.getPrice(), prod.getStock()));
        }

        return info.toArray(new ProductInfo[0]);
    }

    public void removeById(String id) {
        boolean found = false;
        for (int i = 0; !found && i < data.size(); i++) {
            if(data.get(i).getId().equals(id)) {
                found = true;
                data.remove(i);
            }
        }
    }

    public String addProduct(String name, int price) {
        final byte[] priceBytes = ByteBuffer.allocate(4).putInt(price).array();
        final StringBuilder idBuilder =  new StringBuilder();
        byte[] digest;

        md.update(priceBytes);
        md.update(name.getBytes());
        md.update(Instant.now().toString().getBytes());
        digest = md.digest();
        md.reset();

        for (byte b : digest) {
            idBuilder.append(String.format("%02x", b));
        }
        idBuilder.append(String.format("%02x", Instant.now().getEpochSecond()));

        final String id = idBuilder.toString();
        final Product product = new innerProduct(id, name, price);
        data.add(product);

        return id;
    }

    public Product findUnique(String productIdOrName) throws ProductNotUniqueOrMissingException {
        Product[] prods = this.find(productIdOrName);

        if (prods.length != 1) {
            throw new ProductNotUniqueOrMissingException(String.format("Found %d products instead of 1", prods.length));
        }

        return prods[0];
    }

    private Product[] find(String productIdOrNameClosestMatch) {
        ArrayList<Product> foundProducts = new ArrayList<>();
        for (Product prod : data) {
            if (
                    (prod.getId().toLowerCase().contains(productIdOrNameClosestMatch.toLowerCase()) && productIdOrNameClosestMatch.length() > 2) ||
                            prod.getName().toLowerCase().contains(productIdOrNameClosestMatch.toLowerCase())
            ) {
                foundProducts.add(prod);
            }
        }

        return foundProducts.toArray(new Product[0]);
    }

    public ProductInfo[] search(String productIdOrNameClosestMatch) {
        ArrayList<ProductInfo> foundProducts = new ArrayList<>();
        for (Product prod : data) {
            if (
                    (prod.getId().toLowerCase().contains(productIdOrNameClosestMatch.toLowerCase()) && productIdOrNameClosestMatch.length() > 2) ||
                    prod.getName().toLowerCase().contains(productIdOrNameClosestMatch.toLowerCase())
            ) {
                foundProducts.add(new ProductInfo(prod.getId(), prod.getName(), prod.getPrice(), prod.getStock()));
            }
        }

        return foundProducts.toArray(new ProductInfo[0]);
    }
}

final class innerProduct extends Product {
    private final String id;
    private String name;
    private int price;

    public innerProduct(String id, String name, int price) {
        this.id = id;
        this.name = validateName(name);
        this.price = validatePrice(price);
    }

    @Override
    public String toString() {
        return String.format("(id=%s, name=%s, price=%s)", id, name, price);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = validateName(name);
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = validatePrice(price);
    }

    private String validateName(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Product's name cannot be empty");
        }

        return name;
    }

    private int validatePrice(int price) {
        if (price < 0) {
            throw new IllegalArgumentException("Product's price cannot be negative");
        }

        return price;
    }
}