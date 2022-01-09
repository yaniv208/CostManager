package il.ac.hit.model;

import il.ac.hit.CostManException;
import java.util.List;

/**
 * This class represents the Interface of the Model
 */
public interface IModel {

    /**
     * A function that inserts a new user to the database.
     * @param user - A parameter representing user, containing strings of email address and a password.
     * @return ID of the inserted user, if successful
     * @throws CostManException if insertion wasn't successful
     */
    public int insertNewUser(User user) throws CostManException;

    /**
     * A function that verifies the user's input with the database and showing corresponding message.
     * @param email - email address written on login page
     * @param password - email address written on login page
     * @return ID of the specific user on the database, 0 if null/not found
     * @throws CostManException if login wasn't successful
     */
    public int selectUserCredentials(String email, String password) throws CostManException;

    /**
     * A function that returns data by a specific range of dates
     * @param userId - user ID of the requesting user
     * @param from - origin date
     * @param to - destination date
     * @return - A specific list of items selected by range of dates
     * @throws CostManException if fetching items by dates wasn't successful
     */
    public List<Item> getItemsByRangeOfDates(int userId, String from, String to) throws CostManException;

    /**
     * A function that inserts a new item into the database
     * @param item - represents an item that would be inserted into database
     * @throws CostManException if insertion of a new item wasn't successful
     */
    public void insertNewItem(Item item) throws CostManException;

    /**
     * A function that deletes a specific item from the database
     * @param OwnerUserId - represents specific user ID whose belonged item to be deleted.
     * @param itemId - represents specific item ID to be deleted.
     * @throws CostManException if deletion of an item wasn't successful
     */
    public void deleteItem(int OwnerUserId, int itemId) throws CostManException;

    /**
     * Retrieve categories by their specified type (PRIMARY or SECONDARY)
     * @param requestedCategoriesType - The type of the requested categories which should be retrieved.
     * @return - The list of the categories from the requested type.
     * @throws CostManException if fetching categories wasn't successful
     */
    public List<String> getCategoriesByCategoryType(EnumCategoryType requestedCategoriesType) throws CostManException;

    public List<String> getPrimaryCategories() throws CostManException;

    public List<String> getSecondaryCategories(String currentPrimaryCategory) throws CostManException;

    /**
     * Insert new category to the Database
     * @param newCategoryName - The new category name
     * @param ownerCategoryName - The Owner of the current category.
     *                          If the current category is a PRIMARY category, this value should be null,
     *                          otherwise the name of the category owner.
     * @throws CostManException if insertion of a new category wasn't successful
     */
    public void insertNewCategory(String newCategoryName, String ownerCategoryName) throws CostManException;

    /**
     * Retrieve the requested category ID by its name
     * @param categoryName - The name of the category which should be resolved to it's corresponding ID
     * @return - The corresponding ID of the given category name
     * @throws CostManException if fetching a category ID by name wasn't successful
     */
    public int getCategoryIDByCategoryName(String categoryName) throws CostManException;

    /**
     * Retrieve the requested category name by its ID
     * @param categoryId - The ID of the category which should be resolved to it's corresponding name
     * @return - The corresponding name of the given category ID
     * @throws CostManException if fetching a category name by ID wasn't successful
     */
    public String getCategoryNameByCategoryID(int categoryId) throws CostManException;

    public float[] getCurrencies() throws CostManException;
}
