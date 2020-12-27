package ui.operations;

import model.ListOfExpenses;
import ui.ExpenseAppInterface;
import ui.displays.ExpenseDisplayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//represents the button to remove an expense
public class RemoveExpenseButton extends Button {

    private ListOfExpenses listOfExpenses;

    //constructor: uses the button constructor to construct the button
    public RemoveExpenseButton(ExpenseAppInterface expenseApp, JComponent parent, ListOfExpenses listOfExpenses) {
        super(expenseApp, parent);
        this.listOfExpenses = listOfExpenses;
    }

    // MODIFIES: this
    //EFFECTS: creates the button and adds it to the parent
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Remove Expense");
        addToParent(parent);
    }

    //MODIFIES: parent
    //EFFECTS: adds the button to the given parent
    @Override
    protected void addToParent(JComponent parent) {
        parent.add(button);
    }

    //MODIFIES: this
    //EFFECTS: adds an action listener and command to the button
    @Override
    protected void addListener() {
        button.setActionCommand("Remove Expense");
        button.addActionListener(new RemoveExpenseClickHandler());
    }

    //represents the action listener for the button
    private class RemoveExpenseClickHandler implements ActionListener {

        //MODIFIES: ExpenseDisplayer.list, listOfExpenses
        //EFFECTS: removes the selected button
        // if the list is has no objects inside then it beeps
        // else it will remove the selected butto nand autoselected the one beside it
        @Override
        public void actionPerformed(ActionEvent e) {

            if (ExpenseDisplayer.list.getSize() == 0) {
                Toolkit.getDefaultToolkit().beep();
            } else {
                int index = ExpenseDisplayer.expenseList.getSelectedIndex();
                ExpenseDisplayer.list.remove(index);
                listOfExpenses.removeExpenseByPosition(index);

                if (index == ExpenseDisplayer.list.getSize()) {
                    index--;
                }

                ExpenseDisplayer.expenseList.setSelectedIndex(index);


            }
        }
    }
}
