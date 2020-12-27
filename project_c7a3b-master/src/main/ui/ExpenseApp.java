package ui;


import exceptions.OutOfBoundsException;
import model.Categories;
import model.Category;
import model.Expense;
import model.ListOfExpenses;
import persistence.Reader;
import persistence.Saver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import static java.lang.Integer.parseInt;
//NO LONGER REQUIRED CAN BE REMOVED

//Represents the Expense application and all its functions
public class ExpenseApp {
    private static final String JSON_FILE = "./data/Save.json";

    private ListOfExpenses expenses;
    private Categories categories;

    private Scanner scanner;

    private Saver saver;
    private Reader reader;

    //EFFECTS: runs the expense application
    public ExpenseApp() {
        runApp();
    }

    //MODIFIES: this
    //EFFECTS: runs the app and keeps it running
    private void runApp() {
        boolean keepRunning = true;
        String request;
        initialize();
        while (keepRunning) {
            menu();
            request = scanner.next().toLowerCase();
            if (request.equals("q")) {
                keepRunning = false;
                saveApp();
                System.out.println("Your list has been automatically saved!");
                System.out.println("End Application");
            } else {
                requestedOption(request);

            }
        }
    }

    //EFFECTS: saves the list of expenses and categories into file
    public void saveApp() {
        try {
            saver.open();
            saver.save(categories,expenses);
            saver.close();
        } catch (FileNotFoundException e) {
            System.out.println("Can not find File!");
        }
    }

    //MODIFIES: this
    //EFFECTS: loads the file data
    public void loadSave() {
        try {
            expenses = reader.readExpenses();
            categories = reader.readCategories();
        } catch (IOException e) {
            System.out.println("error while trying to load data!");
        }
    }

    //MODIFIES: this
    //EFFECTS: initializes an empty list of expenses, a list of categories
    //         with a default category miscellaneous with id 0
    private void initialize() {

        expenses = new ListOfExpenses();
        categories = new Categories();
        scanner = new Scanner(System.in).useDelimiter("\n");

        saver = new Saver(JSON_FILE);
        reader = new Reader(JSON_FILE);
        Category miscellaneous = new Category("Miscellaneous",0);
        categories.insertCategory(miscellaneous);
        System.out.println("Would you like to load your previous save? [Y] / [N]");

        boolean done = false;
        while (!done) {
            String loadAnswer = scanner.next().toLowerCase();
            if (loadAnswer.equals("y")) {
                loadSave();
                done = true;
            } else if (loadAnswer.equals("n")) {
                done = true;
            } else {
                System.out.println("wrong input!");
            }
        }
    }

    //EFFECTS: lays out the possible menu selections
    private void menu() {
        System.out.println("\nCreate new expense [1]");
        System.out.println("Edit existing expense [2]");
        System.out.println("Remove existing expense [3]");
        System.out.println("Create new Category [4]");
        System.out.println("Remove existing category [5]");
        System.out.println("Sort expense list [6]");
        System.out.println("Open list of expenses [7]");
        System.out.println("Open list of categories [8]");
        System.out.println("Exit out of application [q]");
    }

    //EFFECTS: based on input it will do what is requested
    private void requestedOption(String s) {
        if (s.equals("1") || s.equals("2") || s.equals("3") || s.equals("4")
                || s.equals("5") || s.equals("6") || s.equals("7") || s.equals("8")) {
            mainOptions(s);
        } else {
            System.out.println("\nInvalid selection.");
        }
    }

    //HELPER to be used to shorten method requestedOptions()
    //EFFECTS: based on input it will do what is requested
    private void mainOptions(String s) {
        if (s.equals("1")) {
            createExpense();
        } else if (s.equals("2")) {
            editExpense();
        } else if (s.equals("3")) {
            removeExpense();
        } else if (s.equals("4")) {
            createCategory();
        } else if (s.equals("5")) {
            removeCategory();
        } else if (s.equals("6")) {
            sortExpenses();
        } else if (s.equals("7")) {
            showExpenses();
        } else if (s.equals("8")) {
            showCategories();
        }
    }

    //MODIFIES: this
    //EFFECTS: creates and adds a new expense to the list of expenses
    private void createExpense() {
        String name = tryNextName();

        double amount = tryNextAmount();

        int month = tryNextMonth();

        int categoryId = createExpenseCategory();
        if (!checkCategoryId(categoryId)) {
            System.out.println("No category with the id " + categoryId + " exists!");
            return;
        }
        Expense e = new Expense(name,amount,month);
        e.setCategory(categories.getCategoryById(categoryId));
        expenses.insertExpense(e);
        System.out.println("New expense added to the list!");
    }

    //HELPER
    //EFFECTS: asks for an expense amount and try's to get nextDouble. If can not then repeat until user gives
    // appropriate input
    private double tryNextAmount() {
        System.out.println("Enter the expense amount.");
        double amount = 0;
        while (true) {
            if (scanner.hasNextDouble()) {
                amount = scanner.nextDouble();
                break;
            } else {
                System.out.println("Enter a number!");
                scanner.next();
            }
        }
        return amount;

    }

    //HELPER
    //EFFECTS: asks user to input a name and returns that name if it exists then prompts for another input until
    // given a unique String that isnt alaready inside Expense list
    private String tryNextName() {
        System.out.println("Enter the expense's name.");
        String name = "";
        while (true) {
            name = scanner.next();
            if (checkExpenseNames(name)) {
                System.out.println("\nThere already exists a expense with this name!\n");
            } else {
                break;
            }
        }
        return name;
    }

    //HELPER
    //EFFECTS: asks user to input a month number between 1-12 and returns that int if not int or range then prompts
    //for anotger input
    private int tryNextMonth() {
        System.out.println("Enter the month of purchase 1-12.");
        int month = 0;
        while (true) {
            if (scanner.hasNextInt()) {
                month = scanner.nextInt();
                if (month > 12 || month < 1) {
                    System.out.println("Please enter a month number between 1-12");
                } else {
                    break;
                }
            } else {
                System.out.println("Please enter a month number between 1-12");
                scanner.next();
            }
        }
        return month;
    }

    //HELPER
    //EFFECTS: asks user to input a category Id and returns that int
    private int createExpenseCategory() {
        System.out.println("Which category? Enter category number.");
        displayCategories();
        int categoryId = scanner.nextInt();
        return categoryId;
    }

    //MODIFIES: this
    //EFFECTS: After choosing the desired expense to edit, edits whichever part of the desired expense
    private void editExpense() {
        if (emptyExpenses()) {
            return;
        }
        System.out.println("\nWhich expense do you want to edit?\n");
        showExpenses();
        System.out.println("\nEnter name of expense.");

        String selection = scanner.next().toLowerCase();
        if (!checkExpenseNames(selection)) {
            System.out.println("\nNo expense with given name exists in list!\n");
            return;
        }
        printExpense(selection);
        Expense selectedExpense = expenses.getExpenseByName(selection);
        printExpenseEditOptions();
        int i = scanner.nextInt();
        if (i < 1 || i > 4) {
            System.out.println("\nInvalid Option!\n");
            return;
        }
        changeExpenseSelectedOption(i,selectedExpense);
        System.out.println("Edit complete! \n");
    }

    //MODIFIES: this
    //EFFECTS: removes the desired expense from the list of expenses
    private void removeExpense() {
        if (emptyExpenses()) {
            return;
        }
        System.out.println("\nWhich expense do you want to remove?\n");
        showExpenses();
        System.out.println("\nEnter name of expense.");
        String selection = scanner.next().toLowerCase();
        if (expenses.containsExpenseWithName(selection)) {
            expenses.removeExpenseByName(selection);
            System.out.println("Remove completed!\n");
            return;
        }
        System.out.println("Could not find expense with given name!\n");

    }

    //MODIFIES: this
    //EFFECTS: creates and adds the new category with given name and id to the list of categories
    private void createCategory() {
        Category newCategory;
        System.out.println("\nEnter the new category's name");
        String name = scanner.next();
        if (checkCategoryName(name)) {
            System.out.println("\nThere is already a category with the name " + name + "\n");
            return;
        }
        System.out.println("Enter desired category id (Cannot use and id that already exists inside list)");
        int id = scanner.nextInt();
        if (checkCategoryId(id)) {
            System.out.println("\nThe desired id already exists inside the list\n");
            return;
        }
        newCategory = new Category(name,id);
        categories.insertCategory(newCategory);
        System.out.println("New Category made!\n");
    }

    //MODIFIES: this
    //EFFECTS: removes the desired category from the list of categories
    private void removeCategory() {
        System.out.println("\nWhich category do you want to remove?");
        displayCategories();
        System.out.println("Enter the id of the category that you want to remove");
        System.out.println("Cannot remove the category: Miscellaneous - 0 ");

        int requestedCategoryId = scanner.nextInt();
        if (requestedCategoryId == 0) {
            System.out.println("Cannot remove requested category!");
            return;
        }
        categories.removeCategoryById(requestedCategoryId);
        System.out.println("Category successfully removed!\n");
    }

    //MODIFIES: this
    //EFFECTS: sorts out the list of expenses depending on which selected choice
    //         high to low, low to high, alphabetically, category, by months
    private void sortExpenses() {
        if (emptyExpenses()) {
            return;
        }
        System.out.println("\nHow would you like to sort the expenses?");
        printExpenseSortOptions();
        String request = scanner.next();

        if (request.equals("1")) {
            expenses.sortExpensesAlphabetically();
        } else if (request.equals("2")) {
            expenses.sortGreatestToLeast();
        } else if (request.equals("3")) {
            expenses.sortLeastToGreatest();
        } else if (request.equals("4")) {
            expenses.sortExpensesByMonth();
        } else if (request.equals("5")) {
            expenses.sortByCategory();
        } else {
            System.out.println("Invalid Option!\n");
        }
    }

    //EFFECTS: displays the list of expenses
    private void showExpenses() {
        if (emptyExpenses()) {
            return;
        }
        for (int i = 0; i < expenses.getLength(); i++) {
            System.out.print("Name: " + expenses.getExpenseByPosition(i).getName() + " ");
            System.out.print("Amount: " + expenses.getExpenseByPosition(i).getAmount() + " ");
            System.out.print("Month: " + expenses.getExpenseByPosition(i).getMonth() + " ");
            System.out.print("Category: " + expenses.getExpenseByPosition(i).getCategory().getName());
            System.out.println();
        }
    }

    //EFFECTS: displays the list of categories
    private void showCategories() {
        System.out.println();
        try {
            for (int i = 0; i < categories.getLength(); i++) {
                System.out.print("Name: " + categories.getCategoryByPosition(i).getName() + " ");
                System.out.print("Id: " + categories.getCategoryByPosition(i).getId());
            }
        } catch (OutOfBoundsException outOfBoundsException) {
            System.out.println("Error Out Of Bounds");
        }
        System.out.println();

    }

    //EFFECTS: checks if there is an expense with the same name as input returns true if there is a expense with name
    private boolean checkExpenseNames(String s) {
        boolean exists = false;
        s = s.toLowerCase();
        for (int i = 0; i < expenses.getLength(); i++) {
            if (expenses.getExpenseByPosition(i).getName().toLowerCase().equals(s)) {
                exists = true;
            }
        }
        return exists;
    }

    //EFFECTS: display list of category options
    private void displayCategories() {
        try {
            for (int i = 0; i < categories.getLength(); i++) {
                System.out.println(categories.getCategoryByPosition(i).getName()
                        + " - "
                        + categories.getCategoryByPosition(i).getId() + "\n");
            }
        } catch (OutOfBoundsException outOfBoundsException) {
            System.out.println("Error Out Of Bounds");
        }
    }

    //EFFECTS: checks if there is a category with the requested Id returns true if there exists a category with id
    private boolean checkCategoryId(int id) {
        try {
            for (int i = 0; i < categories.getLength(); i++) {
                if (id == categories.getCategoryByPosition(i).getId()) {
                    return true;
                }
            }
        } catch (OutOfBoundsException outOfBoundsException) {
            System.out.println("Error Out Of Bounds");
        }
        return false;
    }

    //EFFECTS: checks if there is a category that has the same name as the input string
    private boolean checkCategoryName(String name) {
        try {
            for (int i = 0; i < categories.getLength(); i++) {
                if (name.equals(categories.getCategoryByPosition(i).getName())) {
                    return true;
                }
            }
        } catch (OutOfBoundsException outOfBoundsException) {
            System.out.println("Error Out Of Bounds");

        } finally {
            return false;
        }
    }

    //EFFECTS: prints out the info of requested expense by name
    private void printExpense(String s) {
        System.out.print("Name: " + expenses.getExpenseByName(s).getName() + " ");
        System.out.print("Amount: " + expenses.getExpenseByName(s).getAmount() + " ");
        System.out.print("Month: " + expenses.getExpenseByName(s).getMonth() + " ");
        System.out.print("Category: " + expenses.getExpenseByName(s).getCategory().getName());
        System.out.println();
    }

    //EFFECTS: prints the possible parts of the expense that can be edited
    private void printExpenseEditOptions() {
        System.out.println("Name [1]");
        System.out.println("Amount [2]");
        System.out.println("Month [3]");
        System.out.println("Category [4]");
    }

    //EFFECTS: prints the possible sorting selections
    private void printExpenseSortOptions() {
        System.out.println("Alphabetically [1]");
        System.out.println("Highest expense amount to lowest [2]");
        System.out.println("Lowest expense amount to highest [3]");
        System.out.println("By months [4]");
        System.out.println("By category name [5]");
    }

    //MODIFIES: selected
    //EFFECTS: changes the selected part of the expense
    private void changeExpenseSelectedOption(int i,Expense selected) {
        if (i == 1) {
            System.out.println("Enter new Name");
            selected.changeName(tryNextName());
        } else if (i == 2) {
            System.out.println("Enter new Amount");
            selected.changeAmount(tryNextAmount());
        } else if (i == 3) {
            System.out.println("Enter new Month");
            selected.changeMonth(tryNextMonth());
        } else if (i == 4) {
            System.out.println("Enter new Category Id");
            displayCategories();
            String newId = scanner.next();
            if (checkCategoryId(parseInt(newId))) {
                selected.changeCategory(categories.getCategoryById(parseInt(newId)));
            }
        }
    }

    //EFFECTS: returns true if list of expenses is empty else false
    private boolean emptyExpenses() {
        if (expenses.getLength() == 0) {
            System.out.println("\nYour list of expenses is empty!\n");
            return true;
        }
        return false;
    }
}