package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {
    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category("First",1);
    }

    @Test
    void testConstructor() {
        assertEquals("First",category.getName());
        assertEquals(1,category.getId());
    }

    @Test
    void testParseToJson() {
        JSONObject testObject = category.parseToJson();;
        assertEquals("First",testObject.getString("name"));
        assertEquals(1,testObject.getInt("id"));
    }

    @Test
    void testEquals() {
        Category category1 = new Category("category1",1);
        Category equalCategory = new Category("category1",1);
        Category category2 = new Category("category2",2);

        assertTrue(category1.equals(equalCategory));
        assertFalse(category1.equals(category2));
        assertFalse(category1.equals(null));
        assertFalse(category1.equals(1));
        assertFalse(category1.equals(new Category("category1",2)));
        assertFalse(category1.equals(new Category("fake",1)));
    }

    @Test
    void testHashCode() {
        Category category1 = new Category("category1",1);
        Category equalCategory = new Category("category1",1);
        Category category2 = new Category("category2",2);

        assertEquals(category1.hashCode(),equalCategory.hashCode());
        assertNotEquals(category1.hashCode(),category2.hashCode());
    }
}
