package app.structures;

public class ProductNotUniqueOrMissingException extends Exception {
    public ProductNotUniqueOrMissingException(String message) {
        super(message);
    }
}
