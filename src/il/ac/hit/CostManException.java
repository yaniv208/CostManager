package il.ac.hit;

/**
 * This class represents the General Exception to the CostMan project.
 */
public class CostManException extends Exception{
    public CostManException(String message) {
        super(message);
    }

    public CostManException(String message, Throwable cause) {
        super(message, cause);
    }
}
