package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseTest {
    Expense expense;
    Category miscellaneous = new Category("miscellaneous",1);

    @BeforeEach
    void setUp() {
        expense = new Expense("Clothes",50.50,10);
    }

    @Test
    void testConstructor() {
        assertEquals("Clothes",expense.getName());
        assertEquals(50.50,expense.getAmount());
        assertEquals(10,expense.getMonth());
        assertEquals(null,expense.getCategory());
        expense.setCategory(miscellaneous);
        assertEquals(miscellaneous,expense.getCategory());
    }
    @Test
    void testChangeName() {
        //making sure the setUp works
        assertEquals("Clothes",expense.getName());
        expense.changeName("Toys");
        //test to check if name of the expense changes
        assertEquals("Toys",expense.getName());
    }

    @Test
    void testChangeAmount() {
        assertEquals(50.50,expense.getAmount());
        expense.changeAmount(25.25);
        //test to check expense amount changing
        assertEquals(25.25 ,expense.getAmount());
    }

    @Test
    void testChangeMonth() {
        assertEquals(10,expense.getMonth());
        expense.changeMonth(11);
        //test to check expense month changing
        assertEquals(11,expense.getMonth());
    }

    @Test
    void testChangeCategory() {
        expense.setCategory(miscellaneous);
        assertEquals(miscellaneous,expense.getCategory());
        Category clothing = new Category("Clothing",2);
        //teset to check if category changes
        expense.changeCategory(clothing);
        assertEquals(clothing,expense.getCategory());
    }

    @Test
    void testParseToJson() {
        Expense testExpense = new Expense("testExpense",123,12);
        testExpense.setCategory(miscellaneous);
        JSONObject testObject = testExpense.parseToJson();
        assertEquals("testExpense",testObject.get("name"));
        assertEquals(123.0,testObject.get("amount"));
        assertEquals(12,testObject.get("month"));
        assertEquals("miscellaneous",testObject.get("category name"));
        assertEquals(1,testObject.get("category id"));
    }

    @Test
    void testEquals() {
        Expense testExpense = new Expense("testExpense",300,12);
        Expense equalExpense = new Expense("testExpense",300,12);
        Expense notEqualname = new Expense("notequal",300,12);
        Expense notEqualAmount = new Expense("testExpense",30,12);
        Expense notEqualMonth = new Expense("testExpense",300,11);
        Expense notEqualCategory = new Expense("testExpense",300,12);
        notEqualAmount.setCategory(miscellaneous);
        notEqualMonth.setCategory(miscellaneous);
        Category notEqual = new Category("notEqual",3);
        notEqualCategory.setCategory(notEqual);
        notEqualname.setCategory(miscellaneous);
        testExpense.setCategory(miscellaneous);
        equalExpense.setCategory(miscellaneous);
        assertFalse(testExpense.equals(null));
        assertTrue(testExpense.equals(equalExpense));
        assertFalse(testExpense.equals(1));
        assertFalse(testExpense.equals(notEqualname));
        assertFalse(testExpense.equals(notEqualAmount));
        assertFalse(testExpense.equals(notEqualMonth));
        assertFalse(testExpense.equals(notEqualCategory));
    }

    @Test
    void testHash() {
        Expense testExpense1 = new Expense("testExpense",300,12);
        testExpense1.setCategory(miscellaneous);
        Expense testExpense2 = new Expense("testExpense",300,12);
        testExpense2.setCategory(miscellaneous);
        Expense notEqualExpense = new Expense("unique",300,12);
        notEqualExpense.setCategory(miscellaneous);
        assertEquals(testExpense1.hashCode(),testExpense2.hashCode());
        assertNotEquals(testExpense1.hashCode(),notEqualExpense.hashCode());
    }
}
