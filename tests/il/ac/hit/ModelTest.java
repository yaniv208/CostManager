package il.ac.hit;

import il.ac.hit.model.EnumCategoryType;
import il.ac.hit.model.Item;
import il.ac.hit.model.Model;
import il.ac.hit.model.User;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import java.util.List;
import java.util.logging.Logger;
import static org.junit.Assert.*;

/**
 * A class that contains all the unit tests for the functions in Model
 * assertEquals    - Asserts that two objects are equal.
 * assertNotEquals - Asserts that two objects are not equals.
 * assertFalse - Asserts that the actual parameter is false.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class ModelTest
{
    private static final Logger LOGGER = Logger.getLogger(ModelTest.class.getName());

    @org.junit.Test
    public void insertNewUser() {
        LOGGER.info("Starting insertNewUser() Test");
        String expected = "Problem with registering user!";

        try {
            User user = new User("test@gmail.com", "testPass", "Yossi Cohen");
            Model.getInstance().insertNewUser(user);
            // if an exception will occur, it would take place on next line
        }
        catch (CostManException e) {
            assertEquals(null, expected, e.getMessage());
        }
    }

    @org.junit.Test
    public void selectUserCredentials() {
        LOGGER.info("Starting selectUserCredentials() Test");
        String expected = "Error logging in! (User not found/Wrong password)";

        try {
            Model.getInstance().selectUserCredentials("test@gmail.com", "testPass");
            // if an exception will occur, it would take place on next line
        }
        catch (CostManException err) {
            assertEquals(null, expected, err.getMessage());
        }

    }

    @org.junit.Test
    public void getItemsByRangeOfDates() {
        LOGGER.info("Starting getRangeOfDatesByUser() Test");
        String expected = "Error getting data between dates!";

        try {
            List<Item> items = Model.getInstance().getItemsByRangeOfDates(12,
                    "2020-01-01", "2021-12-08");
            assertNotNull("List of items is null", items);
            // if another exception will be thrown, it will take place on next line
        }
        catch (CostManException err) {
            assertEquals(null, expected, err.getMessage());
        }

    }

    @org.junit.Test
    public void insertNewItem() {
        LOGGER.info("Starting insertNewItem() Test");
        String expected = "Error while inserting into DataBase!";

        try {
            Model.getInstance().insertNewItem(new Item(5,2, 3, "2021-12-08",
                    50, "USD", 2.2f, "Hamburger"));
            // if an exception will occur, it would take place on next line
        }
        catch (CostManException err) {
            assertEquals(null, expected, err.getMessage());
        }
    }

    @org.junit.Test
    public void deleteItem() {
        LOGGER.info("Starting deleteItem() Test");

        String expected = "Error deleting item!";

        try {
            Model.getInstance().deleteItem(12, 12);
        }
        catch (CostManException err) {
            assertEquals(null, expected, err.getMessage());
        }
    }

    @org.junit.Test
    public void GetCategories_Primary_Test1()
    {
        boolean isExceptionThrown = false;
        LOGGER.info("Starting GetCategories_Primary_Test1()");

        try {
            List<String> primaries2 = Model.getInstance().getCategoriesByCategoryType(EnumCategoryType.Primary);

            assertNotEquals("List of primaries is null", primaries2, null);
            assertEquals("Wrong element at position 0", primaries2.get(0), "TstCat1_Primary");
        }
        catch (CostManException err) {
            isExceptionThrown = true;
        }

        assertFalse(isExceptionThrown);
    }

    @org.junit.Test
    public void GetCategories_Secondary_Test1()
    {
        boolean isExceptionThrown = false;
        LOGGER.info("Starting GetCategories_Secondary_Test1()");

        try {
            List<String> secondaries2 = Model.getInstance().getCategoriesByCategoryType(EnumCategoryType.Secondary);

            assertNotEquals("List of primaries is null", secondaries2, null);
            assertEquals("Wrong element at position 0", secondaries2.get(0), ("TstCat1_Secondary"));
        }
        catch (CostManException err) {
            isExceptionThrown = true;
        }

        assertFalse(isExceptionThrown);
    }

    @org.junit.Test
    public void InsertNewCategory_Primary_Test1()
    {
        boolean isExceptionThrown = false;
        LOGGER.info("Starting InsertNewCategory_Primary_Test1()");

        try {
            Model.getInstance().insertNewCategory("ProgramPrimary", null);
            List<String> primaries1 = Model.getInstance().getCategoriesByCategoryType(EnumCategoryType.Primary);

            assertEquals("Wrong number of elements", primaries1.size(), 2);
            assertEquals("Wrong element at position 0", primaries1.get(0), "ProgramPrimary");
            assertEquals("Wrong element at position 1", primaries1.get(1), "TstCat1_Primary");
        }
        catch (CostManException err) {
            isExceptionThrown = true;
        }

        assertFalse(isExceptionThrown);
    }

    @org.junit.Test
    public void InsertNewCategory_Secondary_Test1()
    {
        boolean isExceptionThrown = false;
        LOGGER.info("Starting InsertNewCategory_Secondary_Test1()");

        try {
            Model.getInstance().insertNewCategory("ProgramSecondary", "ProgramPrimary");
            List<String> secondaries1 = Model.getInstance().getCategoriesByCategoryType(EnumCategoryType.Secondary);

            assertEquals("Wrong number of elements", secondaries1.size(), 2);
            assertEquals("Wrong element at position 0", secondaries1.get(0), "ProgramSecondary");
            assertEquals("Wrong element at position 1", secondaries1.get(1), "TstCat1_Secondary");
        }
        catch (CostManException err) {
            isExceptionThrown = true;
        }

        assertFalse(isExceptionThrown);
    }

    @org.junit.Test
    public void getCategoryNameByCategoryID(){
        boolean isExceptionThrown = false;
        LOGGER.info("Starting getCategoryNameByCategoryID()");

        try {
            String categoryName = Model.getInstance().getCategoryNameByCategoryID(1);

            assertEquals("Wrong category name at ID 1", categoryName, "TstCat1_Primary");
        }
        catch (CostManException err) {
            isExceptionThrown = true;
        }
        assertFalse(isExceptionThrown);
    }

    @org.junit.Test
    public void getCurrencies(){
        boolean isExceptionThrown = false;
        LOGGER.info("Starting getCurrencies()");

        try {
            float[] currencies = Model.getInstance().getCurrencies();

            assertEquals("Wrong array size", 4, currencies.length);
        }
        catch (CostManException err) {
            isExceptionThrown = true;
        }
        assertFalse(isExceptionThrown);
    }
}

