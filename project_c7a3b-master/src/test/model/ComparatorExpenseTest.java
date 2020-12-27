package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ComparatorExpenseTest {
    private final ComparatorExpense expenseComparator = new ComparatorExpense();

    @Test
    void testCompare() {
        Category apples = new Category("apples",0);
        Category astro = new Category("astro",1);

        Expense expense1 = new Expense("first",123,12);
        expense1.setCategory(apples);

        Expense expense2 = new Expense("second",123,12);
        expense2.setCategory(astro);
        //tests to make check the return value is <= -1 (expense1 category is lexicographically
        // less than expense2 category name)
        assertTrue(-1 >= expenseComparator.compare(expense1,expense2));
        //tests to make check the return value is >= 1 (expense2 category is lexicographically
        // more than expense1 category name)
        assertTrue(1 <= expenseComparator.compare(expense2,expense1));

        expense2.changeCategory(apples);
        //test to make sure return value is == 0 (expense1 and expense 2 category are lexicographically the same
        assertEquals(0,expenseComparator.compare(expense1, expense2));
        assertEquals(0, expenseComparator.compare(expense2,expense1));

    }

}
