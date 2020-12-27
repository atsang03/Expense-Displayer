package persistence;

import exceptions.OutOfBoundsException;
import model.Categories;
import model.Category;
import model.Expense;
import model.ListOfExpenses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

public class ReaderTest {
    @Test
    void testReaderEmptyFileExpenses() {
        Reader reader = new Reader("./data/EmptyFile.json");
        try {
            ListOfExpenses listOfExpenses = reader.readExpenses();
            assertEquals(0,listOfExpenses.getLength());
        } catch (IOException e) {
            fail("should be able to read");
        }
    }

    @Test
    void testReaderEmptyFileCategories() {
        Reader reader = new Reader("./data/EmptyFile.json");
        try {
            Categories categories = reader.readCategories();
            assertEquals(1,categories.getLength());
        } catch (IOException e) {
            fail("should be able to read");
        }
    }

    @Test
    void testReaderFakeFile() {
        Reader reader = new Reader("./data/fakeFile");
        try {
            ListOfExpenses listOfExpenses = reader.readExpenses();
            fail("should throw exception");
        } catch (IOException e) {
        }
    }


    @Test
    void testReaderTestFile() {
        Reader reader = new Reader("./data/TestFile.json");
        //Category and Expenses that will be used to compare inside testFile
        Category clothes = new Category("clothes",2);
        Category foods = new Category("foods",1);
        Category miscellaneous = new Category("Miscellaneous",0);
        Expense pants = new Expense("pants",300,1);
        pants.setCategory(clothes);
        Expense apples = new Expense("apples",10,12);
        apples.setCategory(foods);
        Expense courage = new Expense("courage",130,4);
        courage.setCategory(miscellaneous);
        try {
            ListOfExpenses listOfExpenses = reader.readExpenses();
            Categories categories = reader.readCategories();
            //assert tests to make sure amount of things are correctly put in
            assertEquals(3,listOfExpenses.getLength());
            assertEquals(3,categories.getLength());
            //assert tests to make sure items are correct
            assertTrue(listOfExpenses.getExpenseByPosition(0).equals(pants));
            assertTrue(listOfExpenses.getExpenseByPosition(2).equals(courage));
            assertTrue(categories.getCategoryByPosition(0).equals(miscellaneous));
            assertTrue(categories.getCategoryByPosition(2).equals(clothes));
        } catch (IOException e) {
            System.out.println("File Exists");
        } catch (OutOfBoundsException e) {
            fail();
        }
    }


}
