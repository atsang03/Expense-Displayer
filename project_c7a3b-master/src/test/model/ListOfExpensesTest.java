package model;

import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.platform.commons.annotation.Testable;

import static org.junit.jupiter.api.Assertions.*;

public class ListOfExpensesTest {
    private ListOfExpenses expenses;

    Expense expense1 = new Expense("Clothing",20.20,1);
    Expense expense2 = new Expense("Boots",30.90,2);
    Expense expense3 = new Expense("Pants",10.10,3);

    @BeforeEach
    void setUp() {
        expenses = new ListOfExpenses();
    }

    @Test
    void testSortExpensesByMonth() {
        insertingExpenses();

        expenses.sortExpensesByMonth();
        //Testing if sorting correctly, should be 1 -> 2 -> 3
        assertEquals(expense1, expenses.getExpenseByPosition(0));
        assertEquals(expense2, expenses.getExpenseByPosition(1));
        assertEquals(expense3, expenses.getExpenseByPosition(2));
    }

    @Test
    void testSortGreatestToLeast() {
        insertingExpenses();
        Expense boots2 = new Expense("Equal to Boots", 30.90,3);
        expenses.insertExpense(boots2);
        //Test to check list is sorted from greatest amount to least amount and the first of the boots that appears
        //first is the one closer to the beginning of the list
        expenses.sortGreatestToLeast();
        assertEquals(expense2,expenses.getExpenseByPosition(0));
        assertEquals(boots2,expenses.getExpenseByPosition(1));
        assertEquals(expense1,expenses.getExpenseByPosition(2));
        assertEquals(expense3,expenses.getExpenseByPosition(3));
    }

    @Test
    void testSortLeastToGreatest() {
        insertingExpenses();
        Expense boots2 = new Expense("Equal to Boots", 30.90,3);
        expenses.insertExpense(boots2);
        //Test to check list is sorted least to greatest amount
        //the first of the ones that have the same amount should appear before the other
        expenses.sortLeastToGreatest();
        assertEquals(expense3,expenses.getExpenseByPosition(0));
        assertEquals(expense1,expenses.getExpenseByPosition(1));
        assertEquals(expense2,expenses.getExpenseByPosition(2));
        assertEquals(boots2,expenses.getExpenseByPosition(3));
    }

    @Test
    void testSortExpensesAlphabetically() {
        insertingExpenses();

        Expense baking = new Expense("Baking",123.23,11);
        expenses.insertExpense(baking);
        expenses.sortExpensesAlphabetically();
        //Testing if sorting correctly baking,expenses2,expenses1,expense3
        assertEquals(baking, expenses.getExpenseByPosition(0));
    }

    @Test
    void testRemoveExpenseByName() {
        insertingExpenses();

        //Test to see if returns false if there are no expense with name cheese
        assertFalse(expenses.removeExpenseByName("cheese"));
        assertEquals(3,expenses.getLength());
        //test to see if expense2 will be removed
        assertTrue(expenses.removeExpenseByName("Boots"));
        assertEquals(2,expenses.getLength());
        //check if expense3 is now at the index of 1
        assertEquals(expense3, expenses.getExpenseByPosition(1));
    }

    @Test
    void testGetHighestExpense() {
        insertingExpenses();
        //check if the highest expense really is expense2 (it is the highest)
        assertEquals(expense2, expenses.getHighestExpense());

        Expense boots2 = new Expense("Equal to Boots", 30.90,3);
        expenses.insertExpense(boots2);
        //checks if the returned expense really is the first one in the list if there are two with the same expense
        //value
        assertEquals(expense2, expenses.getHighestExpense());
        Expense expensive = new Expense("cheese",1000,11);
        expenses.insertExpense(expensive);
        assertEquals(expensive,expenses.getHighestExpense());
    }

    @Test
    void testGetLowestExpense() {
        insertingExpenses();
        //test if lowest expense is expense3
        assertEquals(expense3, expenses.getLowestExpense());
        Expense pants2 = new Expense("Equal to pants",10.10,12);
        //test if expense returned is is expense3 if there are two expenses with same expense value
        assertEquals(expense3, expenses.getLowestExpense());
        }

    @Test
    void testGetExpenseByName() {
        insertingExpenses();
        //test to get expenses that is inside list
        assertEquals(expense3, expenses.getExpenseByName("Pants"));
        //test case sensitive
        assertEquals(expense3, expenses.getExpenseByName("pAnTs"));
        //test to get expenses that isn't inside list
        assertEquals(null, expenses.getExpenseByName("Bucket"));
    }

    @Test
    void testContainsExpenseWithName() {
        insertingExpenses();
        //test to see if true if asking for an expense inside list
        assertTrue(expenses.containsExpenseWithName("Pants"));
        //test for case sensitive
        assertTrue(expenses.containsExpenseWithName(("pAnTs")));
        //test to see if false if asking for a non existing expense name
        assertFalse(expenses.containsExpenseWithName(("job")));
    }

    @Test
    void testSortByCategory() {
        Expense expense1 = new Expense("a",10,11);
        Expense expense2 = new Expense("b",10,11);
        Expense expense3 = new Expense("c",10,11);
        Expense expense4 = new Expense("d",10,11);
        Category category1 = new Category("a",1);
        Category category2 = new Category("b",2);
        Category category3 = new Category("ca",3);
        Category category4 = new Category("cb",4);
        expense1.setCategory(category1);
        expense2.setCategory(category2);
        expense3.setCategory(category3);
        expense4.setCategory(category4);
        expenses.insertExpense(expense3);
        expenses.insertExpense(expense2);
        expenses.insertExpense(expense4);
        expenses.insertExpense(expense1);
        expenses.sortByCategory();
        assertEquals(expense1,expenses.getExpenseByPosition(0));
        assertEquals(expense2,expenses.getExpenseByPosition(1));
        assertEquals(expense3,expenses.getExpenseByPosition(2));
        assertEquals(expense4,expenses.getExpenseByPosition(3));
    }

    @Test
    void testInsertExpense() {
        assertTrue(expenses.insertExpense(expense2));
        assertTrue(expenses.insertExpense(expense1));
        assertEquals(2,expenses.getLength());
        Expense sameName = new Expense("boOts",20,11);
        assertFalse(expenses.insertExpense(sameName));
        assertEquals(2,expenses.getLength());
    }

    @Test
    void testParseToJson() {
        Category testCat = new Category("testCat",1);
        expenses = new ListOfExpenses();
        JSONArray testArrayEmpty = expenses.parseToJson();
        assertEquals(0,testArrayEmpty.length());

        expense1.setCategory(testCat);
        expense2.setCategory(testCat);
        expense3.setCategory(testCat);
        insertingExpenses();
        JSONArray testArrayFilled = expenses.parseToJson();

        assertEquals(3,testArrayFilled.length());
        assertTrue(testArrayFilled.getJSONObject(2).getString("name").equals(expense3.getName()));
    }

    @Test
    void testRemoveExpenseByPosition() {
        Category testCat = new Category("testCat",1);
        expenses = new ListOfExpenses();
        insertingExpenses();

        assertEquals(3,expenses.getLength());
        assertEquals(expense3,expenses.getExpenseByPosition(2));

        expenses.removeExpenseByPosition(2);
        assertEquals(2,expenses.getLength());
        assertEquals(expense1.getName(),expenses.getExpenseByPosition(1).getName());
    }

    //HELPER
    //EFFECTS: Inserts into Expenses expenses1,expenses2,expenses3
    void insertingExpenses() {
        expenses.insertExpense(expense2);
        expenses.insertExpense(expense1);
        expenses.insertExpense(expense3);



    }
}
