package persistence;

import model.Categories;

import model.ListOfExpenses;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


//represents the saver that saves the JSON representation of the data
//to the file
//Referenced Demo
public class Saver {
    private String filename;
    PrintWriter writer;

    //CONSTRUCTOR
    //EFFECTS:Creates a Saver with the given String as the filename
    public Saver(String file) {
        filename = file;
    }


    //referenced demo project
    //MODIFIES: this
    //EFFECTS: saves the list of categories inside the file
    public void save(Categories categories, ListOfExpenses expenses) {
        JSONArray parsedCategories = categories.parseToJson();
        JSONArray parsedExpenses = expenses.parseToJson();
        JSONObject save = new JSONObject();

        save.put("categories",parsedCategories);
        save.put("expenses",parsedExpenses);

        saveToFile(save.toString(3));
    }

    //Got this from the demo project!
    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

    //Got this from demo project
    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(filename));
    }

    //Got this from demo project
    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }
}



