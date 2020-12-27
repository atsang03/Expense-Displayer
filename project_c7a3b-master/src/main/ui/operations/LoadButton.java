package ui.operations;

import exceptions.OutOfBoundsException;
import model.Categories;
import model.ListOfExpenses;
import persistence.Reader;
import ui.ExpenseAppInterface;
import ui.displays.CategoryDisplayer;
import ui.displays.ExpenseDisplayer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

//represents the load save button
public class LoadButton extends Button {

    ListOfExpenses listOfExpenses;
    Categories listOfCategories;

    private final Reader reader = new Reader(JSON_FILE);

    private static final String JSON_FILE = "./data/Save.json";

    //Constructor for the load button given an expenseAppInterface and a
    public LoadButton(ExpenseAppInterface expenseApp, JComponent parent, ListOfExpenses listOfExpenses,
                      Categories listOfCategories) {
        super(expenseApp,parent);
        this.listOfExpenses = listOfExpenses;
        this.listOfCategories = listOfCategories;

    }

    //MODIFIES: this
    //EFFECTS: creates new button
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Load Categories and Expenses");
        addToParent(parent);
    }

    //MODIFIES: parent
    //EFFECTS: adds button to the parent
    @Override
    protected void addToParent(JComponent parent) {
        parent.add(button);
    }

    //MODIFIES: this
    //EFFECTS: adds action command and listener to button
    @Override
    protected void addListener() {
        button.setActionCommand("Save");
        button.addActionListener(new LoadDataClickHandler());
    }

    //MODIFIES: this
    //EFFECTS: loads the file data
    public void loadSave() {
        try {
            Categories readerCategoryList = reader.readCategories();
            for (int i = 0; i < readerCategoryList.getLength(); i++) {
                listOfCategories.insertCategory(reader.readCategories().getCategoryByPosition(i));
            }

            ListOfExpenses readerExpenseList = reader.readExpenses();
            for (int i = 0; i < readerExpenseList.getLength(); i++) {
                listOfExpenses.insertExpense(reader.readExpenses().getExpenseByPosition(i));
            }
        } catch (IOException e) {
            System.out.println("error while trying to load data!");
        } catch (OutOfBoundsException e) {
            System.out.println("Error Out Of Bounds");
        }
    }

    //HELPER
    //MODIFIES: ExpenseDisplayer.list
    //EFFECTS: adds elements to the displayer
    private void addToDisplayer() {
        for (int i = 0; i < listOfExpenses.getLength(); i++) {
            ExpenseDisplayer.list.addElement("Name: " + listOfExpenses.getExpenseByPosition(i).getName()
                    + " Amount: " + listOfExpenses.getExpenseByPosition(i).getAmount()
                    + " Month: " + listOfExpenses.getExpenseByPosition(i).getMonth()
                    + " Category: " + listOfExpenses.getExpenseByPosition(i).getCategory().getName());
        }

        for (int i = 0; i < listOfCategories.getLength(); i++) {
            try {
                CategoryDisplayer.list.addElement("Name: " + listOfCategories.getCategoryByPosition(i).getName()
                        + " Id: " + listOfCategories.getCategoryByPosition(i).getId());
            } catch (OutOfBoundsException e) {
                System.out.println("Error Out Of Bounds");
            }
        }
    }

    //represents the button action listener
    private class LoadDataClickHandler implements ActionListener {

        //MODIFIES: ListOfExpenses, ListOfCategories, ExpenseDisplayer.list, CategoryDisplayer.list
        //EFFECTS:  loads the saved file and then removes all elements from the displayers
        //          then for every newly loaded expense and category, adds them back to the displayer
        @Override
        public void actionPerformed(ActionEvent e) {

            int sizeExpenses = listOfExpenses.getLength();
            for (int i = 0; i < sizeExpenses; i++) {
                listOfExpenses.removeExpenseByPosition(0);
            }
            int sizeCategories = listOfCategories.getLength();
            for (int i = 0; i < sizeCategories; i++) {
                try {
                    listOfCategories.removeCategoryByPosition(0);
                } catch (OutOfBoundsException a) {
                    System.out.println("Error Out Of Bounds");
                }
            }

            loadSave();
            ExpenseDisplayer.list.removeAllElements();
            CategoryDisplayer.list.removeAllElements();

            addToDisplayer();
            ExpenseDisplayer.expenseList.setSelectedIndex(0);
            CategoryDisplayer.categoryList.setSelectedIndex(0);
        }


    }
}
