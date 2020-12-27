package ui.operations;

import ui.ExpenseAppInterface;

import javax.swing.*;

//represents the abstract class for buttons
//Referenced SimpleDrawing Demo
public abstract class Button extends JButton {

    protected JButton button;
    protected ExpenseAppInterface expenseApp;

    //Constructs the button using the abstract functions
    public Button(ExpenseAppInterface expenseApp, JComponent parent) {
        this.expenseApp = expenseApp;
        createButton(parent);
        addToParent(parent);
        addListener();
    }

    //MODIFIES: parent
    //EFFECTS: creates a button
    protected abstract void createButton(JComponent parent);

    //MODIFIES: parent
    //EFFECTS: adds given button to parent
    protected abstract void addToParent(JComponent parent);

    //MODIFIES: parent
    //EFFECTS: creates a actionListener
    protected abstract void addListener();


}
