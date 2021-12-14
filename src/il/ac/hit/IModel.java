package il.ac.hit;

import java.util.Collection;
import java.util.List;

/**
 * This class represents the Interface of the Model
 */
public interface IModel {
    void insertNewUser(String email, String password) throws CostManException;
    int selectUserCredentials(String email, String password) throws CostManException;
    Collection<Item> getDataByRangeOfDates(int userId, String from, String to) throws CostManException;
    void insertNewItem(Item item) throws CostManException;
    void deleteItem(int OwnerUserId, int itemId) throws CostManException;

    /**
     * Retrieve categories by their specified type (PRIMARY or SECONDARY)
     * @param requestedCategoriesType - The type of the requested categories which should be retrieved.
     * @return - The list of the categories from the requested type.
     * @throws CostManException
     */
    List<String> getCategoriesByCategoryType(EnumCategoryType requestedCategoriesType) throws CostManException;

    /**
     * Insert new category to the Database
     * @param newCategoryName - The new category name
     * @param ownerCategoryName - The Owner of the current category.
     *                          If the current category is a PRIMARY category, this value should be null,
     *                          otherwise the name of the category owner.
     * @throws CostManException
     */
    void insertNewCategory(String newCategoryName, String ownerCategoryName) throws CostManException;

    /**
     * Retrieve the requested category ID by its name
     * @param categoryName - The name of the category which should be resolved to it's corresponding ID
     * @return - The corresponding ID of the given category name
     * @throws CostManException
     */
    int getCategoryIDByCategoryName(String categoryName) throws CostManException;

    /**
     * Retrieve the requested category name by its ID
     * @param categoryId - The ID of the category which should be resolved to it's corresponding name
     * @return - The corresponding name of the given category ID
     * @throws CostManException
     */
    String getCategoryNameByCategoryID(int categoryId) throws CostManException;
}
