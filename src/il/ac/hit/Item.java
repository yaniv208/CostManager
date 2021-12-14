package il.ac.hit;

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

    public Item(int userId, int categoryId, int subCategoryId, String date, int price, float currencyRate, String description) {
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

    public void setDate(String date) {
        this.date = date;
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

    public void setDescription(String description) {
        this.description = description;
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
}
