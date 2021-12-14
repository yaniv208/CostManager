package il.ac.hit;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.sql.Types.NULL;

public class DBModel implements IModel
{
    private String dbDriverName = "com.mysql.jdbc.Driver";
    private String dbProtocol = "jdbc:mysql://localhost:3306/costman";
    private String dbUserName = "root";
    private String dbPassword = "123456";

    private static DBModel instance = new DBModel();

    public static DBModel getInstance()
    {
        return DBModel.instance;
    }

    // Insertion of new user to DataBase

    /************************
     General Functions
     ************************/

    /**
     * @param inputPreparedStatement - The prepared statement which should be terminated
     * @param inputConnection        - The connection which should be terminated
     * @param inputResultSet         - The result set which should be terminated
     * @throws CostManException
     */
    private void cleanupUpdateUsageProcess(PreparedStatement inputPreparedStatement, Connection inputConnection, ResultSet inputResultSet) throws CostManException
    {
        if (inputConnection != null)
        {
            try
            {
                inputConnection.close();
            }
            catch (Exception e)
            {
                throw new CostManException("A problem occurred while closing inputConnection.", e);
            }
        }

        if (inputPreparedStatement != null)
        {
            try
            {
                inputPreparedStatement.close();
            }
            catch (SQLException e)
            {
                throw new CostManException("A problem occurred while closing Prepared Statement!", e);
            }
        }
        if (inputResultSet != null)
        {
            try
            {
                inputResultSet.close();
            }
            catch (SQLException e)
            {
                throw new CostManException("A problem occurred while closing ResultSet!", e);
            }
        }
    }

    /************************************************
     Functions for Users Registration Handling
     ************************************************/

    /**
     * @param email
     * @param password
     * @throws CostManException
     */
    public void insertNewUser(String email, String password) throws CostManException
    {
        PreparedStatement ps = null;
        Connection connection = null;

        try
        {
            Class.forName(dbDriverName);
            connection = DriverManager.getConnection(dbProtocol, dbUserName, dbPassword);
            ps = connection.prepareStatement("INSERT INTO users VALUES (?, ?,?)");

            ps.setInt(1, NULL);
            ps.setString(2, email);
            ps.setString(3, password);

            if (ps.executeUpdate() != 1)
            {
                throw new CostManException("Problem with registering user!");
            }

        }
        catch (SQLException | ClassNotFoundException e)
        {
            throw new CostManException("Problem with registering user!", e);

        }
        finally
        {
            cleanupUpdateUsageProcess(ps, connection, null);
        }
    }

    /*****************************************
     Functions for Users Login Handling
     ****************************************/

    // verifying the user's input with the database and showing corresponding message
    public int selectUserCredentials(String email, String password) throws CostManException
    {
        PreparedStatement ps = null;
        Connection connection = null;
        ResultSet rs = null;
        int userId = 0;

        if (email.length() == 0 || password.length() == 0)
        {
            throw new CostManException("One or more of the details are missing!");
        }

        try
        {
            Class.forName(dbDriverName);
            connection = DriverManager.getConnection(dbProtocol, dbUserName, dbPassword);
            ps = connection.prepareStatement("SELECT UserID from users WHERE Email = ? AND Password = ?");

            ps.setString(1, email);
            ps.setString(2, password);

            rs = ps.executeQuery();

            while (rs.next())
            { // getting ID of the specific user, only 1 ID returned.
                userId = rs.getInt("UserID");
            }

        }
        catch (SQLException | ClassNotFoundException e)
        {
            throw new CostManException("Error logging in!", e);
        }
        finally
        {
            cleanupUpdateUsageProcess(ps, connection, rs);
        }

        return userId;
    }

    /****************************************
     Functions for Categories Handling
     ****************************************/

    /**
     *
     * @param requestedCategoriesType - The type of the requested categories which should be retrieved.
     * @return - The list of the categories from the requested type.
     * @throws CostManException
     */
    public List<String> getCategoriesByCategoryType(EnumCategoryType requestedCategoriesType) throws CostManException {
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        String finalQueryTemplate = "SELECT CategoryName FROM categories WHERE OwnerCategoryID IS%sNULL ORDER BY CategoryName ASC";
        String finalQuery;
        String categoryPrimaryIndicator = " ";

        List<String> allRequestedCategories = null;

        // Decide the indicator value according to the value of "requestedCategoriesType"
        switch (requestedCategoriesType) {
            case Primary:
                categoryPrimaryIndicator = " ";
                break;
            case Secondary:
                categoryPrimaryIndicator = " NOT ";
                break;
        }

        // Concat the indicator string's value to the general query.
        finalQuery = String.format(finalQueryTemplate, categoryPrimaryIndicator);

        try {

            Class.forName(dbDriverName);
            connection = DriverManager.getConnection(dbProtocol, dbUserName, dbPassword);
            ps = connection.prepareStatement(finalQuery);

            // Run the query
            rs = ps.executeQuery();

            // Initialize the returned list
            allRequestedCategories = new ArrayList<String>();

            // Get all the Categories
            while (rs.next()) {
                allRequestedCategories.add(rs.getString("CategoryName"));
            }

        } catch (Exception e)
        {
            throw new CostManException("Can't retrieve the categories from the DB.", e);
        }
        finally
        {
            cleanupUpdateUsageProcess(ps, connection, rs);
        }

        return allRequestedCategories;
    }

    /**
     *
     * @param newCategoryName - The new category name
     * @param ownerCategoryName - The Owner of the current category.
     *                          If the current category is a PRIMARY category, this value should be null,
     *                          otherwise the name of the category owner.
     * @throws CostManException
     */
    public void insertNewCategory(String newCategoryName, String ownerCategoryName) throws CostManException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String finalQueryTemplate = "INSERT INTO Categories VALUES (NULL, ?, ?)";
        String categoryPrimaryIndicator;

        if (ownerCategoryName == null) {
            categoryPrimaryIndicator = null;
        } else {
            categoryPrimaryIndicator = String.valueOf(getCategoryIDByCategoryName(ownerCategoryName));
        }

        try {
            Class.forName(dbDriverName);
            connection = DriverManager.getConnection(dbProtocol, dbUserName, dbPassword);

            ps = connection.prepareStatement(finalQueryTemplate);
            ps.setString(1, newCategoryName);
            ps.setString(2, categoryPrimaryIndicator);

            if (ps.executeUpdate() != 1)
            {
                throw new CostManException("Error while trying to insert a new category!");
            }
        }
        catch (Exception e) {
            throw new CostManException("Cannot insert the new category to the DB", e);
        }
        finally
        {
            cleanupUpdateUsageProcess(ps, connection, rs);
        }
    }

    /**
     *
     * @param categoryName - The name of the category which should be resolved to it's corresponding ID
     * @return - The corresponding ID of the given category name
     * @throws CostManException
     */
    public int getCategoryIDByCategoryName(String categoryName) throws CostManException {
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        String finalQueryTemplate = "SELECT CategoryID FROM categories WHERE CategoryName=?";

        // In case where the input category name does not exists, this default value should be returned.
        int returnValue = 0;

        try {
            Class.forName(dbDriverName);
            connection = DriverManager.getConnection(dbProtocol, dbUserName, dbPassword);
            ps = connection.prepareStatement(finalQueryTemplate);

            ps.setString(1, categoryName);

            // Run the query
            rs = ps.executeQuery();

            // Get the requested category ID
            rs.next();
            returnValue = rs.getInt("CategoryID");

        } catch (Exception e) {
            throw new CostManException(String.format("Cannot convert the category name \"%s\" to a category id", categoryName), e);
        }
        finally
        {
            cleanupUpdateUsageProcess(ps, connection, rs);
        }

        return returnValue;
    }

    /**
     *
     * @param categoryId - The ID of the category which should be resolved to it's corresponding name
     * @return - The corresponding name of the given category ID
     * @throws CostManException
     */
    public String getCategoryNameByCategoryID(int categoryId) throws CostManException {
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        String finalQueryTemplate = "SELECT CategoryName FROM categories WHERE CategoryID=?";
        String returnValue = null;

        try {
            Class.forName(dbDriverName);
            connection = DriverManager.getConnection(dbProtocol, dbUserName, dbPassword);
            ps = connection.prepareStatement(finalQueryTemplate);
            ps.setInt(1, categoryId);

            // Run the query
            rs = ps.executeQuery();

            // Get the requested category ID
            rs.next();
            returnValue = rs.getString("CategoryName");
        } catch (Exception e) {
            throw new CostManException(String.format("Cannot convert the category id \"%d\" to a category name", categoryId), e);
        }
        finally
        {
            cleanupUpdateUsageProcess(ps, connection, rs);
        }

        return returnValue;
    }

    /**********************************
     Functions for Reports Handling
     **********************************/

    // get specific list of items selected by range of dates
    public Collection<Item> getDataByRangeOfDates(int userId, String from, String to) throws CostManException
    {
        PreparedStatement ps = null;
        Connection connection = null;
        ResultSet rs = null;
        Collection<Item> items = null;
        Item itemToAdd = null;

        try
        {
            Class.forName(dbDriverName);
            connection = DriverManager.getConnection(dbProtocol, dbUserName, dbPassword);
            ps = connection.prepareStatement("SELECT * FROM items WHERE OwnerUserID = ? AND (Date BETWEEN ? AND ?)");

            ps.setInt(1, userId);
            ps.setString(2, from);
            ps.setString(3, to);

            rs = ps.executeQuery();
            items = new ArrayList<Item>();

            while (rs.next())
            { // assign variables of each item, and adding it to collection
                itemToAdd = new Item();
                itemToAdd.setItemId(rs.getInt("ItemID"));
                itemToAdd.setUserId(rs.getInt("OwnerUserID"));
                itemToAdd.setCategoryId(rs.getInt("CategoryID"));
                itemToAdd.setSubCategoryId(rs.getInt("SubCategoryID"));
                itemToAdd.setDate(rs.getString("Date"));
                itemToAdd.setPrice(rs.getInt("Price"));
                itemToAdd.setCurrencyRate(rs.getFloat("CurrencyRate"));
                itemToAdd.setDescription(rs.getString("Description"));
                items.add(itemToAdd);
                itemToAdd = null;
            }

        }
        catch (SQLException | ClassNotFoundException e)
        {
            throw new CostManException("Error getting data between dates!", e);
        }
        finally
        {
            cleanupUpdateUsageProcess(ps, connection, rs);
        }

        return items;
    }

    /***********************************
     Functions for Items Handling
     ***********************************/

    public void insertNewItem(Item item) throws CostManException
    {
        PreparedStatement ps = null;
        Connection connection = null;

        try
        {
            Class.forName(dbDriverName);
            connection = DriverManager.getConnection(dbProtocol, dbUserName, dbPassword);
            ps = connection.prepareStatement("INSERT INTO items VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            ps.setInt(1, NULL);
            ps.setInt(2, item.getUserId());
            ps.setInt(3, item.getCategoryId());
            ps.setInt(4, item.getSubCategoryId());
            ps.setString(5, item.getDate());
            ps.setInt(6, item.getPrice());
            ps.setFloat(7, item.getCurrencyRate());
            ps.setString(8, item.getDescription());


            if (ps.executeUpdate() != 1)
                throw new CostManException("Error while inserting into DataBase!");

        }
        catch (SQLException | ClassNotFoundException e)
        {
            throw new CostManException("Error while inserting into DataBase!", e);
        }
        finally
        {
            cleanupUpdateUsageProcess(ps, connection, null);
        }
    }

    public void deleteItem(int OwnerUserId, int itemId) throws CostManException
    {
        PreparedStatement ps = null;
        Connection connection = null;

        try
        {
            Class.forName(dbDriverName);
            connection = DriverManager.getConnection(dbProtocol, dbUserName, dbPassword);
            ps = connection.prepareStatement("DELETE FROM items WHERE ItemID = ? AND OwnerUserID = ?");

            ps.setInt(1, itemId);
            ps.setInt(2, OwnerUserId);

            if (ps.executeUpdate() != 1)
            {
                throw new CostManException("Error deleting item!");
            }

        }
        catch (SQLException | ClassNotFoundException e)
        {
            throw new CostManException("Error deleting item!", e);
        }
        finally
        {
            cleanupUpdateUsageProcess(ps, connection, null);
        }
    }
}
