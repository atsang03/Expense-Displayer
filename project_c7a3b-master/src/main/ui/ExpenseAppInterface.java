package ui;

import model.Categories;
import model.ListOfExpenses;
import ui.displays.CategoryDisplayer;
import ui.displays.ExpenseDisplayer;
import ui.operations.*;

import javax.swing.*;
import java.awt.*;

//Represents the GUI of the expense app
public class ExpenseAppInterface extends JFrame {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;

    private JFrame gui;

    private ListOfExpenses expenseList = new ListOfExpenses();
    private Categories categoryList = new Categories();



    //CONSTRUCTOR
    //EFFECTS: creates the GUI window for the Expense App
    public ExpenseAppInterface() {
        super("Expense Application");

        createAppInterface();


    }

    //MODIFIES this
    //EFFECTS: creates the window for the app
    //Referenced from SimpleDrawingPlayer Demo
    private void createAppInterface() {
        gui = new JFrame("Expense Application");
        gui.setLayout(new BorderLayout());
        gui.setSize(new Dimension(WIDTH,HEIGHT));
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createButtons();
        createComboBox();
        displayExpenseList();
        displayCategoryList();
        gui.setVisible(true);


    }

    //MODIFIES this
    //EFFECTS: creates buttons that users can use
    private void createButtons() {
        JPanel buttonArea = new JPanel();
        buttonArea.setLayout(new GridLayout(6,0));
        buttonArea.setSize(new Dimension(50, 50));
        gui.add(buttonArea, BorderLayout.SOUTH);

        add(new AddCategoryButton(this,buttonArea,categoryList));
        add(new AddExpenseButton(this,buttonArea,expenseList,categoryList));
        add(new EditCategoryButton(this,buttonArea));
        add(new EditExpenseButton(this,buttonArea));
        add(new RemoveCategoryButton(this,buttonArea,categoryList, expenseList));
        add(new RemoveExpenseButton(this,buttonArea,expenseList));
        add(new SaveButton(this,buttonArea,expenseList,categoryList));
        add(new LoadButton(this,buttonArea,expenseList,categoryList));

    }

    //MODIFIES: this
    //EFFECTS: creates a list that displays the expenses
    private void displayExpenseList() {
        JPanel displayArea = new JPanel();
        displayArea.setLayout(new BoxLayout(displayArea,BoxLayout.Y_AXIS));
        JLabel expenseTitle = new JLabel("Expenses");
        displayArea.add(expenseTitle);
        gui.add(displayArea,BorderLayout.EAST);
        ExpenseDisplayer expenseDisplayer = new ExpenseDisplayer(displayArea, expenseList);

        displayArea.add(expenseDisplayer);
    }

    //MODIFIES: this
    //EFFECTS: creates a list that displays the categories
    private void displayCategoryList() {
        JPanel displayArea = new JPanel();
        displayArea.setLayout(new BoxLayout(displayArea,BoxLayout.Y_AXIS));
        JLabel categoryTitle = new JLabel("Categories");
        displayArea.add(categoryTitle);
        gui.add(displayArea,BorderLayout.WEST);
        CategoryDisplayer categoryDisplayer = new CategoryDisplayer(displayArea, categoryList);

        displayArea.add(categoryDisplayer);
    }

    //MODIFIES: this
    //EFFECTS: creates a combo box that users can use to select sorting
    private void createComboBox() {
        JPanel comboBoxArea = new JPanel();
        gui.add(comboBoxArea,BorderLayout.NORTH);

        SelectionList sortingOptions = new SelectionList(comboBoxArea,expenseList);
        add(sortingOptions);
    }
}
