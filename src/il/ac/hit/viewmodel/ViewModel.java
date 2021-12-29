package il.ac.hit.viewmodel;

import il.ac.hit.*;
import il.ac.hit.model.IModel;
import il.ac.hit.model.Item;
import il.ac.hit.model.User;
import il.ac.hit.view.IView;
import il.ac.hit.view.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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

                    // ?
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
        executorService.submit(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    User newUser = new User(email, password, fullName);
                    ViewModel.this.currentID = ViewModel.this.model.insertNewUser(newUser);
                    ViewModel.this.view.switchFromRegistrationWindowToMainWindow();
                    GUIUtils.ShowOkMessageBox("Successful Registration !", "You've been registered successfully !");
                }
                catch (CostManException err)
                {
                    GUIUtils.ShowErrorMessageBox("An Error Occured", err.toString());
                }
            }
        });
    }

    @Override
    public void getItemsBetweenDates(String fromDate, String toDate)
    {
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
    }

    @Override
    public void addCategory(String categoryName, String ownerCategoryName)
    {
        executorService.submit(new Runnable()
        {
            @Override
            public void run()
            {
                // Check if the 2 input string are letters-only (Source: https://stackoverflow.com/a/29836318/2196301)
                boolean isCategoryHasLettersOnly = categoryName.chars().allMatch(Character::isLetter);
                boolean isOwnerCategoryHasLettersOnly = ownerCategoryName.chars().allMatch(Character::isLetter);

                CostManException illegalCategoryOrOwnerCategory = null;

                if (isCategoryHasLettersOnly == false)
                {
                    illegalCategoryOrOwnerCategory = new CostManException("Invalid Category Name !", new IllegalArgumentException());
                }
                if (isOwnerCategoryHasLettersOnly == false)
                {
                    illegalCategoryOrOwnerCategory = new CostManException("Invalid Owner Category Name !", new IllegalArgumentException());
                }

                if (illegalCategoryOrOwnerCategory != null)
                {
                    GUIUtils.ShowErrorMessageBox("Invalid Parameter !", illegalCategoryOrOwnerCategory.toString());
                    return;
                }

                try
                {
                    ViewModel.this.model.insertNewCategory(categoryName, ownerCategoryName);
                }
                catch (CostManException err)
                {
                    GUIUtils.ShowErrorMessageBox("An Error Occured", err.toString());
                }
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
        executorService.submit(new Runnable()
        {
            @Override
            public void run()
            {
                int integerItemId = -1;

                try
                {
                    integerItemId = Integer.valueOf(itemID);
                }
                catch (NumberFormatException err)
                {
                    GUIUtils.ShowOkMessageBox("An Error Occured", err.toString());
                }

                try
                {
                    ViewModel.this.model.deleteItem(ViewModel.this.currentID, integerItemId);
                    GUIUtils.ShowOkMessageBox("Success", "Item deleted successfully!");
                }
                catch (CostManException err)
                {
                    GUIUtils.ShowOkMessageBox("An Error Occured", err.toString());
                }
            }
        });
    }

    @Override
    public void logout()
    {
        executorService.submit(new Runnable()
        {
            @Override
            public void run()
            {
                boolean result = GUIUtils.ShowYesNoMessageBox("Logout?", "Are you sure you want to logout?");

                if (result == true)
                {
                    ViewModel.this.currentID = 0;
                    ViewModel.this.view.openLoginWindowOnlyAndCloseOtherWindows();
                }
            }
        });
    }

    @Override
    public void handleClosingOfFeatureWindow()
    {
        executorService.submit(new Runnable()
        {
            @Override
            public void run()
            {
                boolean result = GUIUtils.ShowYesNoMessageBox("Get out of this window?", "Are you sure you want to get out of this window?\nAll unsaved changes will be lost !");

                if (result == true)
                {
                    ViewModel.this.view.openMainWindowOnlyAndCloseOtherWindows();
                }
            }
        });
    }

}
