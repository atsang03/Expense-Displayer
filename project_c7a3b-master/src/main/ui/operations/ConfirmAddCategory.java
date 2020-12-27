package ui.operations;

import model.Categories;
import model.Category;
import ui.displays.CategoryDisplayer;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Integer.parseInt;

//represents the add category button for the pop up from clicking add category
public class ConfirmAddCategory extends JButton {

    private JButton button;
    private JTextField name;
    private JTextField id;
    private Categories listOfCategories;


    //Constructor: creates the confirm add category button and sets the given name,id and category list
    // also creates the buutton and adds it to the  parent and adds a action listener to the button
    public ConfirmAddCategory(JPanel parent, JTextField name, JTextField id, Categories categories) {
        this.name = name;
        this.id = id;
        this.listOfCategories = categories;
        createButton(parent);
        addListener();
    }

    // MODIFIES: this
    //EFFECTS: creates the button with the name "add category" and adds it to the JPanel parent
    private void createButton(JPanel parent) {
        button = new JButton("Add Category");
        addToParent(parent);
    }

    //MODIFIES: parent
    //EFFECTS: adds the button to the given parent
    private void addToParent(JPanel parent) {
        parent.add(button);
    }

    // MODIFIES: this
    //EFFECTS: adds an actionlistener and action command to the button
    private void addListener() {
        button.setActionCommand("Add Expense");
        button.addActionListener(new AddCategoryClickHandler());
    }

    //checks if there is a category with the same name or same id number
    private boolean alreadyInList(Category c) {
        return listOfCategories.containsCategoryByIdOrName(c);
    }


    //represents the action listener for the button
    //refereced java's list demo
    private class AddCategoryClickHandler implements ActionListener {

        //MODIFIES: listOfCategories, categoryDisplayer.list
        //EFFECTS:  if any text fields are empty then it beeps
        //          if there already exists a category with name or id then beeps
        //          else it will create a new category and inserts it into the listOfCatetgories
        //          and then add it to the displayer
        @Override
        public void actionPerformed(ActionEvent e) {

            if (name.getText().equals("") || id.getText().equals("")) {
                Toolkit.getDefaultToolkit().beep();
            } else {
                Category newCategory = new Category(name.getText(),parseInt(id.getText()));

                if (alreadyInList(newCategory)) {
                    Toolkit.getDefaultToolkit().beep();
                } else {
                    int index = CategoryDisplayer.list.getSize();
                    listOfCategories.insertCategory(newCategory);
                    CategoryDisplayer.list.addElement("Name: " + newCategory.getName() + " Id: "
                            + newCategory.getId());
                    CategoryDisplayer.categoryList.setSelectedIndex(index++);
                    name.setText("");
                    id.setText("");
                }
            }
        }
    }

}
