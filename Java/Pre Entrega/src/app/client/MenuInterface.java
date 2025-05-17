package app.client;

import app.server.ProductServer;
import app.structures.*;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class MenuInterface {
    private static Order currentOrder = new Order();
    public static final ProductServer server;

    static {
        try {
            server = new ProductServer();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addProductOption(String name, int price) throws UnsuccessfulOperationException {
        if (price < 0)
            throw new UnsuccessfulOperationException("Price should be a positive number or zero.");
        if (name.isEmpty())
            throw new UnsuccessfulOperationException("Product name cannot be blank.");

        server.addProduct(name, price);
    }

    public static void updateProductOption(String idOrName, String newName, int newPrice, int newStock) throws UnsuccessfulOperationException {
        if (newPrice < 0)
            throw new UnsuccessfulOperationException("Price should be a positive number or zero.");
        if (newStock < 0)
            throw new UnsuccessfulOperationException("Stock should be a positive number or zero.");
        try {
            Product prod = server.findUnique(idOrName);
            if (!newName.isEmpty())
                prod.setName(newName);

            prod.setPrice(newPrice);
            prod.setStock(newStock);

        } catch (ProductNotUniqueOrMissingException e) {
            throw new UnsuccessfulOperationException("Product is not unique or it may be missing");
        }
    }

    public static ProductInfo findUnique(String productIdOrNameClosestMatch) throws ProductNotUniqueOrMissingException {
        Product prod = server.findUnique(productIdOrNameClosestMatch);
        return new ProductInfo(prod.getId(), prod.getName(), prod.getPrice(), prod.getStock());
    }

    public static void listProductsOption() {
        listProductsOption("");
    }

    public static void listProductsOption(String productIdOrNameClosestMatch) {
        ProductInfo[] products = server.search(productIdOrNameClosestMatch);

        if (products.length == 0) {
            System.out.println("No products to list, server is clean.");
        } else {
            int maxCharacters = 14;

            System.out.printf("%16s %16s %16s %16s", "id", "name", "price", "stock\n");

            for (ProductInfo pinfo : products) {
                stringEllipsisHelper(pinfo, maxCharacters);
            }
        }
    }

    public static void stringEllipsisHelper(ProductInfo pinfo, int maxCharacters) {
        String shortId = _stringEllipsisHelper(pinfo.id, maxCharacters);
        String shortName = _stringEllipsisHelper(pinfo.name, maxCharacters);
        String shortPrice = _stringEllipsisHelper(String.valueOf(pinfo.price), maxCharacters);
        String shortStock = _stringEllipsisHelper(String.valueOf(pinfo.stock), maxCharacters);
        System.out.printf("%16s %16s %16s %16s\n", shortId, shortName, shortPrice, shortStock);
    }

    private static String _stringEllipsisHelper(String str, int maxCharacters) {
        if (str.length() <= maxCharacters)
            return str;

        return str.substring(0, maxCharacters - 3).concat("...");
    }

    public static void removeProductOption(String productIdOrNameClosestMatch) throws UnsuccessfulOperationException {
        ProductInfo pinfo = null;

        try {
            pinfo = findUnique(productIdOrNameClosestMatch);
        } catch (ProductNotUniqueOrMissingException e) {
            throw new UnsuccessfulOperationException("Product id or name matches more than once, unable to remove multiple products.");
        }

        server.removeById(pinfo.id);
    }

    public static void clearOrder() {
        System.out.println("Emptying order list.");
        currentOrder = new Order();
    }

    public static void listOrder() {
        Order.IndividualOrder[] iors = currentOrder.ls();
        int maxCharacters = 14;
        System.out.printf("%16s %16s %16s %16s", "id", "name", "price", "quantity\n");

        for (Order.IndividualOrder ior : iors) {
            ProductInfo pinfo = ior.getProduct();
            String shortId = _stringEllipsisHelper(pinfo.id, maxCharacters);
            String shortName = _stringEllipsisHelper(pinfo.name, maxCharacters);
            String shortPrice = _stringEllipsisHelper(String.valueOf(pinfo.price), maxCharacters);
            String shortQuantity = _stringEllipsisHelper(String.valueOf(ior.getQuantity()), maxCharacters);

            System.out.printf("%16s %16s %16s %16s\n", shortId, shortName, shortPrice, shortQuantity);
        }
    }

    public static void addProductToOrder(String productIdOrNameClosestMatch, int quantity)
            throws UnsuccessfulOperationException, ProductNotUniqueOrMissingException {

        if (quantity < 0)
            throw new UnsuccessfulOperationException("Quantity should be a positive number or zero.");

        try {
            ProductInfo pinfo = findUnique(productIdOrNameClosestMatch);
            currentOrder.addOrUpdateProductItem(pinfo, quantity);

        } catch (ProductNotUniqueOrMissingException e) {
            throw e;
        }
    }

    public static void sendOrderOption() throws UnsuccessfulOperationException {
        if (currentOrder.ls().length == 0)
            throw new UnsuccessfulOperationException("Cannot send a empty order list to the server.");

        for (Order.IndividualOrder ior : currentOrder.ls()) {
            Product prod = null;
            try {
                prod = server.findUnique(ior.getProduct().id);
                prod.assertStockAvailable(ior.getQuantity());

            } catch (ProductNotUniqueOrMissingException e) {
                throw new UnsuccessfulOperationException(
                        "Product missing from the server's product list, possibly erased after order's creation."
                );
            } catch (InsufficientStockException e) {
                ProductInfo pinfo = ior.getProduct();
                throw new UnsuccessfulOperationException(
                        String.format("Not enough stock for \"%s\".\nStock: %d, Required: %d\n", pinfo.name, pinfo.stock, ior.getQuantity())
                );
            }
        }

        for (Order.IndividualOrder ior : currentOrder.ls()) {
            Product prod = null;
            try {
                prod = server.findUnique(ior.getProduct().id);

            } catch (ProductNotUniqueOrMissingException e) {
                throw new UnsuccessfulOperationException(
                        "Product missing from the server's product list, possibly erased after order's creation."
                );
            }

            prod.setStock(prod.getStock() - ior.getQuantity());
        }
    }
}

class Order {
    private ArrayList<IndividualOrder> products = new ArrayList<>();

    public static class IndividualOrder {
        private ProductInfo product;
        private int quantity;

        public IndividualOrder(ProductInfo product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        public ProductInfo getProduct() {
            return product;
        }

        public void setProduct(ProductInfo product) {
            this.product = product;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    public IndividualOrder[] ls() {
        IndividualOrder[] iors = new IndividualOrder[products.size()];

        for (int i = 0; i < products.size(); i++) {
            IndividualOrder newIor = new IndividualOrder(products.get(i).product, products.get(i).quantity);
            iors[i] = newIor;
        }

        return iors;
    }

    public void addOrUpdateProductItem(ProductInfo pinfo, int quantity) {
        IndividualOrder currentIndividualOrder = null;

        if (pinfo != null) {
            for (IndividualOrder ior : products) {
                if (ior.product.id.equals(pinfo.id)) {
                    currentIndividualOrder = ior;
                }
            }
        }

        if (currentIndividualOrder != null) {
            currentIndividualOrder.quantity = quantity;
        } else {
            products.add(new IndividualOrder(pinfo, quantity));
        }
    };

    public void removeProduct(int orderProductRow) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void modifyOrderByRow(int orderProductRow, int quantity) {
        IndividualOrder order = products.get(orderProductRow);
        order.quantity = quantity;
    }

    @Override
    public String toString() {
        int index = 0;
        StringBuilder sb = new StringBuilder();
        for (IndividualOrder iorder : products) {
            ProductInfo info = iorder.product;
            int quantity = iorder.quantity;

            sb.append(String.format("(%d), %s, %s, %d\n", index, info.id, info.name, quantity));
                    index++;
        }

        return sb.toString();
    }
}
