package il.ac.hit;

/**
 * This class represents the specific exception to the CostMan project, providing the options to use show a specific
 * message to the user, or, in addition to our specific message, show the system's corresponding error.
 */
public class CostManException extends Exception{
    /**
     * Exception that provides a specific message to the user
     * @param message message to be shown to user
     */
    public CostManException(String message) {
        super(message);
    }

    /**
     * Exception that provides a specific message to the user, in addition to the cause (which is a Throwable)
     * @param message message to be shown to user
     * @param cause Throwable object that contains the cause
     */
    public CostManException(String message, Throwable cause) {
        super(message, cause);
    }
}
