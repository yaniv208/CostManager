package il.ac.hit;

/**
 * This class represents the specific exception to the CostMan project, providing the options to use show a specific
 * message to the user, or, in addition to our specific message, show the system's corresponding error.
 */
public class CostManException extends Exception{
    public CostManException(String message) {
        super(message);
    }

    public CostManException(String message, Throwable cause) {
        super(message, cause);
    }
}
