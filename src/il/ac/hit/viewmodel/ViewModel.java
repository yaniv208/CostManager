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
            try
            {
                itemsInRangeOfDates = model.getItemsByRangeOfDates(currentID, fromDate, toDate);
                SwingUtilities.invokeLater(() -> view.showItems(itemsInRangeOfDates));
            }
            catch (CostManException err)
            {
                GUIUtils.ShowErrorMessageBox("Error", "Problem fetching items!");
            }
        });
    }

    @Override
    public void addCategory(String categoryName, String ownerCategoryName)
    {
        executorService.submit(() -> {
            // Check if the 2 input string are letters-only (Source: https://stackoverflow.com/a/29836318/2196301)
            boolean isCategoryValid = (!ownerCategoryName.equals(""));
            boolean isOwnerCategoryValid = !ownerCategoryName.equals("");

            if (!isCategoryValid)
            {
                GUIUtils.ShowErrorMessageBox("Error", "Invalid Category Name!");
            }
            if (!isOwnerCategoryValid)
            {
                GUIUtils.ShowErrorMessageBox("Error", "Invalid Owner Category Name!");
            }

            try
            {
                ViewModel.this.model.insertNewCategory(categoryName, null);
                GUIUtils.ShowOkMessageBox("Success", "The category \"%s\" has been successfully added.");
            }
            catch (CostManException err)
            {
                GUIUtils.ShowErrorMessageBox("An Error Occurred", err.toString());
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
                currentID = 0;
                view.openLoginWindowOnlyAndCloseOtherWindows();
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
                view.openMainWindowOnlyAndCloseOtherWindows();
            }
        }));
    }

    /**
     * Retrieve categories by their specified type (PRIMARY or SECONDARY)
     */
    @Override
    public void getPrimaryCategories()
    {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try
                {
                    List<String> categories = model.getPrimaryCategories();
                    view.showCategories(categories, EnumCategoryType.Primary);
                }
                catch (CostManException e)
                {
                    GUIUtils.ShowErrorMessageBox("Error", e.toString());
                }
            }
        });

    }

    @Override
    public void getSubCategories(String currentSelectedPrimaryCagtegory)
    {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try
                {
                    List<String> categories = model.getSecondaryCategories(currentSelectedPrimaryCagtegory);
                    view.showCategories(categories, EnumCategoryType.Secondary);
                }
                catch (CostManException e)
                {
                    GUIUtils.ShowErrorMessageBox("Error", e.toString());
                }
            }
        });

    }
}