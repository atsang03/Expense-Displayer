package ui.displays;

import model.ListOfExpenses;

import javax.swing.*;


//represents the expense list displayer
//referenced Java's ListDemo
public class ExpenseDisplayer extends JPanel {

    public static  JList expenseList;
    public static DefaultListModel list;
    private ListOfExpenses listOfExpenses;

    //Constructs a expense displayer
    //referenced java listDemoProject
    public ExpenseDisplayer(JComponent parent, ListOfExpenses listofExpenses) {
        this.listOfExpenses = listofExpenses;
        createExpenseList(parent);
    }

    //MODIFIES: this
    //EFFECTS: creates a new DefaultListModel and adds the listOfExpenses object data into the list
    private void createExpenseList(JComponent parent) {
        list = new DefaultListModel();
        for (int i = 0; i < listOfExpenses.getLength(); i++) {
            list.addElement(" Name: " + listOfExpenses.getExpenseByPosition(i).getName()
                    + " Amount: " + listOfExpenses.getExpenseByPosition(i).getAmount()
                    + " Month: " + listOfExpenses.getExpenseByPosition(i).getMonth());
        }

        expenseList = new JList(list);
        expenseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        expenseList.setSelectedIndex(0);
        expenseList.setVisibleRowCount(15);
        JScrollPane expenseListScrollPane = new JScrollPane(expenseList);

        parent.add(expenseListScrollPane);
    }




}
