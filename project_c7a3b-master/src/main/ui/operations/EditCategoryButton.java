package ui.operations;

import ui.ExpenseAppInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//represents the edit category button
public class EditCategoryButton extends Button {

    //constructor creates an edit category button
    public EditCategoryButton(ExpenseAppInterface expenseApp, JComponent parent) {
        super(expenseApp, parent);
    }

    //MODIFIES: this
    //EFFECTS: creates a new JButton Edit Category and adds it to the parent
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Edit Category");
        addToParent(parent);
    }

    //MODIFIES: parent
    //EFFECTS: adds button to the parent
    @Override
    protected void addToParent(JComponent parent) {
        parent.add(button);
    }

    //MODIFIES: this
    //EFFECTS: adds a ActionListener for the button
    @Override
    protected void addListener() {
        button.setActionCommand("Edit Category");
        button.addActionListener(new EditCategoryClickHandler());
    }

    //represents the actionlistner for the button
    private class EditCategoryClickHandler implements ActionListener {

        //EFFECTS: once the button is clicked it will prompt the user to edit a category
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame prompt = new JFrame();
            prompt.setLayout(new BorderLayout());
            prompt.setSize(new Dimension(250, 250));
            JPanel test = new JPanel();
            JButton button = new JButton("Edit Category");
            test.add(button);
            prompt.add(test);
            prompt.setVisible(true);
        }
    }
}
