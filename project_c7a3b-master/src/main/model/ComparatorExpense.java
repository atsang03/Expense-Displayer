package model;

import java.util.Comparator;

//Represents a comparator for expenses which is used to compare the category names lexicographically
//https://beginnersbook.com/2013/12/java-arraylist-of-object-sort-example-comparable-and-comparator/
//By: CHAITANYA SINGH
public class ComparatorExpense implements Comparator<Expense> {

    //EFFECTS: compares two expenses and their category names
    //         compares the two category names lexicographically
    //         returns 0 if the two strings are the same
    //         returns a negative number if expenseName1 is lexicographically less than the other
    //         returns a positive number if expenseName1 is lexicographically more than the other
    @Override
    public int compare(Expense expense1, Expense expense2) {
        String categoryName1 = expense1.getCategory().getName();
        String categoryName2 = expense2.getCategory().getName();

        return categoryName1.toUpperCase().compareTo(categoryName2.toUpperCase());
    }
}
