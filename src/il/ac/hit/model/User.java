package il.ac.hit.model;

import il.ac.hit.CostManException;

/**
 * A class that represents a user and its characteristics
 */
public class User {
    String email;
    String password;

    /**
     * Public constructor of class User
     *
     * @param email E-Mail address of the selected user to be inserted/checked
     * @param password Password of the selected user to be inserted/checked
     * @throws CostManException Exception that will be thrown in case there's a problem inserting the user / invalid
     * credentials
     */
    public User(String email, String password) throws CostManException {
        setEmail(email);
        setPassword(password);
    }

    /**
     * A method that returns user's email address
     *
     * @return User's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * A method that sets user's email address
     *
     * @param email User's email address to be set
     * @throws CostManException Exception will be thrown in case email's length is shorter than 8 characters
     */
    public void setEmail(String email) throws CostManException {
        if (email.length() > 8) {
            this.email = email;
        } else {
            throw new CostManException("E-mail address must be longer than 8 chars!");
        }
    }

    /**
     * A method that returns user's password
     *
     * @return User's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * A method that sets user's password
     *
     * @param password User's password to be set
     * @throws CostManException Exception will be thrown in case password's length is shorter than 6 characters
     */
    public void setPassword(String password) throws CostManException {
        if (password.length() > 6) {
            this.password = password;
        } else {
            throw new CostManException("Password must be longer than 6 chars!");
        }
    }
}
