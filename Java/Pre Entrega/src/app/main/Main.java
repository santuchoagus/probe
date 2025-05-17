package app.main;

import app.client.MenuInterface;
import app.server.ProductServer;
import app.structures.ProductInfo;
import app.structures.ProductNotUniqueOrMissingException;
import app.structures.UnsuccessfulOperationException;

import java.util.Scanner;

enum status {RUNNING, EXITING, UPDATING, ORDERING};
enum option {
    help,
    addProduct,
    listProducts,
    searchProducts,
    updateProduct,
    fullDescription,
    removeProduct,
    manageOrder,
    addProductToOrder,
    listOrder,
    clearOrder,
    sendOrder,
    exit,
}

public class Main {
    public static void main(String[] args) {
        ProductServer server = MenuInterface.server;

        while(serverHandler(server) == status.RUNNING);
    }

    private static void helpPrinterMenu() {
        System.out.printf("\n%-24s %-24s\n", "(a) Add product.", "(u) Update product");
        System.out.printf("%-24s %-24s\n", "(l) List products.", "(o) Manage order");
        System.out.printf("%-24s %-24s\n\n", "(s) Search products.", "(q) Exit.");
    }

    private static void helpPrinterOrders() {
        System.out.println("\n(a) add product.");
        System.out.println("(l) List order.");
        System.out.println("(x) Clear order.");
        System.out.println("(s) Send order.");
        System.out.println("(q) Back.\n");
    }

    private static void helpPrinterUpdate(ProductInfo pinfo) {
        System.out.println("Selected product:");
        System.out.printf("%16s %16s %16s %16s", "id", "name", "price", "stock\n");
        MenuInterface.stringEllipsisHelper(pinfo, 14);
        System.out.print("\n(f) Print full info. | ");
        System.out.print("(u) Update values. | ");
        System.out.print("(r) Remove. | ");
        System.out.println("(q) Back.\n");
    }

    private static status serverHandler(ProductServer server) {
        status currentStatus = status.RUNNING;
        Scanner scanner = new Scanner(System.in);
        option currentOption = option.help;

        helpPrinterMenu();
        System.out.print("> ");
        String input = scanner.nextLine();

        switch (input) {
            case "a", "A", "add" -> currentOption = option.addProduct;
            case "l", "L", "ls" -> currentOption = option.listProducts;
            case "s", "S", "search" -> currentOption = option.searchProducts;
            case "u", "U", "update" -> currentOption = option.updateProduct;
            case "o", "O", "order" -> currentOption = option.manageOrder;
            case "q", "Q", "exit" -> currentOption = option.exit;
            default -> {
                System.out.println("Incorrect option, please ensure spelling it correctly...");
                currentOption = option.help;
            }
        }

        switch (currentOption) {
            case option.addProduct -> addProductHandler();
            case option.listProducts -> listProductHandler();
            case option.searchProducts -> searchProductsHandler();
            case option.updateProduct -> updateProductHandler();
            case option.manageOrder -> manageOrderHandler();
            case option.exit -> currentStatus = status.EXITING;
            case option.help -> currentStatus = status.RUNNING;
            default -> throw new UnsupportedOperationException("Not yet implemented");
        }

        return currentStatus;
    }

    private static void manageOrderHandler() {
        option currentOption = option.help;

        while(!currentOption.equals(option.exit)) {
            currentOption = option.help;

            helpPrinterOrders();

            System.out.print(" > ");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            switch(input) {
                case "a", "A", "add" -> currentOption = option.addProductToOrder;
                case "l", "L", "ls" -> currentOption = option.listOrder;
                case "x", "clear" -> currentOption = option.clearOrder;
                case "s", "S" -> currentOption = option.sendOrder;
                case "q", "Q" -> currentOption = option.exit;
                default -> System.out.println("Incorrect option, please ensure spelling it correctly...");
            }

            switch(currentOption) {
                case option.addProductToOrder -> addProductToOrderHandler();
                case option.listOrder -> listOrderHandler();
                case option.clearOrder -> clearOrderHandler();
                case option.sendOrder -> sendOrderHandler();
            }
        }
    }

    private static void sendOrderHandler() {
        try {
            MenuInterface.sendOrderOption();
            clearOrderHandler();
        } catch (UnsuccessfulOperationException e) {
            System.out.printf("Order list couldn't be processed, reason: %s\n", e.getMessage());
        }
    }

    private static void clearOrderHandler() {
        MenuInterface.clearOrder();
    }

    private static void listOrderHandler() {
        Scanner scanner = new Scanner(System.in);
        MenuInterface.listOrder();

        System.out.println("Press Enter key to continue...");
        scanner.nextLine();
    }

    private static void addProductToOrderHandler() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Select a product by id or name (can be shortened), but it should be unambiguous and unique.");
            System.out.println("Selecting by id requires at minimum 3 characters.");
            System.out.print("id_or_name > ");
            String idOrName = scanner.nextLine();
            ProductInfo pinfo = MenuInterface.findUnique(idOrName);

            System.out.println("Amount of product to add to the order, can exceed available stock.");
            System.out.print("quantity > ");

            try {
                int quantity = Integer.parseInt(scanner.nextLine());
                MenuInterface.addProductToOrder(pinfo.id, quantity);

            } catch (NumberFormatException e) {
                System.out.println("Amount to add to the order should be a valid number");
            }

        } catch (ProductNotUniqueOrMissingException e) {
            System.out.println("Query may have returned multiple results or none, be more specific.");
        } catch (UnsuccessfulOperationException e) {
            System.out.printf("Operation couldn't be completed, reason: %s\n", e.getMessage());
        }
    }

    private static void searchProductsHandler() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Search all the products by id or name, filters all of the closest matches.");
        System.out.print("id_or_name > ");
        String idOrName = scanner.nextLine();

        MenuInterface.listProductsOption(idOrName);
        System.out.println("Press Enter key to continue...");
        scanner.nextLine();
    }

//    private static void addOrUpdateOrderHandler() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Select a product to add by it's id or name (could be approximated but has to be unambiguous)");
//        System.out.print("id_or_name > ");
//        String idOrName = scanner.nextLine();
//
//        System.out.println("Place the quantity of the product to add or update.");
//        System.out.print("quantity > ");
//        int quantity = 0;
//
//        try {
//            quantity = Integer.parseInt(scanner.nextLine());
//        } catch (NumberFormatException e) {
//            System.out.printf("Operation failed, reason: %s\n", "Quantity should be a valid number.");
//            return;
//        }
//
//        try {
//            MenuInterface.addProductToOrder(idOrName, quantity);
//        } catch (UnsuccessfulOperationException e) {
//            System.out.printf("Unsuccessful operation, error: %s\n", e.getMessage());
//        } catch (ProductNotUniqueOrMissingException e) {
//            System.out.println("error product id or name doesn't match: Product is not a valid product from the product's list.");
//        }
//    }

    private static void updateProductInformationHandler(ProductInfo pinfo) {
        Scanner scanner = new Scanner(System.in);
        String newName;
        int newPrice;
        int newStock;

        System.out.println("Insert the updated values of the fields behind the arrow.");
        System.out.println("Leave name blank and press Enter if you wanna keep the same");

        System.out.print("name > ");
        newName = scanner.nextLine();

        System.out.print("price > ");

        try {
            newPrice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.printf("Operation failed, reason: %s\n", "Price should be a number.");
            return;
        }

        System.out.print("stock > ");

        try {
            newStock = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.printf("Operation failed, reason: %s\n", "Stock should be a number.");
            return;
        }

        try {
            MenuInterface.updateProductOption(pinfo.id, newName, newPrice, newStock);

            System.out.println("Product updated successfully.");

        } catch (UnsuccessfulOperationException e) {
            System.out.printf("Operation couldn't be completed, reason: %s\n", e.getMessage());
        }
    }

    private static void printFullProductInfoHandler(ProductInfo pinfo) {
        System.out.printf("- Id: %s\n", pinfo.id);
        System.out.printf("- Name: \"%s\"\n", pinfo.name);
        System.out.printf("- Price: $%-10s - Stock: %s units\n", pinfo.price, pinfo.stock);
    }

    private static void removeProductHandler(ProductInfo pinfo) {
        try {
            MenuInterface.removeProductOption(pinfo.id);
        } catch (Exception e) {
            System.out.printf("Item with id: %s... failed to be removed. Error: %s\n", pinfo.id.substring(0,8), e.getMessage());
        }
    }

    private static void updateProductHandler() {
        Scanner scanner = new Scanner(System.in);
        option currentOption = option.help;

        System.out.println("Select a product by id or name (can be shortened), but it should be unambiguous and unique.");
        System.out.println("Selecting by id requires at minimum 3 characters.");
        System.out.print("id_or_name > ");
        String idOrName = scanner.nextLine();

        try {
            ProductInfo pinfo = MenuInterface.findUnique(idOrName);
            helpPrinterUpdate(pinfo);

            while (currentOption.equals(option.help)) {
                System.out.print("> ");
                String input = scanner.nextLine();

                switch (input) {
                    case "f", "F" -> currentOption = option.fullDescription;
                    case "u", "U", "update" -> currentOption = option.updateProduct;
                    case "r", "R", "remove" -> currentOption = option.removeProduct;
                    case "q", "Q", "exit" -> currentOption = option.exit;
                    default -> {
                        System.out.println("Invalid operation, ensure correct spelling");
                    }
                }
            }

            switch (currentOption) {
                case option.fullDescription -> printFullProductInfoHandler(pinfo);
                case option.updateProduct -> updateProductInformationHandler(pinfo);
                case option.removeProduct -> removeProductHandler(pinfo);
                case option.exit -> currentOption = option.exit;
                default -> System.out.println("Invalid operation, ensure correct spelling");
            }

        } catch (ProductNotUniqueOrMissingException e) {
            System.out.println("Query may have returned multiple results or none, be more specific.");
        }
    }

    private static void addProductHandler() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert the next elements");

        System.out.print("name > ");
        String name = scanner.nextLine();

        System.out.print("price > ");
        int price;

        try {
            price = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.printf("Operation failed, reason: %s\n", "Price should be a number");
            return;
        }

        try {
            MenuInterface.addProductOption(name, price);
        } catch (UnsuccessfulOperationException e) {
            System.out.printf("Operation failed, reason: %s\n", e.getMessage());
        }
    }

    private static void listProductHandler() {
        Scanner scanner = new Scanner(System.in);

        MenuInterface.listProductsOption();
        System.out.println("Press Enter key to continue...");
        scanner.nextLine();
    }
}
