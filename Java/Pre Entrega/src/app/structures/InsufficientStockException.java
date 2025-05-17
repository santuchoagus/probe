package app.structures;

public class InsufficientStockException extends Exception {
    InsufficientStockException() {
        super();
    }

    InsufficientStockException(String message) {
        super(message);
    }
}
