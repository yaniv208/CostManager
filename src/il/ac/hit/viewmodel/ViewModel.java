package il.ac.hit.viewmodel;

import il.ac.hit.*;
import il.ac.hit.model.IModel;
import il.ac.hit.model.Item;
import il.ac.hit.view.IView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ViewModel implements IViewModel
{
    private IView view;
    private IModel model;
    private int currentID;
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
                    view.setIsConnected(true);
                    view.setID(currentID);
                }

                else { // Error logging in
                    view.setIsConnected(false);
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
    public void handleRegistrationRequest()
    {
        /*
        try
        {

        }
        catch (CostManException err)
        {

        }
        finally
        {

        }
        */
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
        /*
        try
        {
        }
        catch (CostManException err)
        {

        }
        finally
        {

        }
        */
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
    public void deleteItem(int itemID)
    {
        executorService.submit(() -> {
            try {
                model.deleteItem(currentID, itemID);
            } catch (CostManException e) {
                GUIUtils.ShowErrorMessageBox("Error", "Error deleting item!");
            }
            GUIUtils.ShowOkMessageBox("Success", "Item deleted successfully!");
        });
    }

    @Override
    public void logout()
    {
        /*
        try
        {
        }
        catch (CostManException err)
        {

        }
        finally
        {

        }
        */
    }

}
