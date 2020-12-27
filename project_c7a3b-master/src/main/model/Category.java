package model;

import org.json.JSONObject;
import persistence.ParseToJson;

import java.util.Objects;

//Represents a category which has a name and a id as a non-negative integer
public class Category implements ParseToJson {
    private String name;
    private int id;

    //CONSTRUCTOR
    //EFFECTS: Creates a new Category and sets this.name as name and this.id as id
    public Category(String name,int id) {
        this.name = name;
        this.id = id;
    }

    //EFFECTS: parses Category into a JSONObject containing a name and id
    public JSONObject parseToJson() {
        JSONObject parsedCategory = new JSONObject();
        parsedCategory.put("name", this.name);
        parsedCategory.put("id",this.id);
        return parsedCategory;
    }

    //EFFECTS: return this.name
    public String getName() {
        return this.name;
    }

    //EFFECTS: returns this.id
    public int getId() {
        return this.id;
    }

    //EFFECTS: checks if given object's class,id and name are equal to this' fields
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Category category = (Category) o;
        return id == category.id && name.toLowerCase().equals(category.name.toLowerCase());
    }

    //hashcode for the object with name and id
    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }
}
