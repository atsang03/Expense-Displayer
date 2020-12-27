package ui.operations;

import model.Categories;
import model.ListOfExpenses;
import persistence.Saver;
import ui.ExpenseAppInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

//represents the save button
public class SaveButton extends Button {

    private ListOfExpenses listOfExpenses;
    private Categories listOfCategories;

    private Saver saver = new Saver(JSON_FILE);

    private static final String JSON_FILE = "./data/Save.json";


    //constructor for the save button
    public SaveButton(ExpenseAppInterface expenseApp, JComponent parent, ListOfExpenses listOfExpenses,
                      Categories listOfCategories) {
        super(expenseApp,parent);
        this.listOfExpenses = listOfExpenses;
        this.listOfCategories = listOfCategories;

    }

    //MODIFIES: this
    //EFFECTS: creates new button
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Save Categories and Expenses");
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
        button.addActionListener(new SaveDataClickHandler());
    }

    //represents the save button click handler action listener
    private class SaveDataClickHandler implements ActionListener {

        //EFFECTS: saves the elements currently inside listOfExpenses and listOfCategories
        @Override
        public void actionPerformed(ActionEvent e) {
            saveApp();
        }

        //EFFECTS: saves the list of expenses and categories into file
        public void saveApp() {
            try {
                saver.open();
                saver.save(listOfCategories,listOfExpenses);
                saver.close();
            } catch (FileNotFoundException e) {
                System.out.println("Can not find File!");
            }
        }
    }
}
