package il.ac.hit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Collection;
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
    public void handleAuthentication(String email, String password)
    {
        executorService.submit(new Runnable() {
            @Override
            public void run() {

                try
                {
                    boolean isAuthenticated;
                    currentID = model.selectUserCredentials(email, password);
                    isAuthenticated = currentID != 0;

                    if (isAuthenticated)
                    {
                        // Activate main menu
                        // view.setUserID(currentID);

                        // close login window and open main window
                    }
                }
                catch (CostManException err)
                {

                }
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
    public void getItems(String fromDate, String toDate)
    {
        DefaultTableModel itemsInRangeOfDates;
        try
        {
            itemsInRangeOfDates = this.model.getItemsByRangeOfDates(this.currentID, fromDate, toDate);

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    // view.showItems(itemsInRangeOfDates);
                }
            });
        }
        catch (CostManException err)
        {
            // Popup a messagebox in the UI
            // GUIUtils.showErrorMessageBox("Error fetching items!", err.toString());
        }
        finally {
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
        /*
        try
        {
            this.model.deleteItem();
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
    public void deleteItem(int itemID)
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
