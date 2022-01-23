package il.ac.hit.view;

import il.ac.hit.model.EnumCategoryType;
import il.ac.hit.model.Item;
import il.ac.hit.viewmodel.EnumConsumerOfCategories;
import il.ac.hit.viewmodel.IViewModel;
import java.util.List;

/**
 * This class represents the Interface of the View
 */
public interface IView {

    /**
     * Set the ViewModel instance variable as the implemented ViewModel provided in parameter.
     *
     * @param vm Implemented ViewModel class
     */
    void setIViewModel(IViewModel vm);

    /**
     * A function that initializes all the windows of the program
     */
    void init();

    /**
     * A function that loads loginWindow's components and shows this specific window
     */
    void start();

    /**
     * Show the current user's items in the table within the reports window.
     *
     * @param data A list of all the items which should be displayed.
     */
    void showItems(List<Item> data);

    /**
     * Save the current userID in the View.
     *
     * @param id The ID of the current logged-on user.
     */
    void setID(int id);

    /**
     * "open" (show) the main window of the program and "close" (hide) all the windows.
     * In addition, this method clears all the fields of all the windows of the program.
     */
    void openMainWindowOnlyAndCloseOtherWindows();

    /**
     * "close" (hide) the login window and "open" (show) the main window of the program.
     * In addition, this method clears all the fields of the login window.
     */
    void switchFromLoginWindowToMainWindow();

    /**
     * "close" (hide) the registration window and "open" (show) the main window of the program.
     * In addition, this method clears all the fields of the registration window.
     */
    void switchFromRegistrationWindowToMainWindow();

    /**
     * "open" (show) the login window of the program and "close" (hide) all the windows.
     * In addition, this method clears all the fields of all the windows of the program.
     */
    void openLoginWindowOnlyAndCloseOtherWindows();

    /**
     * Show categories to the user within the "TransactionsWindow" and the "CategoriesWindow"
     *
     * @param categories A list of all the categories which should be displayed.
     * @param currentCategoriesType An Enum variable which indicates what is the type of the current incoming
     * categories, where "Primary" stands for "Categories" and "Secondary" stands for "Sub-Categories".
     */
    void showCategories(List<String> categories, EnumCategoryType currentCategoriesType, EnumConsumerOfCategories caller);

    /**
     * A function that gets the currency rates array and applies it in the array showed in View.
     *
     * @param currencies array of float variables, containing currencies.
     */
    void saveCurrenciesRates(float[] currencies);
}
