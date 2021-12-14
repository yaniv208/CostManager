package il.ac.hit;

import java.util.Objects;

/**
 * This class represents an item and its characteristics, such as:
 * itemId - represents item ID in the database. each item ID is basically related to a specific user ID
 * userId - represents user ID in the database
 * categoryId - represents MAIN category ID in the database.
 * subCategoryId - represents sub-category ID in the database. if sub-category (of a main one)
 *                 does not exist, then value would be null.
 * date - represents the specific date of the expense.
 * price - represents the specific price of the expense.
 * currencyRate - represents the specific currency rate of the expense.
 * description - represents a short description of the expense.
 */
public class Item {
    int itemId;
    int userId;
    int categoryId;
    int subCategoryId;
    String date;
    int price;
    float currencyRate;
    String description;

    public Item() {}

    public Item(int userId, int categoryId, int subCategoryId, String date, int price, float currencyRate, String description) throws CostManException {
        setUserId(userId);
        setCategoryId(categoryId);
        setSubCategoryId(subCategoryId);
        setDate(date);
        setPrice(price);
        setCurrencyRate(currencyRate);
        setDescription(description);
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) throws CostManException {
        if (date.length() > 0) {
            this.date = date;
        }
        else {
            throw new CostManException("Must specify date!");
        }
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public float getCurrencyRate() {
        return currencyRate;
    }

    public void setCurrencyRate(float currencyRate) {
        this.currencyRate = currencyRate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws CostManException {
        if (description.length() > 4) {
            this.description = description;
        }
        else {
            throw new CostManException("Description must be longer than 4 chars!");
        }
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", userId=" + userId +
                ", categoryId=" + categoryId +
                ", subCategoryId=" + subCategoryId +
                ", date='" + date + '\'' +
                ", price=" + price +
                ", currencyRate=" + currencyRate +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return getItemId() == item.getItemId() && getUserId() == item.getUserId() && getCategoryId() == item.getCategoryId() && getSubCategoryId() == item.getSubCategoryId() && getPrice() == item.getPrice() && Float.compare(item.getCurrencyRate(), getCurrencyRate()) == 0 && getDate().equals(item.getDate()) && getDescription().equals(item.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getItemId(), getUserId(), getCategoryId(), getSubCategoryId(), getDate(), getPrice(), getCurrencyRate(), getDescription());
    }
}
