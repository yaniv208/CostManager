package il.ac.hit.viewmodel;

import il.ac.hit.model.IModel;
import il.ac.hit.model.Item;
import il.ac.hit.view.IView;

public interface IViewModel {
    // Login form
    void handleAuthentication(String email, String password);

    // Registration form
    void handleRegistrationRequest(String email, String password, String fullName);

    // Reports form
    void getItemsBetweenDates(String fromDate, String toDate);

    // Categories form
    void addCategory(String categoryName, String ownerCategoryName);

    // Add items form
    void addItem(Item item);
    void deleteItem(String itemID);

    // All forms
    void logout();

    // Kernelic methods
    void setView(IView view);
    void setModel(IModel model);
    void handleClosingOfFeatureWindow();
}
