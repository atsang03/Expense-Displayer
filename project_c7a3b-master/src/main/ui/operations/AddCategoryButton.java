package ui.operations;

import model.Categories;

import ui.ExpenseAppInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//represents the add category button
public class AddCategoryButton extends Button {

    private Categories categories;

    //CONSTRUCTOR
    //EFFECTS: creates an add expense button that will prompt the user to input data
    // and then adds an expense to the list
    public AddCategoryButton(ExpenseAppInterface expenseApp, JComponent parent, Categories categories) {
        super(expenseApp,parent);
        this.categories = categories;
    }

    // MODIFIES: this
    // EFFECTS: associate button with new ClickHandler
    @Override
    protected void addListener() {
        button.setActionCommand("Add Category");
        button.addActionListener(new AddCategoryClickHandler());
    }

    //MODIFIES: this
    //EFFECTS:Creates a button with the name "Add Category"
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Add Category");
        addToParent(parent);
    }

    //MODIFIES: this
    //EFFECTS: adds the button to the given JComponent
    @Override
    protected void addToParent(JComponent parent) {
        parent.add(button);
    }

    //Represents class that handles when someone clicks the button expense
    private class AddCategoryClickHandler implements ActionListener {

        JTextField name = new JTextField(15);
        JTextField id = new JTextField(15);

        //EFFECTS: prompts user to input information for new expense and then
        // once finished it will add expense to the list of expenses
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame prompt = new JFrame();
            prompt.setLayout(new BorderLayout());
            JPanel textArea = new JPanel();
            prompt.setSize(new Dimension(230,250));

            addToParent(textArea);

            prompt.add(textArea);
            add(new ConfirmAddCategory(textArea,name,id,categories));
            prompt.setVisible(true);
        }

        //Helper method
        //MODIFIES: parent
        //EFFECTS: adds things to  the parent
        private void addToParent(JComponent parent)  {

            JLabel nameRequest = new JLabel("Please provide a unique name");
            JLabel idRequest = new JLabel("Please provide a unique id ");

            parent.add(nameRequest);
            parent.add(name);

            parent.add(idRequest);
            parent.add(id);


        }
    }

}
