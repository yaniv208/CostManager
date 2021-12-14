package il.ac.hit;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.sql.Types.NULL;

public class DBModel {

    public static String driver = "com.mysql.jdbc.Driver";
    public static String protocol = "jdbc:mysql://localhost:3306/costsaviv";

    // Insertion of new user to DataBase

    /**
     *
     * @param email
     * @param password
     * @throws CostManException
     */
    public static void insertNewUser(String email, String password) throws CostManException {
        PreparedStatement ps = null;
        Connection connection = null;

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(protocol, "root", "123456");
            ps = connection.prepareStatement("INSERT INTO users VALUES (?, ?,?)");

            ps.setInt(1, NULL);
            ps.setString(2, email);
            ps.setString(3, password);

            if (ps.executeUpdate() != 1) {
                throw new CostManException("Problem with registering user!");
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new CostManException("Problem with registering user!", e);

        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new CostManException("A problem occurred while closing connection!", e);
                }
            }

            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new CostManException("Problem with closing Prepared Statement!", e);
                }
            }
        }

    }

    // verifying the user's input with the database and showing corresponding message
    public static int selectUserCredentials(String email, String password) throws CostManException {
        PreparedStatement ps = null;
        Connection connection = null;
        ResultSet rs = null;
        int userId = 0;

        if (email.length() == 0 || password.length() == 0) {
            throw new CostManException("One or more of the details are missing!");
        }

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(protocol, "root", "123456");
            ps = connection.prepareStatement("SELECT UserID from users WHERE Email = ? AND Password = ?");

            ps.setString(1, email);
            ps.setString(2, password);

            rs = ps.executeQuery();

            while (rs.next()) { // getting ID of the specific user, only 1 ID returned.
                userId = rs.getInt("UserID");
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new CostManException("Error logging in!", e);
        } finally {
            finallyBlockMethods(ps, connection, rs);
        }

        return userId;
    }

    // get specific list of items selected by range of dates
    public static Collection<Item> getDataByRangeOfDates(int userId, String from, String to) throws CostManException {
        PreparedStatement ps = null;
        Connection connection = null;
        ResultSet rs = null;
        Collection<Item> items = null;
        Item itemToAdd = null;

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(protocol, "root", "123456");
            ps = connection.prepareStatement("SELECT * FROM items WHERE OwnerUserID = ? AND (Date BETWEEN ? AND ?)");

            ps.setInt(1, userId);
            ps.setString(2, from);
            ps.setString(3, to);

            rs = ps.executeQuery();
            items = new ArrayList<Item>();

            while (rs.next()) { // assign variables of each item, and adding it to collection
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

        } catch (SQLException | ClassNotFoundException e) {
            throw new CostManException("Error getting data between dates!", e);
        } finally {
            finallyBlockMethods(ps, connection, rs);
        }

        return items;
    }

    public static void insertNewItem(Item item) throws CostManException {
        PreparedStatement ps = null;
        Connection connection = null;

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(protocol, "root", "123456");
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

        } catch (SQLException | ClassNotFoundException e) {
            throw new CostManException("Error while inserting into DataBase!", e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    throw new CostManException("A problem occurred while closing connection.", e);
                }
            }

            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new CostManException("Problem occurred while closing Prepared Statement!", e);
                }
            }
        }
    }

    public static void deleteItem(int OwnerUserId, int itemId) throws CostManException {
        PreparedStatement ps = null;
        Connection connection = null;

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(protocol, "root", "123456");
            ps = connection.prepareStatement("DELETE FROM items WHERE ItemID = ? AND OwnerUserID = ?");

            ps.setInt(1, itemId);
            ps.setInt(2, OwnerUserId);

            if (ps.executeUpdate() != 1) {
                throw new CostManException("Error deleting item!");
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new CostManException("Error deleting item!", e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    throw new CostManException("A problem occurred while closing connection.", e);
                }
            }

            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new CostManException("A problem occurred while closing Prepared Statement!", e);
                }
            }
        }

    }

    private static void finallyBlockMethods(PreparedStatement ps, Connection connection,
                                            ResultSet rs) throws CostManException {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                throw new CostManException("A problem occurred while closing connection.", e);
            }
        }

        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                throw new CostManException("A problem occurred while closing Prepared Statement!", e);
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new CostManException("A problem occurred while closing ResultSet!", e);
            }
        }
    }

    public static List<String> getCategoriesByCategoryType(EnumCategoryType requestedCategoriesType) throws CostManException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        String finalQueryTemplate = "SELECT CategoryName FROM categories WHERE OwnerCategoryID IS%sNULL ORDER BY CategoryName ASC";
        String finalQuery;
        String categoryPrimaryIndicator = " ";

        List<String> allRequestedCategories = null;

        switch (requestedCategoriesType) {
            case Primary:
                categoryPrimaryIndicator = " ";
                break;
            case Secondary:
                categoryPrimaryIndicator = " NOT ";
                break;
        }

        finalQuery = String.format(finalQueryTemplate, categoryPrimaryIndicator);

        try {

            Class.forName(driver);
            connection = DriverManager.getConnection(protocol, "root", "123456");
            statement = connection.createStatement();

            // Run the query
            rs = statement.executeQuery(finalQuery);

            // Initialize the returned list
            allRequestedCategories = new ArrayList<String>();

            // Get all the Categories
            while (rs.next()) {
                allRequestedCategories.add(rs.getString("CategoryName"));
            }

        } catch (Exception e) {
            throw new CostManException("Can't retrieve the categories from the DB. please try again later.", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    throw new CostManException("A problem occurred while closing statement.", e);
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    throw new CostManException("A problem occurred while closing connection.", e);
                }
            }

            if (rs != null){
                try {
                    rs.close();
                } catch (Exception e) {
                    throw new CostManException("A problem occurred while closing ResultSet.", e);
                }
            }
        }

        return allRequestedCategories;
    }

    public static void insertNewCategory(String newCategoryName, String ownerCategoryName) throws CostManException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        String finalQueryTemplate = "INSERT INTO Categories VALUES (NULL, \"%s\", %s)";
        String finalQuery;
        String categoryPrimaryIndicator;

        if (ownerCategoryName == null) {
            categoryPrimaryIndicator = "NULL";
        } else {
            categoryPrimaryIndicator = String.valueOf(getCategoryIDByCategoryName(ownerCategoryName));
        }

        finalQuery = String.format(finalQueryTemplate, newCategoryName, categoryPrimaryIndicator);

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(protocol, "root", "123456");
            statement = connection.createStatement();

            // WORKING WITHOUT SETTING ID
            if (statement.executeUpdate(finalQuery) != 1) {
                throw new CostManException("Error while trying to insert a new category!");
            }
        }
        catch (Exception e) {
            throw new CostManException("Cannot insert the new category to the DB, please try again later");
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    throw new CostManException("A problem occurred while trying to close statement.", e);
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    throw new CostManException("A problem occurred while trying to close connection.", e);
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    throw new CostManException("A problem occurred while trying to close ResultSet.", e);
                }
            }
        }
    }

    public static int getCategoryIDByCategoryName(String categoryName) throws CostManException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        String finalQueryTemplate = "SELECT CategoryID FROM categories WHERE CategoryName=\"%s\"";
        String finalQuery;

        int returnValue = -1;

        finalQuery = String.format(finalQueryTemplate, categoryName);

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(protocol, "root", "123456");
            statement = connection.createStatement();

            // Run the query
            rs = statement.executeQuery(finalQuery);

            // Get the requested category ID
            rs.next();
            returnValue = rs.getInt("CategoryID");
        } catch (Exception e) {
            throw new CostManException(String.format("Cannot convert the category name \"%s\" to a category id", categoryName));
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    throw new CostManException("A problem occurred while trying to close statement.", e);
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    throw new CostManException("A problem occurred while trying to close connection.", e);
                }
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    throw new CostManException("A problem occurred while trying to close ResultSet.", e);
                }
            }
        }

        return returnValue;
    }

    public String getCategoryNameByCategoryID(int categoryId) throws CostManException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        String finalQueryTemplate = "SELECT CategoryName FROM categories WHERE CategoryID=\"%d\"";
        String finalQuery;

        String returnValue = null;

        finalQuery = String.format(finalQueryTemplate, categoryId);

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(protocol, "root", "123456");
            statement = connection.createStatement();

            // Run the query
            rs = statement.executeQuery(finalQuery);

            // Get the requested category ID
            returnValue = rs.getString("CategoryName");
        } catch (Exception e) {
            throw new CostManException(String.format("Cannot convert the category id \"%d\" to a category name", categoryId));
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    throw new CostManException("A problem occurred while trying to close statement.", e);
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    throw new CostManException("A problem occurred while trying to close connection.", e);
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    throw new CostManException("A problem occurred while trying to close ResultSet.", e);
                }
            }
        }

        return returnValue;
    }
}
