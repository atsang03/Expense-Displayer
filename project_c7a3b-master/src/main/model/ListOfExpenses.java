package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.ParseToJsonArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//Represents a list of Expense to contain multiple expenses
//Cannot have expenses with the same name inside the list.
public class ListOfExpenses implements ParseToJsonArray {
    private ArrayList<Expense> listOfExpenses;

    //CONSTRUCTOR
    //Creates a new empty Arraylist to hold expenses and no name
    public ListOfExpenses() {
        listOfExpenses = new ArrayList<>();
    }

    //EFFECTS: parses list of expenses to a JSONArray
    public JSONArray parseToJson() {
        JSONArray parsedExpenses = new JSONArray();
        for (Expense e : listOfExpenses) {
            parsedExpenses.put(e.parseToJson());
        }
        return parsedExpenses;
    }

    //MODIFIES: this
    //EFFECTS: inserts expense e into the list of expenses unless there is already an expense
    //         inside that list with the same name
    public boolean insertExpense(Expense e) {
        for (Expense expense: listOfExpenses) {
            if (expense.getName().toUpperCase().equals(e.getName().toUpperCase())) {
                return false;
            }
        }
        listOfExpenses.add(e);
        return true;
    }

    //MODIFIES: this
    //EFFECTS: removes the expense with the corresponding name and returns true
    //         if no expense with the parameter name is found in the list then return false
    public boolean removeExpenseByName(String name) {
        for (Expense e : listOfExpenses) {
            if (e.getName().toUpperCase().equals(name.toUpperCase())) {
                listOfExpenses.remove(e);
                return true;
            }
        }
        return false;
    }

    //MODIFIES: this
    //EFFECTS: remove the expense at the requested index
    public void removeExpenseByPosition(int i) {
        listOfExpenses.remove(i);
    }

    //The sorting methods I made I searched for a way to do it (not an exact copy but just basing off of it)
    //From: https://dzone.com/articles/java-8-comparator-how-to-sort-a-list By: Mario Pio Gioiosa. May. 20, 19

    //REQUIRES: non-empty list of expenses
    //MODIFIES: this
    //EFFECTS: it sorts the list of expenses by the first to last month 1 -> 12
    public void sortExpensesByMonth() {
        listOfExpenses.sort(Comparator.comparingInt(Expense::getMonth));
    }

    //REQUIRES: non-empty list of expenses
    //MODIFIES: this
    //EFFECTS: sorts the list of expenses alphabetically
    public void sortExpensesAlphabetically() {
        listOfExpenses.sort(Comparator.comparing(Expense::getName));
    }

    //REQUIRES: non-empty list of expenses
    //MODIFIES: this
    //EFFECTS: sorts the list of expenses by the greatest to least
    public void sortGreatestToLeast() {
        listOfExpenses.sort(Comparator.comparingDouble(Expense::getAmount).reversed());
    }

    //REQUIRES: non-empty list of expenses
    //MODIFIES: this
    //EFFECTS: sorts the list of expenses from least to greatest
    public void sortLeastToGreatest() {
        listOfExpenses.sort(Comparator.comparingDouble(Expense::getAmount));
    }


    //REQUIRES: non-empty list of expenses
    //MODIFIES: this
    //EFFECTS: sorts list by Category name alphabetically
    public void sortByCategory() {
        ComparatorExpense comparatorExpense = new ComparatorExpense();
        Collections.sort(listOfExpenses, comparatorExpense);
    }

    //REQUIRES: non-empty list of expenses
    //EFFECTS: returns true if there is an expense with the given name else returns false
    public Boolean containsExpenseWithName(String name) {
        for (Expense e : listOfExpenses) {
            if (e.getName().toUpperCase().equals(name.toUpperCase())) {
                return true;
            }
        }
        return false;
    }


    //REQUIRES: a non-empty list of expenses
    //EFFECTS: returns the first expense with the highest amount. If there are two with the same amount
    //         returns the first expense found
    public Expense getHighestExpense() {
        Expense highestExpense = listOfExpenses.get(0);
        double highestAmount = listOfExpenses.get(0).getAmount();
        for (Expense e : listOfExpenses) {
            if (e.getAmount() > highestAmount) {
                highestExpense = e;
                highestAmount = e.getAmount();
            }
        }
        return highestExpense;
    }

    //REQUIRES: non-empty list of expenses
    //EFFECTS: returns the expense with the lowest amount
    //         if there are two with the same lowest amount then return the first expense found
    public Expense getLowestExpense() {
        Expense lowestExpense = listOfExpenses.get(0);
        double lowestAmount = listOfExpenses.get(0).getAmount();
        for (Expense e : listOfExpenses) {
            if (e.getAmount() < lowestAmount) {
                lowestExpense = e;
                lowestAmount = e.getAmount();
            }
        }
        return lowestExpense;
    }

    //REQUIRES: non-empty list of expenses
    //EFFECTS: returns the expense with the given name
    public Expense getExpenseByName(String name) {
        for (Expense e: listOfExpenses) {
            if (e.getName().toUpperCase().equals(name.toUpperCase())) {
                return e;
            }
        }
        return null;
    }

    //REQUIRES: non-empty list of expenses
    //EFFECTS: returns the expense at the given index
    public Expense getExpenseByPosition(int i) {
        return listOfExpenses.get(i);
    }

    //EFFECTS: returns the length of the list
    public int getLength() {
        return listOfExpenses.size();
    }
}
