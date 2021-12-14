package il.ac.hit;

import java.util.Collection;
import java.util.List;

public interface IModel {
    void insertNewUser(String email, String password) throws CostManException;
    int selectUserCredentials(String email, String password) throws CostManException;
    Collection<Item> getDataByRangeOfDates(int userId, String from, String to) throws CostManException;
    void insertNewItem(Item item) throws CostManException;
    void deleteItem(int OwnerUserId, int itemId) throws CostManException;
    List<String> getCategoriesByCategoryType(EnumCategoryType requestedCategoriesType) throws CostManException;
    void insertNewCategory(String newCategoryName, String ownerCategoryName) throws CostManException;
    int getCategoryIDByCategoryName(String categoryName) throws CostManException;
    String getCategoryNameByCategoryID(int categoryId) throws CostManException;
}
