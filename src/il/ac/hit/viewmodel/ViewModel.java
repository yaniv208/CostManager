package il.ac.hit.viewmodel;

import il.ac.hit.*;
import il.ac.hit.model.EnumCategoryType;
import il.ac.hit.model.IModel;
import il.ac.hit.model.Item;
import il.ac.hit.model.User;
import il.ac.hit.view.IView;
import javax.swing.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ViewModel implements IViewModel
{
    private IView view;
    private IModel model;
    private int currentID = 0;
    private ExecutorService executorService;

    public ViewModel() {
        this.executorService = Executors.newFixedThreadPool(3);
    }

    @Override
    public void setView(IView view)
    {
        this.view = view;
    }

    @Override
    public void setModel(IModel model)
    {
        this.model = model;
    }

    @Override
    public void handleAuthentication(String email, String password) {
        executorService.submit(() -> {
            try
            {
                currentID = model.selectUserCredentials(email, password);

                if (currentID != 0) { // Login successful
                    view.switchFromLoginWindowToMainWindow();

                    // TODO CHECK IF SHOULD BE REMOVED
                    view.setID(currentID);
                }

                else { // Error logging in
                    view.setID(0);
                    GUIUtils.ShowErrorMessageBox("Error", "No such user exists in DB.");
                }
            }
            catch (CostManException err) {
                GUIUtils.ShowErrorMessageBox("Error", "Error logging in!");
            }
        });

    }

    @Override
    public void handleRegistrationRequest(String email, String password, String fullName)
    {
        executorService.submit(() -> {
            try
            {
                User newUser = new User(email, password, fullName);
                ViewModel.this.currentID = ViewModel.this.model.insertNewUser(newUser);
                ViewModel.this.view.switchFromRegistrationWindowToMainWindow();
                GUIUtils.ShowOkMessageBox("Successful Registration !", "You've been registered" +
                        " successfully!");
            }
            catch (CostManException err)
            {
                GUIUtils.ShowErrorMessageBox("An Error Occurred", err.toString());
            }
        });
    }

    @Override
    public void getItemsBetweenDates(String fromDate, String toDate)
    {
        executorService.submit(() -> {
            List<Item> itemsInRangeOfDates;
            boolean areTwoDatesValid = true;
            try
            {
                areTwoDatesValid = isDateValid(fromDate) || isDateValid(toDate);

                // If one of the dates is invalid
                if (areTwoDatesValid == false)
                {
                    GUIUtils.ShowErrorMessageBox("Invalid Date parameter", "One of the given dates is invalid, please retry.");
                    return;
                }

                itemsInRangeOfDates = model.getItemsByRangeOfDates(currentID, fromDate, toDate);
                SwingUtilities.invokeLater(() -> view.showItems(itemsInRangeOfDates));
            }
            catch (CostManException err)
            {
                GUIUtils.ShowErrorMessageBox("Error", "Problem fetching items!");
            }
        });
    }

    /**
     * Checks if the given input date is valid date or not.
     * @param date - The given date which should be checked.
     * @return true if valid, otherwise false
     */
    public static boolean isDateValid(String date)
    {
        try {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

            // order to do a strict-check with the format and not relying on heuristics.
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public void getCurrenciesRates() {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try{
                    float[] currencies = model.getCurrencies();
                    ViewModel.this.view.saveCurrenciesRates(currencies);
                }
                catch (CostManException e){
                    GUIUtils.ShowErrorMessageBox("Error", e.toString());
                }
            }
        });
    }

    @Override
    public void addCategory(String categoryName, String ownerCategoryName, EnumCategoryType currentCategoryTypeToInsert)
    {
        executorService.submit(() -> {

            boolean isCategoryValid = (categoryName != null) && (categoryName.equals("") == false);
            String outputMessageFormat = "The category \"%s\" has been successfully added.";

            /*
            Primary:
                categoryName is not null and not ""
                +
                ownerCategoryName is null

            Sub-Category:
                categoryName is not null and not ""
                +
                ownerCategoryName is not null and not ""
            */

            // There is an insertion of "primary" category
            if (currentCategoryTypeToInsert == EnumCategoryType.Primary)
            {
                isCategoryValid = isCategoryValid && (ownerCategoryName == null);
            }
            // There is an insertion of sub-category
            else
            {
                isCategoryValid = isCategoryValid && (ownerCategoryName != null) && (ownerCategoryName.equals("") == false);
            }

            // If the categories matches the conditions above accroding to their types
            if (isCategoryValid == true)
            {
                try
                {
                    ViewModel.this.model.insertNewCategory(categoryName, ownerCategoryName);
                    GUIUtils.ShowOkMessageBox("Sub-Category added!", String.format(outputMessageFormat, categoryName));
                    this.getPrimaryCategories();
                }
                catch (CostManException err)
                {
                    GUIUtils.ShowErrorMessageBox("An Error Occurred", err.toString());
                }
            }
            else
            {
                GUIUtils.ShowErrorMessageBox("ERROR", "Invalid parameters for inserting new categories.");
            }
        });
    }

    @Override
    public void addItem(Item item)
    {
       executorService.submit(() -> {
           try {
               model.insertNewItem(item);
           } catch (CostManException e) {
               GUIUtils.ShowErrorMessageBox("Error", "Error adding item!");
           }

           GUIUtils.ShowOkMessageBox("Success", "Item successfully added!");
       });
    }

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
                GUIUtils.ShowOkMessageBox("An Error Occurred", err.toString());
            }

            try
            {
                ViewModel.this.model.deleteItem(ViewModel.this.currentID, integerItemId);
                GUIUtils.ShowOkMessageBox("Success", "Item deleted successfully!");
            }
            catch (CostManException err)
            {
                GUIUtils.ShowOkMessageBox("An Error Occurred", err.toString());
            }
        });
    }

    @Override
    public void logout()
    {
        executorService.submit(() -> {
            boolean result = GUIUtils.ShowYesNoMessageBox("Logout?", "Are you sure you want to logout?");

            if (result)
            {
                ViewModel.this.currentID = 0;
                ViewModel.this.view.openLoginWindowOnlyAndCloseOtherWindows();
            }
        });
    }

    @Override
    public void handleClosingOfFeatureWindow()
    {
        executorService.submit(() -> SwingUtilities.invokeLater(() -> {
            boolean result = GUIUtils.ShowYesNoMessageBox("Get out of this window?",
                    "Are you sure you want to get out of this window?\n" +
                            "All unsaved changes will be lost !");

            if (result) {
                ViewModel.this.view.openMainWindowOnlyAndCloseOtherWindows();
            }
        }));
    }

    /**
     * Retrieve categories by their specified type (PRIMARY or SECONDARY)
     */
    @Override
    public void getPrimaryCategories()
    {
        try
        {
            List<String> categories = this.model.getPrimaryCategories();
            this.view.showCategories(categories, EnumCategoryType.Primary);
        }
        catch (CostManException e)
        {
            GUIUtils.ShowErrorMessageBox("Error", e.toString());
        }
    }

    @Override
    public void getSubCategories(String currentSelectedPrimaryCagtegory)
    {
        try
        {
            List<String> categories = this.model.getSecondaryCategories(currentSelectedPrimaryCagtegory);
            this.view.showCategories(categories, EnumCategoryType.Secondary);
        }
        catch (CostManException e)
        {
            GUIUtils.ShowErrorMessageBox("Error", e.toString());
        }
    }
}