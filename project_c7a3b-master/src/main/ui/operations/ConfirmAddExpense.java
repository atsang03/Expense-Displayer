package ui.operations;

import model.Categories;
import model.Category;
import model.Expense;
import model.ListOfExpenses;
import ui.displays.ExpenseDisplayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Integer.parseInt;

//represents the add expense button inside the add expense prompt
public class ConfirmAddExpense extends JButton {

    private JButton button;
    private JTextField name;
    private JTextField amount;
    private JTextField month;
    private JTextField id;
    private ListOfExpenses listOfExpenses;
    private Categories listOfCategories;

    //constructor: creates the add expense button and adds it to the parent and adds an action listener
    public ConfirmAddExpense(JPanel parent, JTextField name, JTextField amount, JTextField month,
                             JTextField id, ListOfExpenses listOfExpenses, Categories listOfCategories) {
        this.name = name;
        this.amount = amount;
        this.month =  month;
        this.id = id;
        this.listOfExpenses = listOfExpenses;
        this.listOfCategories = listOfCategories;
        createButton(parent);
        addListener();
    }

    // MODIFIES: this
    //EFFECTS: creates the button and adds it to the parent
    private void createButton(JPanel parent) {
        button = new JButton("Add Expense");
        addToParent(parent);
    }

    //MODIFIES: parent
    //EFFECTS: adds the  button to the parent
    private void addToParent(JPanel parent) {
        parent.add(button);
    }

    // MODIFIES: this
    //EFFECTS: adds a listener and command to the button
    private void addListener() {
        button.setActionCommand("Add Expense");
        button.addActionListener(new AddExpenseClickHandler());
    }

    //REQUIRES: non-empty list of expenses
    //EFFECTS: checks if the list of expenses contains an expense with given name
    private boolean alreadyInList(String name) {
        return listOfExpenses.containsExpenseWithName(name);
    }

    //HELPER
    //EFFECTS: returns true if inputs are not valid
    private boolean checkInputs() {
        if (name.getText().equals("") || amount.getText().equals("") || month.getText().equals("")
                || id.getText().equals("") || !listOfCategories.containsCategoryWithId(parseInt(id.getText()))
                || 0  >= parseInt(month.getText()) || 13 <= parseInt(month.getText())) {
            return true;
        } else {
            return false;
        }
    }

    //represents the action listener for the button
    private class AddExpenseClickHandler implements ActionListener {

        //MODIFIES: listOfExpenses, ExpenseDisplayer.list
        //EFFECTS: checks if any text fields are empty or if there doesn;t exist a category with id
        // if so then beeps else it will create a new expense and set the expense's category
        // then it will add it to the expense display
        @Override
        public void actionPerformed(ActionEvent e) {
            if (checkInputs()) {
                Toolkit.getDefaultToolkit().beep();
            } else {
                Expense newExpense = new Expense(name.getText(),Double.parseDouble(amount.getText()),
                        parseInt(month.getText()));
                Category selectedCategory = listOfCategories.getCategoryById(parseInt(id.getText()));
                newExpense.setCategory(selectedCategory);

                if (alreadyInList(newExpense.getName())) {
                    Toolkit.getDefaultToolkit().beep();
                } else {
                    int index = ExpenseDisplayer.list.getSize();
                    listOfExpenses.insertExpense(newExpense);
                    ExpenseDisplayer.list.addElement("Name: " + newExpense.getName() + " Amount: "
                            + newExpense.getAmount()
                            + " Month: " + newExpense.getMonth() + " Category: " + selectedCategory.getName());
                    ExpenseDisplayer.expenseList.setSelectedIndex(index++);
                    name.setText("");
                    amount.setText("");
                    month.setText("");
                    id.setText("");
                }
            }
        }
    }
}
