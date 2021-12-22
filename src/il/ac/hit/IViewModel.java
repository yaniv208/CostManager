package il.ac.hit;

public interface IViewModel {
    // Login form
    void handleAuthentication(String email, String password);

    // Registration form
    void handleRegistrationRequest();

    // Reports form
    void getItems(String fromDate, String toDate);

    // Categories form
    void addCategory(String categoryName, String ownerCategoryName);

    // Add items form
    void addItem(Item item);
    void deleteItem(int itemID);

    // All forms
    void logout();

    // Kernelic methods
    void setView(IView view);
    void setModel(IModel model);
}
