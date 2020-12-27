package ui.displays;

import exceptions.OutOfBoundsException;
import model.Categories;

import javax.swing.*;

//represents the class that displays the list of categories
public class CategoryDisplayer extends JPanel {


    public static  JList categoryList;
    public static DefaultListModel list;
    private Categories categories;

    //constructor: creates the category displayer and adds it to the given parent
    public CategoryDisplayer(JComponent parent, Categories categoryList) {
        this.categories = categoryList;
        createCategoryList(parent);


    }

    //MODIFIES: this
    //EFFECTS: creates a new DefaultListModel and adds the Categories object data into the list
    private void createCategoryList(JComponent parent) {
        list = new DefaultListModel();
        try {
            for (int i = 0; i < categories.getLength(); i++) {
                list.addElement(" Name: " + categories.getCategoryByPosition(i).getName()
                        + " Id: " + categories.getCategoryByPosition(i).getId());

                categoryList = new JList(list);
                categoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                categoryList.setSelectedIndex(0);
                categoryList.setVisibleRowCount(15);
                JScrollPane expenseListScrollPane = new JScrollPane(categoryList);

                parent.add(expenseListScrollPane);
            }
        } catch (OutOfBoundsException outOfBoundsException) {
            System.out.println("Error Out Of Bounds");
        }
    }
}
