package il.ac.hit.viewmodel;

import il.ac.hit.*;
import il.ac.hit.model.EnumCategoryType;
import il.ac.hit.model.IModel;
import il.ac.hit.model.Item;
import il.ac.hit.model.User;
import il.ac.hit.view.IView;
import javax.swing.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class implements the IViewModel interface,
 * and functions as "man in the middle" between the View and the Model components.
 */
public class ViewModel implements IViewModel
{
    private IView view;
    private IModel model;
    private int currentID = 0;
    private ExecutorService executorService;

    /**
     * Public default constructor
     */
    public ViewModel() {
        this.executorService = Executors.newFixedThreadPool(3);
    }

    /**
     * Set the view instance variable as the implemented View provided in parameter.
     * @param view Implemented View class
     */
    @Override
    public void setView(IView view)
    {
        this.view = view;
    }

    /**
     * Set the model instance variable as the implemented Model provided in parameter.
     * @param model Implemented Model class
     */
    @Override
    public void setModel(IModel model)
    {
        this.model = model;
    }

    /**
     * Function that checks if the details inputted in Login form are legitimate.
     * @param email email of the user to be checked if exists on DB.
     * @param password password of the user to be checked if exists on DB.
     */
    @Override
    public void handleAuthentication(String email, String password) {
        executorService.submit(() -> {
            try
            {
                currentID = model.selectUserCredentials(email, password);

                if (currentID != 0) { // Login successful
                    view.switchFromLoginWindowToMainWindow();
                    view.setID(currentID);
                }

                else { // Error logging in
                    view.setID(0);
                    GUIUtils.showErrorMessageBox("Error", "No such user exists in DB.");
                }
            }
            catch (CostManException err) {
                GUIUtils.showErrorMessageBox("Error", "Error logging in!");
            }
        });

    }

    /**
     * Function that handles the registration of a new user to the DB.
     * @param email email of the new user to be inserted into DB.
     * @param password password of the new user to be inserted into DB.
     */
    @Override
    public void handleRegistrationRequest(String email, String password)
    {
        executorService.submit(() -> {
            try
            {
                User newUser = new User(email, password);
                ViewModel.this.currentID = ViewModel.this.model.insertNewUser(newUser);
                ViewModel.this.view.switchFromRegistrationWindowToMainWindow();
                GUIUtils.showOkMessageBox("Successful Registration!", "You've been registered" +
                        " successfully!");
            }
            catch (CostManException err)
            {
                GUIUtils.showErrorMessageBox("Error", err.toString());
            }
        });
    }

    /**
     * Function that retrieves data about Items that were added between two specific dates.
     * @param fromDate source date of data to be retrieved from.
     * @param toDate destination date of data to be retrieved from.
     */
    @Override
    public void getItemsBetweenDates(String fromDate, String toDate)
    {
        executorService.submit(() -> {
            List<Item> itemsInRangeOfDates;
            boolean areTwoDatesValid;

            try
            {
                areTwoDatesValid = GUIUtils.isDateValid(fromDate) || GUIUtils.isDateValid(toDate);

                // If one of the dates is invalid
                if (!areTwoDatesValid)
                {
                    GUIUtils.showErrorMessageBox("Invalid Date parameter", "One of the given dates is" +
                            "invalid, please retry.");
                    return;
                }

                itemsInRangeOfDates = model.getItemsByRangeOfDates(currentID, fromDate, toDate);
                SwingUtilities.invokeLater(() -> view.showItems(itemsInRangeOfDates));
            }
            catch (CostManException err)
            {
                GUIUtils.showErrorMessageBox("Error", "Problem fetching items!");
            }
        });
    }

    /**
     * Function that retrieves from the DB all the currency rates available.
     */
    @Override
    public void getCurrenciesRates() {
        executorService.submit(() -> {
            try{
                float[] currencies = model.getCurrencies();
                ViewModel.this.view.saveCurrenciesRates(currencies);
            }
            catch (CostManException e){
                GUIUtils.showErrorMessageBox("Error", e.toString());
            }
        });
    }

    /**
     * Function that adds a Root Category/Sub-Category into the DB.
     * @param categoryName the name of the inserted category
     * @param ownerCategoryName name of the root category, if root category inserted, NULL will be inserted.
     * @param currentCategoryTypeToInsert one of two options selected from an Enum, Primary/Secondary
     */
    @Override
    public void addCategory(String categoryName, String ownerCategoryName, EnumCategoryType currentCategoryTypeToInsert)
    {
        executorService.submit(() -> {

            boolean isCategoryValid = (categoryName != null) && (!categoryName.equals(""));
            String outputMessageFormat = "The category \"%s\" has been successfully added.";

            // There is an insertion of "primary" category
            if (currentCategoryTypeToInsert == EnumCategoryType.Primary)
            {
                isCategoryValid = isCategoryValid && (ownerCategoryName == null);
            }

            // There is an insertion of sub-category
            else
            {
                isCategoryValid = isCategoryValid && (ownerCategoryName != null) && (!ownerCategoryName.equals(""));
            }

            // If the categories matches the conditions above according to their types
            if (isCategoryValid)
            {
                try
                {
                    ViewModel.this.model.insertNewCategory(categoryName, ownerCategoryName);
                    GUIUtils.showOkMessageBox("Sub-Category added!", String.format(outputMessageFormat, categoryName));
                    this.getPrimaryCategories(EnumConsumerOfCategories.CategoriesWindow);
                }
                catch (CostManException err)
                {
                    GUIUtils.showErrorMessageBox("Error", err.toString());
                }
            }
            else
            {
                GUIUtils.showErrorMessageBox("Error", "Invalid parameters for inserting new categories.");
            }
        });
    }

    /**
     * Function that adds a new item into the DB
     * @param item the item to be inserted into DB.
     */
    @Override
    public void addItem(Item item)
    {
       executorService.submit(() -> {
           try {
               model.insertNewItem(item);
           } catch (CostManException e) {
               GUIUtils.showErrorMessageBox("Error", "Error adding item!");
           }

           GUIUtils.showOkMessageBox("Success", "Item successfully added!");
       });
    }

    /**
     * Function that deletes an item from the DB.
     * @param itemID the item ID to be deleted from the DB.
     */
    @Override
    public void deleteItem(String itemID)
    {
        executorService.submit(() -> {
            int integerItemId = 0;

            try
            {
                integerItemId = Integer.parseInt(itemID);
            }
            catch (NumberFormatException err)
            {
                GUIUtils.showOkMessageBox("Error", err.toString());
            }

            try
            {
                ViewModel.this.model.deleteItem(ViewModel.this.currentID, integerItemId);
                GUIUtils.showOkMessageBox("Success", "Item deleted successfully!");
            }
            catch (CostManException err)
            {
                GUIUtils.showOkMessageBox("Error", err.toString());
            }
        });
    }

    /**
     * A function that validates that the user wants to log out from the software and shows
     * the corresponding message box.
     */
    @Override
    public void logout()
    {
        executorService.submit(() -> {
            boolean result = GUIUtils.showYesNoMessageBox("Logout?", "Are you sure you want to logout?");

            if (result)
            {
                ViewModel.this.currentID = 0;
                ViewModel.this.view.openLoginWindowOnlyAndCloseOtherWindows();
            }
        });
    }

    /**
     * A function that makes sure that the user wants to get out of one of the windows shown in main screen.
     */
    @Override
    public void handleClosingOfFeatureWindow()
    {
        executorService.submit(() -> SwingUtilities.invokeLater(() -> {
            boolean result = GUIUtils.showYesNoMessageBox("Get out of this window?",
                    "Are you sure you want to get out of this window?\n" +
                            "All unsaved changes will be lost!");

            if (result) {
                ViewModel.this.view.openMainWindowOnlyAndCloseOtherWindows();
            }
        }));
    }

    /**
     * Retrieve categories by their specified type (PRIMARY)
     */
    @Override
    public void getPrimaryCategories(EnumConsumerOfCategories caller)
    {
        executorService.submit(() -> {
            try
            {
                List<String> categories = ViewModel.this.model.getPrimaryCategories();
                ViewModel.this.view.showCategories(categories, EnumCategoryType.Primary, caller);
            }
            catch (CostManException e)
            {
                GUIUtils.showErrorMessageBox("Error", e.toString());
            }
        });
    }

    /**
     * Retrieve categories by their specified type (SECONDARY)
     */
    @Override
    public void getSubCategories(String currentSelectedPrimaryCategory, EnumConsumerOfCategories caller) {
        executorService.submit(() -> {
            try {
                List<String> categories =ViewModel. this.model.getSecondaryCategories(currentSelectedPrimaryCategory);
                ViewModel.this.view.showCategories(categories, EnumCategoryType.Secondary, caller);
            } catch (CostManException e) {
                GUIUtils.showErrorMessageBox("Error", e.toString());
            }
        });
    }
}