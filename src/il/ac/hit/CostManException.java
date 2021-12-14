package il.ac.hit;

public class CostManException extends Exception{
    public CostManException(String message) {
        super(message);
    }

    public CostManException(String message, Throwable cause) {
        super(message, cause);
    }
}
