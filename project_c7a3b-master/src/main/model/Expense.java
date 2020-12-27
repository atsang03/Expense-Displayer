package model;

import org.json.JSONObject;
import persistence.ParseToJson;

import java.util.Objects;


// Represents an expense which includes a name, amount, month and category
public class Expense implements ParseToJson {
    private String name;
    private double amount;
    private int month;
    private Category category;

    //CONSTRUCTOR
    //REQUIRES: non-negative amount
    //          month has to be between 1-12 (representing the month ie Jan. is 1 and Dec. is 12)
    //EFFECTS:constructs a new expense with a name amount month and category
    public Expense(String name, double amount, int month) {
        this.name = name;
        this.amount = amount;
        this.month = month;
    }

    //EFFECTS: Parses the Expense to a JSONObject
    public JSONObject parseToJson() {
        JSONObject parsedExpense = new JSONObject();
        parsedExpense.put("name", this.name);
        parsedExpense.put("amount", this.amount);
        parsedExpense.put("month",this.month);
        parsedExpense.put("category name",this.category.getName());
        parsedExpense.put("category id",this.category.getId());
        return parsedExpense;
    }

    //EFFECT: sets category with given category
    public void setCategory(Category c) {
        this.category = c;
    }

    //MODIFIES: this
    //EFFECTS: replaces name with newName
    public void changeName(String newName) {
        this.name = newName;
    }

    //MODIFIES: this
    //EFFECTS: replaces amount with newAmount
    public void changeAmount(double newAmount) {
        this.amount = newAmount;
    }

    //MODIFIES: this
    //EFFECTS: replaces month with newMonth
    public void changeMonth(int newMonth) {
        this.month = newMonth;
    }

    //MODIFIES: this
    //EFFECTS: replaces category with newCategory
    public void changeCategory(Category newCategory) {
        this.category = newCategory;
    }

    //EFFECTS: returns the expense's name
    public String getName() {
        return this.name;
    }

    //EFFECTS: returns the expense's amount
    public double getAmount() {
        return this.amount;
    }

    //EFFECTS: returns the expense's month number
    public int getMonth() {
        return this.month;
    }

    //EFFECTS: returns the expense's name
    public Category getCategory() {
        return this.category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Expense expense = (Expense) o;
        return Double.compare(expense.amount, amount) == 0
                && month == expense.month
                && name.equals(expense.name)
                && category.equals(expense.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, amount, month, category);
    }
}
