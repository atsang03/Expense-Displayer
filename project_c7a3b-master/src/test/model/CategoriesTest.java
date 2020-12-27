package model;


import exceptions.OutOfBoundsException;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoriesTest {
    private Categories categories;

    @BeforeEach
    void setUp() {
        //Sets up an empty Categories list
        categories = new Categories();
    }

    @Test
    void testConstructor() {
        //checks to make sure the new category list contains only miscellaneous
        assertEquals(1, categories.getLength());


        categories.insertCategory(new Category("first", 1));
        //Test to check that the changes to the category occurs. REDUNDANT but why not
        assertEquals(2, categories.getLength());
    }

    @Test
    void testInsertCategory() {
        Category testCategory = new Category("first", 1);
        //Test to see if inserting a new category which isn't inside works
        assertTrue(categories.insertCategory(testCategory));
        assertEquals(2, categories.getLength());
        assertEquals(testCategory, categories.getCategoryById(1));

        //Test to see if inserting the same category is possible or not
        assertFalse(categories.insertCategory(testCategory));
        assertEquals(2, categories.getLength());

        //Test to see if inserting a category with the same name works or not (should not)
        assertFalse(categories.insertCategory(new Category("first", 2)));
        //Test to see if inserting a category with the same id works or not (should not)
        assertFalse(categories.insertCategory(new Category("second", 1)));
        //checks to see if insertCategory is case sensitive or not (should not be)
        assertFalse(categories.insertCategory(new Category("FIRST", 2)));
    }

    @Test
    void testGetCategoryById() {
        Category testCategory = new Category("first", 1);
        categories.insertCategory(testCategory);
        //Test if trying to retrieve category that the list doesn't have
        assertNull(categories.getCategoryById(2));
        //Test to see if it is possible to retrieve a category that the list does have
        assertEquals(testCategory, categories.getCategoryById(1));
    }

    @Test
    void testRemoveCategoriesById() {
        Category testCategory = new Category("first", 1);
        Category testCategory2 = new Category("second", 2);
        categories.insertCategory(testCategory);
        categories.insertCategory(testCategory2);
        //Checks if possible to remove a category that isn't inside  the list
        assertFalse(categories.removeCategoryById(3));
        assertEquals(3, categories.getLength());
        try {
            assertEquals(testCategory, categories.getCategoryByPosition(1));
        } catch (OutOfBoundsException outOfBoundsException) {
            fail();
        }
        //Checks if it is possible to remove a category that is inside the list
        assertTrue(categories.removeCategoryById(1));
        assertEquals(2, categories.getLength());
        try {
            assertEquals(testCategory2, categories.getCategoryByPosition(1));
        } catch (OutOfBoundsException outOfBoundsException) {
            fail();
        }
    }

    @Test
    void testContainsCategoryByIdOrName() {
        Category testCategory = new Category("first", 1);
        categories.insertCategory(testCategory);
        //Test to check if testCategory is inside categories
        assertTrue(categories.containsCategoryByIdOrName(testCategory));
        //Test to check if a category that isn't inside the list
        assertFalse(categories.containsCategoryByIdOrName(new Category("second", 2)));
        //Test to check a category that has the same name but different Id
        assertTrue(categories.containsCategoryByIdOrName(new Category("first", 2)));
        //test to check if a category with same ID but different name
        assertTrue(categories.containsCategoryByIdOrName(new Category("second", 1)));
    }

    @Test
    void testRemoveCategoryByName() {
        Category testCategory = new Category("first", 1);
        categories.insertCategory(testCategory);
        //Test to remove a category that doesn't exists
        assertFalse(categories.removeCategoryByName("fake test"));
        assertEquals(2, categories.getLength());
        //Test to remove category that exists and testing case sensitive
        assertTrue(categories.removeCategoryByName("fIrSt"));
        assertEquals(1, categories.getLength());
    }

    @Test
    void testRemoveCategoryByPosition() {
        Category testCategory = new Category("first", 1);
        Category testCategory2 = new Category("second", 2);
        categories.insertCategory(testCategory);
        categories.insertCategory(testCategory2);
        assertEquals(3, categories.getLength());
        try {
            assertEquals(testCategory2, categories.getCategoryByPosition(2));
            assertEquals(testCategory, categories.getCategoryByPosition(1));
        } catch (OutOfBoundsException outOfBoundsException) {
            fail();
        }
        try {
            categories.removeCategoryByPosition(0);
        } catch (OutOfBoundsException e) {
            fail();
        }
        assertEquals(2, categories.getLength());
        try {
            assertEquals(testCategory, categories.getCategoryByPosition(0));
            assertTrue(categories.containsCategoryByIdOrName(testCategory2));
            assertTrue(categories.containsCategoryByIdOrName(testCategory));
        } catch (OutOfBoundsException outOfBoundsException) {
            fail();
        }
    }

    @Test
    void testRemoveCategoryByPositionExceptions() {
        try {
            categories.removeCategoryByPosition(0);
            categories.removeCategoryByPosition(0);
            fail("empty list");
        } catch (OutOfBoundsException e) {
            //pass
        }
        try {
            categories.removeCategoryByPosition(5);
            fail("empty list");
        } catch (OutOfBoundsException e) {
            //pass
        }
        Category testCategory = new Category("first", 1);
        categories.insertCategory(testCategory);
        try {
            categories.removeCategoryByPosition(10);
            fail("list not that big");
        } catch (OutOfBoundsException e) {
            //pass
        }
    }

    @Test
    void testContainsCategoryWithId() {
        Category testCategory = new Category("first", 1);
        categories.insertCategory(testCategory);
        assertFalse(categories.containsCategoryWithId(2));
        assertTrue(categories.containsCategoryWithId(1));
    }

    @Test
    void testGetCategoryByPosition() {
        Category testCategory = new Category("first", 1);

        try {
            categories.removeCategoryByPosition(0);
            categories.getCategoryByPosition(0);
            fail();
        } catch (OutOfBoundsException e) {
            //pass
        }
        try {
            categories.getCategoryByPosition(10);
            fail();
        } catch (OutOfBoundsException e) {
            //pass
        }
        categories.insertCategory(testCategory);
        try {
           assertEquals(testCategory,categories.getCategoryByPosition(0));
        } catch (OutOfBoundsException e) {
            fail();
        }
        try {
            categories.getCategoryByPosition(10);
            fail();
        } catch (OutOfBoundsException e) {
            //pass
        }
    }

    @Test
    void testParseToJson() {
        JSONArray testArrayEmpty = categories.parseToJson();
        assertEquals(1, testArrayEmpty.length());
        Category testCategory = new Category("first", 1);
        Category testCategory2 = new Category("second", 2);
        Category testCategory3 = new Category("third", 3);
        categories.insertCategory(testCategory);
        categories.insertCategory(testCategory2);
        categories.insertCategory(testCategory3);
        JSONArray testArrayFilled = categories.parseToJson();
        assertEquals(4, testArrayFilled.length());
        assertTrue(testArrayFilled.getJSONObject(2).getString("name").equals("second"));
        assertEquals(1, testArrayFilled.getJSONObject(1).getInt("id"));
    }
}
