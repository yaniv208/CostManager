package il.ac.hit;

import java.util.Collection;
import java.util.List;

public class ViewModel implements IViewModel
{
    private IView view;
    private IModel model;
    private int currentID;

    public ViewModel()
    {
    }

    @Override
    public void setView(IView view)
    {
        this.view = view;
    }

    @Override
    public void handleAuthentications(String userName, String password)
    {
        boolean isAuthenticated;
        try
        {
            this.currentID = this.model.selectUserCredentials(userName, password);

            isAuthenticated = currentID != 0;

            if(isAuthenticated == true)
            {
                // Activate main menu
            }
        }
        catch (CostManException err)
        {
        }
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
        Collection<Item> itemsInRangeOfDates;
        try
        {
            itemsInRangeOfDates = this.model.getDataByRangeOfDates(this.currentID, fromDate, toDate);
        }
        catch (CostManException err)
        {
            // Popup a messagebox in the ui
            GUIUtils.ShowErrorMessageBox("Wowwwww THIS IS A STRONG MESSAGE", err.toString());
        }
        finally
        {
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

    @Override
    public void setModel(IModel model)
    {
        this.model = model;
    }
}
