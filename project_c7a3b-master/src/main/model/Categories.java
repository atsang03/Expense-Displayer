package model;

import exceptions.OutOfBoundsException;
import org.json.JSONArray;
import persistence.ParseToJsonArray;

import java.util.ArrayList;

// Represents a list of Category
// There can not be multiple category(s) that have the same id or same names
public class Categories implements ParseToJsonArray {

    private ArrayList<Category> categories;

    //Constructor
    //EFFECTS: Creates an ArrayList for Category containing miscellaneous category
    public Categories() {
        categories = new ArrayList<>();
        Category miscellaneous = new Category("Miscellaneous",0);
        categories.add(miscellaneous);
    }

    //EFFECTS: parses categories into a JSONArray filled with parsed category(s)
    public JSONArray parseToJson() {
        JSONArray parsedCategories = new JSONArray();
        for (Category c : categories) {
            parsedCategories.put(c.parseToJson());
        }
        return parsedCategories;
    }

    //MODIFIES: this
    //EFFECTS: Inserts c into the categories and returns true unless there is already an existing
    //         category with the same id or same name
    public boolean insertCategory(Category c) {
        for (Category cat : categories) {
            if (cat.getId() == c.getId()
                    || cat.getName().toLowerCase().equals(c.getName().toLowerCase())) {
                return false;
            }
        }
        categories.add(c);
        return true;
    }

    //MODIFIES: this
    //EFFECTS: removes the category with the id number same as i returns true if removed category
    //         returns false if did not remove category
    public boolean removeCategoryById(int id) {
        for (Category cat : categories) {
            if (cat.getId() == id) {
                categories.remove(cat);
                return true;
            }
        }
        return false;
    }

    //MODIFIES: this
    //EFFECTS: removes the category with the corresponding name and returns true
    //         if no category with the parameter name is found in the list then return false
    public boolean removeCategoryByName(String name) {
        for (Category c : categories) {
            if (c.getName().toUpperCase().equals(name.toUpperCase())) {
                categories.remove(c);
                return true;

            }
        }
        return false;
    }

    //MODIFIES: this
    //EFFECTS: remove the category at the requested index
    //         throws exception if the list is empty or i is greater than the size of list
    public void removeCategoryByPosition(int i) throws OutOfBoundsException {
        if (categories.size() == 0 || i > categories.size()) {
            throw new OutOfBoundsException();
        } else {
            categories.remove(i);
        }
    }

    //EFFECTS: returns true if there is a category with the same id or name
    //         as the requested category
    public Boolean containsCategoryByIdOrName(Category c) {
        for (Category cat : categories) {
            if (cat.getId() == c.getId()
                    || cat.getName().toLowerCase().equals(c.getName().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    //EFFECTS: returns true if there is a category with given id
    public Boolean containsCategoryWithId(int id) {
        for (Category cat : categories) {
            if (cat.getId() == id) {
                return true;
            }
        }
        return false;
    }

    //EFFECTS: returns the Category with the id i
    //         if there are no category with the id i then return null
    public Category getCategoryById(int i) {
        for (Category cat : categories) {
            if (cat.getId() == i) {
                return cat;
            }
        }
        return null;
    }

    //EFFECTS: returns the length of Category
    public int getLength() {
        return categories.size();
    }

    //EFFECTS: returns the category at position i
    ////       throws exception if the list is empty or i is greater than the size of list
    public Category getCategoryByPosition(int i) throws OutOfBoundsException {
        if (categories.size() == 0 || i > categories.size()) {
            throw new OutOfBoundsException();
        } else {
            return categories.get(i);
        }
    }
}
