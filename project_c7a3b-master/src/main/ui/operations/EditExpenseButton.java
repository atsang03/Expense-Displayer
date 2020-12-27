package ui.operations;

import ui.ExpenseAppInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//represents the edit expense button
public class EditExpenseButton extends Button {

    //constructor for the edit expense button using the super Button
    public EditExpenseButton(ExpenseAppInterface expenseApp, JComponent parent) {
        super(expenseApp, parent);
    }

    //EFFECTS: creates the button and adds it to the parent
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Edit Expense");
        addToParent(parent);
    }

    //MODIFIES: parent
    //EFFECTS: adds the button to the given parent
    @Override
    protected void addToParent(JComponent parent) {
        parent.add(button);
    }

    // MODIFIES: this
    //EFFECTS: adds an actionlistener to the button
    @Override
    protected void addListener() {
        button.setActionCommand("Edit Expense");
        button.addActionListener(new EditExpenseClickHandler());
    }


    //represents the Action Listener for the button
    private class EditExpenseClickHandler implements ActionListener {

        //EFFECTS: It will prompt the user to edit the selected expense
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame prompt = new JFrame();
            prompt.setLayout(new BorderLayout());
            prompt.setSize(new Dimension(250, 250));
            JPanel test = new JPanel();
            JButton button = new JButton("Edit Expense");
            test.add(button);
            prompt.add(test);
            prompt.setVisible(true);
        }
    }
}
