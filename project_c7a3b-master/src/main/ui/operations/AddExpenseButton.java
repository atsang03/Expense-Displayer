package ui.operations;

import model.Categories;
import model.ListOfExpenses;
import ui.ExpenseAppInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//represents the add expense button that will add an expense to the list of expense
//referenced the simpledrawing player demo
public class AddExpenseButton extends Button {

    private ListOfExpenses listOfExpenses;
    private Categories listOfCategories;

    //CONSTRUCTOR
    //EFFECTS: creates an add expense button that will prompt the user to input data
    // and then adds an expense to the list
    public AddExpenseButton(ExpenseAppInterface expenseApp, JComponent parent, ListOfExpenses listOfExpenses,
                            Categories categoryList) {
        super(expenseApp,parent);
        this.listOfExpenses = listOfExpenses;
        this. listOfCategories = categoryList;
    }

    // MODIFIES: this
    // EFFECTS: associate button with new ClickHandler
    protected void addListener() {
        button.setActionCommand("Add Expense");
        button.addActionListener(new AddExpenseClickHandler());
    }

    //MODIFIES: this
    //EFFECTS:Creates a button with the name "Add Expense"
    protected void createButton(JComponent parent) {
        button = new JButton("Add Expense");
        addToParent(parent);
    }

    //MODIFIES: this
    //EFFECTS: adds the button to the given JComponent
    protected void addToParent(JComponent parent) {
        parent.add(button);
    }

    //Represents class that handles when someone clicks the button expense
    private class AddExpenseClickHandler implements ActionListener {

        JTextField name = new JTextField(15);
        JTextField amount = new JTextField(15);
        JTextField month  = new JTextField(15);
        JTextField requestedId = new JTextField(15);

        //EFFECTS: prompts user to input information for new expense and then
        // once finished it will add expense to the list of expenses
        @Override
        public void actionPerformed(ActionEvent e) {

            JFrame prompt = new JFrame();
            prompt.setLayout(new BorderLayout());
            prompt.setSize(new Dimension(230, 275));
            JPanel textArea = new JPanel();

            addToParent(textArea);

            prompt.add(textArea);
            add(new ConfirmAddExpense(textArea,name,amount,month,requestedId,listOfExpenses,listOfCategories));
            prompt.setVisible(true);
        }

        //Helper method
        //EFFECTS: adds things to  the parent
        private void addToParent(JComponent parent)  {

            JLabel amountRequest = new JLabel("Enter expense amount");
            JLabel nameRequest = new JLabel("Enter a unique name ");
            JLabel monthRequest = new JLabel("Enter a month between 1-12");
            JLabel idRequest = new JLabel("Enter an existing category id");

            parent.add(nameRequest);
            parent.add(name);

            parent.add(amountRequest);
            parent.add(amount);

            parent.add(monthRequest);
            parent.add(month);

            parent.add(idRequest);
            parent.add(requestedId);
        }
    }
}
