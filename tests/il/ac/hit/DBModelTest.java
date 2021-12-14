package il.ac.hit;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import il.ac.hit.DBModel;

import static org.junit.Assert.*;

/*
assertEquals    - Asserts that two objects are equal.
assertNotEquals - Asserts that two objects are not equals.
*/

@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class DBModelTest
{
    private static final Logger LOGGER = Logger.getLogger(DBModelTest.class.getName());

    @org.junit.Test
    public void insertNewUser() {
        LOGGER.info("Starting insertNewUser() Test");
        String expected = "Problem with registering user!";

        try {
            DBModel.getInstance().insertNewUser("test@gmail.com", "testPass");
            // if an exception will occur, it would take place on next line
        }
        catch (CostManException e) {
            assertEquals(null, expected, e.getMessage());
        }
    }

    @org.junit.Test
    public void selectUserCredentials() {
        LOGGER.info("Starting selectUserCredentials() Test");

        String expected = "Error logging in!";
        try
        {
            DBModel.getInstance().selectUserCredentials("test@gmail.com", "testPass");
            // if an exception will occur, it would take place on next line
        }
        catch (CostManException err)
        {
            assertEquals(null, expected, err.getMessage());
        }

    }

    @org.junit.Test
    public void getDataByRangeOfDates() {
        LOGGER.info("Starting getRangeOfDatesByUser() Test");
        String expected = "Error getting data between dates!";

        try {
            Collection<Item> items = DBModel.getInstance().getDataByRangeOfDates(12, "2020-01-01", "2021-12-08");
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
            DBModel.getInstance().insertNewItem(new Item(5,2, 3, "2021-12-08", 50,
                    2.2f, "Hamburger"));
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
            DBModel.getInstance().deleteItem(12, 12);
        }
        catch (CostManException err)
        {
            assertEquals(null, expected, err.getMessage());
        }
    }

    @org.junit.Test
    public void GetCategories_Primary_Test1()
    {
        boolean isExceptionThrown = false;
        LOGGER.info("Starting GetCategories_Primary_Test1()");

        try
        {
            List<String> primaries2 = DBModel.getInstance().getCategoriesByCategoryType(EnumCategoryType.Primary);

            assertNotEquals("List of primaries is null", primaries2, null);
            assertEquals("Wrong element at position 0", primaries2.get(0), "TstCat1_Primary");
        }
        catch (CostManException err)
        {
            isExceptionThrown = true;
        }

        assertEquals(isExceptionThrown, false);
    }

    @org.junit.Test
    public void GetCategories_Secondary_Test1()
    {
        boolean isExceptionThrown = false;
        LOGGER.info("Starting GetCategories_Secondary_Test1()");

        try
        {
            List<String> secondaries2 = DBModel.getInstance().getCategoriesByCategoryType(EnumCategoryType.Secondary);

            assertNotEquals("List of primaries is null", secondaries2, null);
            assertEquals("Wrong element at position 0", secondaries2.get(0), ("TstCat1_Secondary"));
        }
        catch (CostManException err)
        {
            isExceptionThrown = true;
        }

        assertEquals(isExceptionThrown, false);
    }

    @org.junit.Test
    public void InsertNewCategory_Primary_Test1()
    {
        boolean isExceptionThrown = false;
        LOGGER.info("Starting InsertNewCategory_Primary_Test1()");

        try
        {
            DBModel.getInstance().insertNewCategory("ProgramPrimary", null);
            List<String> primaries1 = DBModel.getInstance().getCategoriesByCategoryType(EnumCategoryType.Primary);

            assertEquals("Wrong number of elements", primaries1.size(), 2);
            assertEquals("Wrong element at position 0", primaries1.get(0), "ProgramPrimary");
            assertEquals("Wrong element at position 1", primaries1.get(1), "TstCat1_Primary");
        }
        catch (CostManException err)
        {
            isExceptionThrown = true;
        }

        assertEquals(isExceptionThrown, false);
    }

    @org.junit.Test
    public void InsertNewCategory_Secondary_Test1()
    {
        boolean isExceptionThrown = false;
        LOGGER.info("Starting InsertNewCategory_Secondary_Test1()");

        try
        {
            DBModel.getInstance().insertNewCategory("ProgramSecondary", "ProgramPrimary");
            List<String> secondaries1 = DBModel.getInstance().getCategoriesByCategoryType(EnumCategoryType.Secondary);

            assertEquals("Wrong number of elements", secondaries1.size(), 2);
            assertEquals("Wrong element at position 0", secondaries1.get(0), "ProgramSecondary");
            assertEquals("Wrong element at position 1", secondaries1.get(1), "TstCat1_Secondary");
        }
        catch (CostManException err)
        {
            isExceptionThrown = true;
        }

        assertEquals(isExceptionThrown, false);
    }
}