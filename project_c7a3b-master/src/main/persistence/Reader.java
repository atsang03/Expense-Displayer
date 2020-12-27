package persistence;

import model.Categories;
import model.Category;
import model.Expense;
import model.ListOfExpenses;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

//represents the reader that is going to read JSON data
//referenced demo reader a lot
public class Reader {
    private String file;

    //CONSTRUCTOR
    //EFFECTS: creates a reader with the given source as file
    public Reader(String filename) {
        this.file = filename;
    }

    //EFFECTS: reads listOfExpenses from file and returns it
    // throws IOException if an error occurs reading data from file
    //got from demo
    public ListOfExpenses readExpenses() throws IOException {
        String jsonData = readFile(file);
        JSONObject data = new JSONObject(jsonData);
        return parseListOfExpenses(data);
    }

    //EFFECTS reads categories from file and returns it
    //throws IOException if an error occurs while reading data
    //got from demo
    public Categories readCategories() throws IOException {
        String jsonData = readFile(file);
        JSONObject data = new JSONObject(jsonData);
        return parseCategories(data);
    }


    //EFFECTS: reads JSON file
    // and returns it
    //throws IOException if an error occurs when reading data from file
    //got from demo project
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    //EFFECTS: parses list of expenses from JSON object and returns it
    //referenced from demo project
    private ListOfExpenses parseListOfExpenses(JSONObject object) {
        ListOfExpenses listofExpenses = new ListOfExpenses();
        addExpenses(listofExpenses,object);
        return listofExpenses;
    }

    //MODIFIES listOfExpenses
    //EFFECTS: parses list of expenses
    private void addExpenses(ListOfExpenses listOfExpenses, JSONObject object) {
        JSONArray expensesData = object.getJSONArray("expenses");
        for (Object expense : expensesData) {
            JSONObject expenseData = (JSONObject) expense;
            addExpense(listOfExpenses,expenseData);
        }
    }

    //MODIFIES: listOfExpenses
    //EFFECTS: parses expense into an actual expense and adds it to the list
    private void addExpense(ListOfExpenses listOfExpenses, JSONObject object) {
        String name = object.getString("name");
        int month = object.getInt("month");
        double amount = object.getDouble("amount");
        String categoryName = object.getString("category name");
        int categoryId = object.getInt("category id");
        Category expenseCategory = new Category(categoryName,categoryId);
        Expense expense = new Expense(name,amount,month);
        expense.setCategory(expenseCategory);
        listOfExpenses.insertExpense(expense);
    }

    //EFFECTS: parses list of categories from JSON object and returns it
    //referenced from demo
    private Categories parseCategories(JSONObject object) {
        Categories categories = new Categories();
        addCategories(categories,object);
        return categories;
    }

    //MODIFIES: categories
    //EFFECTS: parses categories from JSON object and adds to list
    private void addCategories(Categories categories,JSONObject object) {
        JSONArray categoriesData = object.getJSONArray("categories");
        for (Object category : categoriesData) {
            JSONObject categoryData = (JSONObject) category;
            addCategory(categories,categoryData);
        }
    }

    //MODIFIES: categories
    //EFFECTS: parses category from JSON object and adds to list
    private void addCategory(Categories categories, JSONObject object) {
        String name = object.getString("name");
        int id = object.getInt("id");

        Category category = new Category(name,id);
        categories.insertCategory(category);
    }
}
