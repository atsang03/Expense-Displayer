package ui.operations;

import model.ListOfExpenses;
import ui.displays.ExpenseDisplayer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//represents the Combobox for the sorting choices
//referenced java's combo box demo
public class SelectionList extends JComboBox implements ActionListener {

    private String[] choices = {"Sort alphabetically",
            "Sort by greatest to least amount",
            "Sort by lowest to greatest amount", "Sort by months", "Sort by category", "Please select one"};

    private JComboBox selections;
    private ListOfExpenses listOfExpenses;

    //Constructor for the sorting selection list
    public SelectionList(JComponent parent, ListOfExpenses listOfExpenses) {
        this.listOfExpenses = listOfExpenses;
        createComboBox(parent);
        addToParent(parent);
        selections.addActionListener(this);
    }

    // MODIFIES: this
    //EFFECTS: creates the combo box and adds  it to the parent
    private void createComboBox(JComponent parent) {
        selections = new JComboBox(choices);
        selections.setSelectedIndex(5);
        addToParent(parent);
    }

    // MODIFIES: parent
    // EFFECTS:  adds the given button to the parent component
    private void addToParent(JComponent parent) {
        parent.add(selections);
    }

    //MODIFIES: listOfExpenses, ExpenseDisplayer.list
    //EFFECTS: once detected an action on the combo box it updates the list
    //         and also the order of the list of expenses
    public void actionPerformed(ActionEvent e) {
        JComboBox selection = (JComboBox) e.getSource();
        String choice = (String) selection.getSelectedItem();
        updateLists(choice);
    }

    //MODIFIES: listOfExpenses, ExpenseDisplayer.list
    //EFFECTS: depending on the given choice it sorts the displayer list and expenses list
    //         then sets the selection back to the 5th element in combo box
    protected void updateLists(String choice) {
        if (choice.equals("Sort alphabetically")) {
            choiceAlphabetical();
        } else if (choice.equals("Sort by greatest to least amount")) {
            choiceGreatestToLowest();
        } else if (choice.equals("Sort by months")) {
            choiceMonth();
        } else if (choice.equals("Sort by category")) {
            choiceCategory();
        } else if (choice.equals("Sort by lowest to greatest amount")) {
            choiceLowestToGreatest();
        }

        selections.setSelectedIndex(5);
    }

    //helper
    //MODIFIES: listOfExpenses, ExpenseDisplayer.list
    //EFFECTS: sorts list and displayer alphabetically
    private void choiceAlphabetical() {
        listOfExpenses.sortExpensesAlphabetically();
        addToDisplayer();
    }

    //helper
    //MODIFIES: listOfExpenses, ExpenseDisplayer.list
    //EFFECTS: sorts list and displayer by greatest to least
    private void choiceGreatestToLowest() {
        listOfExpenses.sortGreatestToLeast();
        addToDisplayer();
    }

    //helper
    //MODIFIES: listOfExpenses, ExpenseDisplayer.list
    //EFFECTS: sorts list and displayer by least to greatest
    private void choiceLowestToGreatest() {
        listOfExpenses.sortLeastToGreatest();
        addToDisplayer();
    }

    //helper
    //MODIFIES: listOfExpenses, ExpenseDisplayer.list
    //EFFECTS: sorts list and displayer by the lowest number to greatest number month
    private void choiceMonth() {
        listOfExpenses.sortExpensesByMonth();
        addToDisplayer();
    }

    //helper
    //MODIFIES: listOfExpenses, ExpenseDisplayer.list
    //EFFECTS: sorts list and displayer by the alphabetical order of the category
    private void choiceCategory() {
        listOfExpenses.sortByCategory();
        addToDisplayer();
    }


    //helper
    //MODIFIES: ExpenseDisplayer.list
    //EFFECTS: removes all elements in displayer then readds all expenses currently inside listOfExpenses
    private void addToDisplayer() {
        ExpenseDisplayer.list.removeAllElements();
        int expenseListSize = listOfExpenses.getLength();
        for (int i = 0; i < expenseListSize; i++) {
            ExpenseDisplayer.list.addElement("Name: " + listOfExpenses.getExpenseByPosition(i).getName()
                    + " Amount: " + listOfExpenses.getExpenseByPosition(i).getAmount()
                    + " Month: " + listOfExpenses.getExpenseByPosition(i).getMonth()
                    + " Category: " + listOfExpenses.getExpenseByPosition(i).getCategory().getName());
        }
    }

}
