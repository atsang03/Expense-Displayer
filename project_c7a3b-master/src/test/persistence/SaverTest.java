package persistence;

import exceptions.OutOfBoundsException;
import model.Categories;
import model.Category;
import model.Expense;
import model.ListOfExpenses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class SaverTest {
    private ListOfExpenses listOfExpenses;
    private Categories categories;

    private Category foods;
    private Category clothes;

    private Expense apples;
    private Expense pears;
    private Expense pants;
    private Expense shirts;
    @BeforeEach
    void setUp() {
        listOfExpenses = new ListOfExpenses();
        categories = new Categories();

        foods = new Category("foods",1);
        clothes = new Category("clothes",2);

        apples = new Expense("apples",12,1);
        pears = new Expense("pears",20,2);
        pants = new Expense("pants",300,12);
        shirts = new Expense("shirts",350,11);

        apples.setCategory(foods);
        pears.setCategory(foods);
        pants.setCategory(clothes);
        shirts.setCategory(clothes);

        listOfExpenses.insertExpense(apples);
        listOfExpenses.insertExpense(pears);
        listOfExpenses.insertExpense(pants);
        listOfExpenses.insertExpense(shirts);
        categories.insertCategory(foods);
        categories.insertCategory(clothes);
    }

    @Test
    void testSaverFakeFile() {
        try {
            Saver saver = new Saver("");
            saver.open();
            fail("should fail to save");
        } catch (FileNotFoundException e) {
            //passed test
        }
    }

    @Test
    void testSaverToFile() {
        try {
            Saver saver = new Saver("./data/TestFileSaver.json");
            saver.open();
            saver.save(categories,listOfExpenses);
            saver.close();
            Reader reader = new Reader("./data/TestFileSaver.json");
            Categories categories = reader.readCategories();
            ListOfExpenses listOfExpenses = reader.readExpenses();
            assertEquals(3,categories.getLength());
            assertEquals(4,listOfExpenses.getLength());
            assertTrue(listOfExpenses.getExpenseByPosition(1).equals(pears));
            assertTrue(categories.getCategoryByPosition(0).equals(new Category("miscellaneous",0)));
        } catch (FileNotFoundException e) {
            fail("should be able to save");
        } catch (IOException e) {
            fail("should be able to read files");
        } catch (OutOfBoundsException e) {
            fail();
        }
    }
}


