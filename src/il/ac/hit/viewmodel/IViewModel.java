package il.ac.hit.viewmodel;

import il.ac.hit.model.EnumCategoryType;
import il.ac.hit.model.IModel;
import il.ac.hit.model.Item;
import il.ac.hit.view.IView;

public interface IViewModel {
    // Login form
    void handleAuthentication(String email, String password);

    // Registration form
    void handleRegistrationRequest(String email, String password);

    // Reports form
    void getItemsBetweenDates(String fromDate, String toDate);

    // Categories form
    public void addCategory(String categoryName, String ownerCategoryName, EnumCategoryType currentCategoryTypeToInsert);

    // Add items form
    void addItem(Item item);
    void deleteItem(String itemID);
    void getCurrenciesRates();

    // All forms
    void logout();

    // Kernelic methods
    void setView(IView view);
    void setModel(IModel model);
    void handleClosingOfFeatureWindow();
    void getPrimaryCategories(EnumConsumerOfCategories caller);
    void getSubCategories(String currentSelectedPrimaryCategory, EnumConsumerOfCategories caller);

}
