package il.ac.hit.model;

import il.ac.hit.CostManException;

import java.util.Objects;

/**
 * This class represents a user.
 * email - represents email address of the user's specific account
 * password - represents password of the user's specific account
 */
public class User {
    String email;
    String password;
    String fullName;

    public User(String email, String password, String fullName) throws CostManException {
        setEmail(email);
        setPassword(password);
        setFullName(fullName);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) throws CostManException {
        if(!fullName.equals("")){
            this.fullName = fullName;
        }
        else {
            throw new CostManException("You must enter your name!");
        }

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws CostManException {
        if (email.length() > 8) {
            this.email = email;
        }
        else {
            throw new CostManException("E-mail address must be longer than 8 chars!");
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws CostManException {
        if (password.length() > 6) {
            this.password = password;
        }
        else {
            throw new CostManException("Password must be longer than 6 chars!");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        User user = (User) o;
        return getEmail().equals(user.getEmail()) && getPassword().equals(user.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getPassword());
    }
}
