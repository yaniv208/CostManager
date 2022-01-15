package il.ac.hit.model;

import il.ac.hit.CostManException;

/**
 * This class represents an expense and its characteristics.
 */
public class Item {
    int itemId, userId, price;
    float currencyRate;
    String category, subCategory, date, currency, description;

    /**
     * Empty public constructor, to be used during the program
     */
    public Item() {}

    /**
     * A constructor of Item that contains all of its characteristics
     *
     * @param userId The ID of the user that tries to insert a new expense
     * @param category Category of the inserted expense
     * @param subCategory Sub-Category of the inserted expense
     * @param date The date of the specific expense
     * @param price The price of the expense
     * @param currency The currency that was used buying this expense
     * @param currencyRate The specific currency rate at the date of expense
     * @param description The description of the expense
     * @throws CostManException An exception will be thrown in case any verification test fails
     */
    public Item(int userId, String category, String subCategory, String date, int price, String currency,
                float currencyRate, String description) throws CostManException {
        setUserId(userId);
        setCategory(category);
        setSubCategory(subCategory);
        setDate(date);
        setPrice(price);
        setCurrency(currency);
        setCurrencyRate(currencyRate);
        setDescription(description);
    }

    /**
     * A method that returns the used currency in specific expense
     *
     * @return The currency used in specific expense
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * A method that sets the currency used in specific expense
     *
     * @param currency Currency to be set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * A method that returns the Item ID of specific expense
     *
     * @return The item ID of specific expense
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * A method that sets the Item ID of specific expense
     *
     * @param itemId ID of the expense to be set
     */
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    /**
     * A method that returns the user ID of specific expense
     *
     * @return The user ID of the specific expense selected
     */
    public int getUserId() {
        return userId;
    }

    /**
     * A method that sets the user ID of specific expense
     *
     * @param userId User ID of the expense to be set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * A method that returns the category of specific expense
     *
     * @return The category of the specific expense selected
     */
    public String getCategory() {
        return category;
    }

    /**
     * A method that sets the category of specific expense
     *
     * @param category Category of the expense to be set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * A method that returns the sub-category of specific expense
     *
     * @return The sub-category of the specific expense selected
     */
    public String getSubCategory() {
        return subCategory;
    }

    /**
     * A method that sets the sub-category of specific expense
     *
     * @param subCategory Sub-Category of the expense to be set
     */
    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    /**
     * A method that returns the date of specific expense
     *
     * @return The date of the specific expense selected
     */
    public String getDate() {
        return date;
    }

    /**
     * A method that sets the date of specific expense
     *
     * @param date Date of the expense to be set
     */
    public void setDate(String date) throws CostManException {
        if (date.length() > 0) {
            this.date = date;
        } else {
            throw new CostManException("A date must be specified!");
        }
    }

    /**
     * A method that returns the price of specific expense
     *
     * @return The price of the specific expense selected
     */
    public int getPrice() {
        return price;
    }

    /**
     * A method that sets the price of specific expense
     *
     * @param price Price of the expense to be set
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * A method that returns the currency rate of specific expense
     *
     * @return The currency rate of the specific expense selected
     */
    public float getCurrencyRate() {
        return currencyRate;
    }

    /**
     * A method that sets the currency rate of specific expense
     *
     * @param currencyRate Currency rate of the expense to be set
     */
    public void setCurrencyRate(float currencyRate) {
        this.currencyRate = currencyRate;
    }

    /**
     * A method that returns the description of specific expense
     *
     * @return The description of the specific expense selected
     */
    public String getDescription() {
        return description;
    }

    /**
     * A method that sets the description of specific expense
     *
     * @param description Description of the expense to be set
     */
    public void setDescription(String description) throws CostManException {
        if (description.length() > 3) {
            this.description = description;
        } else {
            throw new CostManException("Description must be longer than 3 chars!");
        }
    }
}
