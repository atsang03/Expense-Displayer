package ui.operations;

import exceptions.OutOfBoundsException;
import model.Categories;
import model.ListOfExpenses;
import ui.ExpenseAppInterface;
import ui.displays.CategoryDisplayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//represents the remove category button
public class RemoveCategoryButton extends Button {

    Categories listOfCategories;
    ListOfExpenses listOfExpenses;

    //Constructor: creates the remove category button with the super
    public RemoveCategoryButton(ExpenseAppInterface expenseApp, JComponent parent, Categories listOfCategories,
                                ListOfExpenses listOfExpenses) {
        super(expenseApp, parent);
        this.listOfCategories = listOfCategories;
        this.listOfExpenses = listOfExpenses;
    }

    // MODIFIES: this
    //EFFECTS: creates the button and adds it to the given parent
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Remove Category");
        addToParent(parent);
    }


    //MODIFIES: parent
    //EFFECTS: adds the button to the given parent
    @Override
    protected void addToParent(JComponent parent) {
        parent.add(button);
    }

    // MODIFIES: this
    //EFFECTS: adds an action command and the action listener to the button
    @Override
    protected void addListener() {
        button.setActionCommand("Remove Category");
        button.addActionListener(new RemoveCategoryClickHandler());
    }


    //represents the action listener
    private class RemoveCategoryClickHandler implements ActionListener {

        //MODIFIES: listOfCategories, CategoryDisplayer.list
        //EFFECTS: removes the selected category if there is an existing expense
        //         that uses the currently selected category that is to be removed, then beeps
        //         if the Display has no more elements then it beeps else it removes
        //         the selected element from the displayer list and the actual list of categories
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = CategoryDisplayer.categoryList.getSelectedIndex();
            int expenseListSize = listOfExpenses.getLength();
            try {
                for (int x = 0; x < expenseListSize; x++) {
                    if (listOfExpenses.getExpenseByPosition(x).getCategory().equals(listOfCategories
                            .getCategoryByPosition(index))) {
                        Toolkit.getDefaultToolkit().beep();
                        return;
                    }
                }
                if (listOfCategories.getCategoryByPosition(index).getName().equals("Miscellaneous")) {
                    Toolkit.getDefaultToolkit().beep();
                } else {
                    CategoryDisplayer.list.remove(index);
                    listOfCategories.removeCategoryByPosition(index);

                    if (index == CategoryDisplayer.list.getSize()) {
                        index--;
                    }

                    CategoryDisplayer.categoryList.setSelectedIndex(index);
                }
            } catch (OutOfBoundsException outOfBoundsException) {
                System.out.println("Error Out Of Bounds");
            }
        }
    }
}
