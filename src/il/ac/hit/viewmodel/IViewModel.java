package il.ac.hit.viewmodel;

import il.ac.hit.model.EnumCategoryType;
import il.ac.hit.model.IModel;
import il.ac.hit.model.Item;
import il.ac.hit.view.IView;

public interface IViewModel {

    /**
     * Function that checks if the details inputted in Login form are legitimate.
     *
     * @param email email of the user to be checked if exists on DB.
     * @param password password of the user to be checked if exists on DB.
     */
    public void handleAuthentication(String email, String password);

    /**
     * Function that handles the registration of a new user to the DB.
     *
     * @param email email of the new user to be inserted into DB.
     * @param password password of the new user to be inserted into DB.
     */
    public void handleRegistrationRequest(String email, String password);

    /**
     * Function that retrieves data about Items that were added between two specific dates.
     *
     * @param fromDate source date of data to be retrieved from.
     * @param toDate destination date of data to be retrieved from.
     */
    public void getItemsBetweenDates(String fromDate, String toDate);

    /**
     * Function that adds a Root Category/Sub-Category into the DB.
     *
     * @param categoryName the name of the inserted category
     * @param ownerCategoryName name of the root category, if root category inserted, NULL will be inserted.
     * @param currentCategoryTypeToInsert one of two options selected from an Enum, Primary/Secondary
     */
    public void addCategory(String categoryName, String ownerCategoryName, EnumCategoryType currentCategoryTypeToInsert);

    /**
     * Function that adds a new item into the DB
     *
     * @param item the item to be inserted into DB.
     */
    public void addItem(Item item);

    /**
     * Function that deletes an item from the DB.
     *
     * @param itemID the item ID to be deleted from the DB.
     */
    public void deleteItem(String itemID);

    /**
     * Function that retrieves from the DB all the currency rates available.
     */
    public void getCurrenciesRates();

    /**
     * A function that validates that the user wants to log out from the software and shows
     * the corresponding message box.
     */
    public void logout();

    /**
     * Set the view instance variable as the implemented View provided in parameter.
     *
     * @param view Implemented View class
     */
    public void setView(IView view);

    /**
     * Set the model instance variable as the implemented Model provided in parameter.
     *
     * @param model Implemented Model class
     */
    public void setModel(IModel model);

    /**
     * A function that makes sure that the user wants to get out of one of the windows shown in main screen.
     */
    public void handleClosingOfFeatureWindow();

    /**
     * Retrieve categories by their specified type (PRIMARY in this specific method)
     *
     * @param caller Enum specifying the specific window trying to fetch the categories
     */
    public void getPrimaryCategories(EnumConsumerOfCategories caller);

    /**
     * Retrieve categories by their specified type (SECONDARY in this specific method)
     *
     * @param currentSelectedPrimaryCategory selected Primary category to get its sub-categories
     * @param caller Enum specifying the specific window trying to fetch the categories
     */
    public void getSubCategories(String currentSelectedPrimaryCategory, EnumConsumerOfCategories caller);
}
