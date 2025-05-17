package test;

import app.structures.ProductInfo;
import app.structures.Product;
import app.structures.ProductNotUniqueOrMissingException;
import app.server.ProductServer;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductConsistencyTests {
    ProductServer products;

    @BeforeEach
    void setup() {
        try {
            products = new ProductServer();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Test
    void createProductLength() {
        Assertions.assertEquals(0, products.ls().length);
        products.addProduct("foo", 10);
        Assertions.assertEquals(1, products.ls().length);
        products.addProduct("bar", 20);
        Assertions.assertEquals(2, products.ls().length);
    }

    @Test
    void productInitializationPropertiesMaintain() {
        ProductInfo[] info;
        Assertions.assertEquals(0, products.ls().length);
        products.addProduct("Foo", 23);
        info = products.ls();
        Assertions.assertEquals("Foo", info[0].name);
        Assertions.assertEquals(23, info[0].price);
        Assertions.assertEquals(0, info[0].stock);
    }

    @Test
    void productInitializationPropertiesMaintainBatch() {
        products.addProduct("Foo", 23);
        products.addProduct("Bar", 50);
        products.addProduct("Baz", 11);

        ProductInfo[] info = products.ls();
        assert_MaintainPropertyHelper(info[0], "Foo", 23, 0);
        assert_MaintainPropertyHelper(info[1], "Bar", 50, 0);
        assert_MaintainPropertyHelper(info[2], "Baz", 11, 0);
    }

    private void assert_MaintainPropertyHelper(ProductInfo info, String name, int price, int stock) {
        Assertions.assertEquals(name, info.name);
        Assertions.assertEquals(price, info.price);
        Assertions.assertEquals(stock, info.stock);
    }

    @Test
    void findByNameAndClosestName() {
        String id1 = products.addProduct("Foo", 23);
        String id2 = products.addProduct("Bar", 50);
        String id3 = products.addProduct("Baz", 11);

        // this is to prevent searching for MD5 id with less than 3 characters.
        Assertions.assertEquals(2, products.search("a").length);
        Assertions.assertEquals(0, products.search("b").length);
        Assertions.assertEquals(0, products.search("d").length);

        Assertions.assertEquals(3, products.search("").length);
        Assertions.assertEquals(0, products.search("Dd").length);
        Assertions.assertEquals(1, products.search("Foo").length);
        Assertions.assertEquals(2, products.search("Ba").length);
        Assertions.assertEquals(1, products.search("z").length);
        Assertions.assertEquals(1, products.search("Baz").length);

        String id1Substr = products.search("Foo")[0].id.substring(4, 12);
        Assertions.assertEquals("Foo", products.search(id1Substr)[0].name);

        String id2Substr = products.search("Bar")[0].id.substring(0, 3);
        Assertions.assertEquals("Bar", products.search(id2Substr)[0].name);

        System.out.println(products.search(id2Substr)[0]);
    }

    @Test
    void findUnique() {
        String id1 = products.addProduct("Foo", 23);
        String id2 = products.addProduct("Bar", 50);
        String id3 = products.addProduct("Baz", 11);

        Assertions.assertThrows(ProductNotUniqueOrMissingException.class, () -> {
            products.findUnique("Ba");
        });

        Assertions.assertThrows(ProductNotUniqueOrMissingException.class, () -> {
            products.findUnique("d");
        });

        Assertions.assertDoesNotThrow(() -> {
            products.findUnique("Bar");
        });


    }

    @Test
    void setProperties() {
        products.addProduct("Baf", 12);
        products.addProduct("Foo", 1);
        String id = products.addProduct("Bar", 50);
        products.addProduct("Man", 13);

        try {
            Assertions.assertEquals(0, products.findUnique(id).getStock());
        } catch (ProductNotUniqueOrMissingException e) {
            throw new RuntimeException(e);
        }
        try {
            Assertions.assertEquals("Bar", products.findUnique(id).getName());
        } catch (ProductNotUniqueOrMissingException e) {
            throw new RuntimeException(e);
        }
        try {
            Assertions.assertEquals(50, products.findUnique(id).getPrice());
        } catch (ProductNotUniqueOrMissingException e) {
            throw new RuntimeException(e);
        }

        Product prod = null;
        try {
            prod = products.findUnique(id);
        } catch (ProductNotUniqueOrMissingException e) {
            throw new RuntimeException(e);
        }
        prod.setStock(11);
        prod.setName("Kle");
        prod.setPrice(120);

        try {
            Assertions.assertEquals(11, products.findUnique(id).getStock());
        } catch (ProductNotUniqueOrMissingException e) {
            throw new RuntimeException(e);
        }
        try {
            Assertions.assertEquals("Kle", products.findUnique(id).getName());
        } catch (ProductNotUniqueOrMissingException e) {
            throw new RuntimeException(e);
        }
        try {
            Assertions.assertEquals(120, products.findUnique(id).getPrice());
        } catch (ProductNotUniqueOrMissingException e) {
            throw new RuntimeException(e);
        }
    }
}
