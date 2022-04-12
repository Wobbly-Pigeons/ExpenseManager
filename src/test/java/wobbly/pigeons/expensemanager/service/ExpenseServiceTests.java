package wobbly.pigeons.expensemanager.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import wobbly.pigeons.expensemanager.model.Employee;
import wobbly.pigeons.expensemanager.model.Expense;
import wobbly.pigeons.expensemanager.model.ExpenseCategory;
import wobbly.pigeons.expensemanager.model.ReceiptStatuses;
import wobbly.pigeons.expensemanager.repository.ExpenseRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ExpenseServiceTests {

    @Autowired
    private ExpenseRepository expenseRepository;

    // 3 expenses

    @Test
    void getExpensesList() {

        //Given
        Byte[] receiptByte = {123, 124, 12, 42};
        ExpenseCategory food = null;
        ExpenseCategory travel = null; //is making trouble with this ExpenseCategory Object
        ReceiptStatuses currentStatus = ReceiptStatuses.INCOMPLETE;
        Employee thisEmployee = new Employee();
        thisEmployee.setId(1L);
        thisEmployee.setName("Miguel");

        Expense exp1 = new Expense(
                receiptByte, //Receipt
                food,       // Category
                "USD",      // Local Currency
                LocalDateTime.of(2022, Month.MARCH, 30, 10, 30), //Date of purchase
                13L,        // Amount
                true,       // CC from company
                currentStatus, //Status of the expense
                "American Lunch", // name of the item
                "Some description here", //Description
                thisEmployee // Employee
        );

        Expense exp2 = new Expense(
                receiptByte,
                travel,     // Category
                "USD",      // Local Currency
                LocalDateTime.of(2022, Month.MARCH, 31, 11, 32), //Date of purchase
                24L,        // Amount
                true,       // CC from company
                currentStatus, //Status of the expense
                "American Lunch", // name of the item
                "Some description here", //Description
                thisEmployee // Employee
        );
        expenseRepository.save(exp1);
        expenseRepository.save(exp2);


        // When
        List<Expense> all = expenseRepository.findAll();

        for (Expense expense : all) {
            System.out.println(expense.getId());
            System.out.println(expense.getCategory());
            System.out.println(expense.getUser().getName());
        }

        // Then
        assertThat(all.size()).isEqualTo(2);

    }

    @Test
    void getExpenseByCategory() {

        //Given data
        //Given
        Byte[] receiptByte = {123, 124, 12, 42};
        ExpenseCategory food = ExpenseCategory.FOOD;
        ExpenseCategory travel = ExpenseCategory.TRAVEL;
        ReceiptStatuses currentStatus = ReceiptStatuses.INCOMPLETE;
        Employee thisEmployee = new Employee();
        thisEmployee.setId(1L);
        thisEmployee.setName("Miguel");

        Expense exp1 = new Expense(
                receiptByte, //Receipt
                food,       // Category
                "USD",      // Local Currency
                LocalDateTime.of(2022, Month.MARCH, 30, 10, 30), //Date of purchase
                13L,        // Amount
                true,       // CC from company
                currentStatus, //Status of the expense
                "American Lunch", // name of the item
                "Some description here", //Description
                thisEmployee // Employee
        );

        Expense exp2 = new Expense(
                receiptByte,
                travel,     // Category
                "USD",      // Local Currency
                LocalDateTime.of(2022, Month.MARCH, 31, 11, 32), //Date of purchase
                24L,        // Amount
                true,       // CC from company
                currentStatus, //Status of the expense
                "American Lunch", // name of the item
                "Some description here", //Description
                thisEmployee // Employee
        );
        Expense exp3 = new Expense(
                receiptByte, //Receipt
                food,       // Category
                "USD",      // Local Currency
                LocalDateTime.of(2022, Month.MARCH, 30, 14, 30), //Date of purchase
                23L,        // Amount
                true,       // CC from company
                currentStatus, //Status of the expense
                "American Lunch", // name of the item
                "Some description here", //Description
                thisEmployee // Employee
        );
        expenseRepository.save(exp1);
        expenseRepository.save(exp2);
        expenseRepository.save(exp3);

        List<Expense> all = expenseRepository.findAll();
        List<Expense> byCategory = new ArrayList<Expense>();

        for (Expense expense : all) {
            if (expense.getCategory().toString().equalsIgnoreCase("food")) {
                byCategory.add(expense);
            }
        }

        assertThat(byCategory.size()).isEqualTo(2);
    }

    @Test
    void getExpenseBySubmissionDate() {
        //Given
        Byte[] receiptByte = {123, 124, 12, 42};
        ExpenseCategory food = null;
        ExpenseCategory travel = null; //is making trouble with this ExpenseCategory Object
        ReceiptStatuses currentStatus = ReceiptStatuses.INCOMPLETE;
        Employee thisEmployee = new Employee();
        thisEmployee.setId(1L);
        thisEmployee.setName("Miguel");

        Expense exp1 = new Expense(
                receiptByte, //Receipt
                food,       // Category
                "USD",      // Local Currency
                LocalDateTime.of(2022, Month.MARCH, 30, 10, 30), //Date of purchase
                13L,        // Amount
                true,       // CC from company
                currentStatus, //Status of the expense
                "American Lunch", // name of the item
                "Some description here", //Description
                thisEmployee // Employee
        );

        Expense exp2 = new Expense(
                receiptByte,
                travel,     // Category
                "USD",      // Local Currency
                LocalDateTime.of(2022, Month.MARCH, 31, 11, 32), //Date of purchase
                24L,        // Amount
                true,       // CC from company
                currentStatus, //Status of the expense
                "American Lunch", // name of the item
                "Some description here", //Description
                thisEmployee // Employee
        );
        expenseRepository.save(exp1);
        expenseRepository.save(exp2);


        //When (Testing method)

        // List<Expense> expenseBySubmissionDate = expenseRepository.getExpenseBySubmissionDate(LocalDate.of(2022,4,4));

        List<Expense> all = expenseRepository.findAll();
        List<Expense> withSubmissionDate = new ArrayList<Expense>();

        LocalDate thisDate = LocalDate.of(2022, 4, 5);

        for (Expense expense : all) {
            if (expense.getDateOfSubmission().isEqual(thisDate)) {
                withSubmissionDate.add(expense);
            }
        }
        // Then
        assertThat(withSubmissionDate.size()).isEqualTo(2);
    }

    @Test
    void getExpenseByPurchaseDate() {
        //Given
        Byte[] receiptByte = {123, 124, 12, 42};
        ExpenseCategory food = null;
        ExpenseCategory travel = null;
        ReceiptStatuses currentStatus = ReceiptStatuses.INCOMPLETE;
        Employee thisEmployee = new Employee();
        thisEmployee.setId(1L);
        thisEmployee.setName("Miguel");

        Expense exp1 = new Expense(
                receiptByte, //Receipt
                food,       // Category
                "USD",      // Local Currency
                LocalDateTime.of(2022, Month.MARCH, 30, 10, 30), //Date of purchase
                13L,        // Amount
                true,       // CC from company
                currentStatus, //Status of the expense
                "American Lunch", // name of the item
                "Some description here", //Description
                thisEmployee // Employee
        );

        Expense exp2 = new Expense(
                receiptByte,
                travel,     // Category
                "USD",      // Local Currency
                LocalDateTime.of(2022, Month.MARCH, 31, 11, 32), //Date of purchase
                24L,        // Amount
                true,       // CC from company
                currentStatus, //Status of the expense
                "American Lunch", // name of the item
                "Some description here", //Description
                thisEmployee // Employee
        );
        Expense exp3 = new Expense(
                receiptByte, //Receipt
                food,       // Category
                "USD",      // Local Currency
                LocalDateTime.of(2022, Month.MARCH, 30, 14, 30), //Date of purchase
                23L,        // Amount
                true,       // CC from company
                currentStatus, //Status of the expense
                "American Lunch", // name of the item
                "Some description here", //Description
                thisEmployee // Employee
        );
        expenseRepository.save(exp1);
        expenseRepository.save(exp2);
        expenseRepository.save(exp3);


        //When (Testing method)

        // List<Expense> expenseBySubmissionDate = expenseRepository.getExpenseBySubmissionDate(LocalDate.of(2022,4,4));

        List<Expense> all = expenseRepository.findAll();
        List<Expense> purchaseDateList = new ArrayList<Expense>();

        LocalDate thisDate = LocalDate.of(2022, 3, 30);

        for (Expense expense : all) {
            if (expense.getDateOfPurchase().toLocalDate().isEqual(thisDate)) {
                purchaseDateList.add(expense);
                System.out.println(expense.getDateOfPurchase());
            }
        }
        // Then
        assertThat(purchaseDateList.size()).isEqualTo(2);

    }

    @Test
    void getCurrentMonthExpensesBySubmissionDate() {

        //Given
        Byte[] receiptByte = {123, 124, 12, 42};
        ExpenseCategory food = null;
        ExpenseCategory travel = null; //is making trouble with this ExpenseCategory Object
        ReceiptStatuses currentStatus = ReceiptStatuses.INCOMPLETE;
        Employee thisEmployee = new Employee();
        thisEmployee.setId(1L);
        thisEmployee.setName("Miguel");

        Expense exp1 = new Expense(
                receiptByte, //Receipt
                food,       // Category
                "USD",      // Local Currency
                LocalDateTime.of(2022, Month.MARCH, 30, 10, 30), //Date of purchase
                13L,        // Amount
                true,       // CC from company
                currentStatus, //Status of the expense
                "American Lunch", // name of the item
                "Some description here", //Description
                thisEmployee // Employee
        );

        Expense exp2 = new Expense(
                receiptByte,
                travel,     // Category
                "USD",      // Local Currency
                LocalDateTime.of(2022, Month.MARCH, 31, 11, 32), //Date of purchase
                24L,        // Amount
                true,       // CC from company
                currentStatus, //Status of the expense
                "American Lunch", // name of the item
                "Some description here", //Description
                thisEmployee // Employee
        );
        expenseRepository.save(exp1);
        expenseRepository.save(exp2);

        LocalDate startDate = LocalDate.of(2022, 4, 1);
        LocalDate endDate = LocalDate.of(2022, 4, 30);


        //when
        List<Expense> all = expenseRepository.findAll();
        List<Expense> withSubmissionDate = new ArrayList<Expense>();

        for (Expense expense : all) {
            if (expense.getDateOfSubmission().isBefore(endDate) &&
                    expense.getDateOfSubmission().isAfter(startDate)) {
                withSubmissionDate.add(expense);
            }
        }

        assertThat(withSubmissionDate.size()).isEqualTo(2);

    }

    @Test
    void getTotalAmountByCurrentMonthSubmissionDate() {

        //Given
        Byte[] receiptByte = {123, 124, 12, 42};
        ExpenseCategory food = null;
        ExpenseCategory travel = null; //is making trouble with this ExpenseCategory Object
        ReceiptStatuses currentStatus = ReceiptStatuses.INCOMPLETE;
        Employee thisEmployee = new Employee();
        thisEmployee.setId(1L);
        thisEmployee.setName("Miguel");

        Expense exp1 = new Expense(
                receiptByte, //Receipt
                food,       // Category
                "USD",      // Local Currency
                LocalDateTime.of(2022, Month.MARCH, 30, 10, 30), //Date of purchase
                25L,        // Amount
                true,       // CC from company
                currentStatus, //Status of the expense
                "American Lunch", // name of the item
                "Some description here", //Description
                thisEmployee // Employee
        );

        Expense exp2 = new Expense(
                receiptByte,
                travel,     // Category
                "USD",      // Local Currency
                LocalDateTime.of(2022, Month.MARCH, 31, 11, 32), //Date of purchase
                50L,        // Amount
                true,       // CC from company
                currentStatus, //Status of the expense
                "American Lunch", // name of the item
                "Some description here", //Description
                thisEmployee // Employee
        );
        expenseRepository.save(exp1);
        expenseRepository.save(exp2);

        LocalDate startDate = LocalDate.of(2022, 4, 1);
        LocalDate endDate = LocalDate.of(2022, 4, 30);

        //when
        List<Expense> all = expenseRepository.findAll();
        long totalAmount = 0;

        for (Expense expense : all) {
            if (expense.getDateOfSubmission().isBefore(endDate) &&
                    expense.getDateOfSubmission().isAfter(startDate)) {
                totalAmount += expense.getAmount();
            }
        }

        Assertions.assertEquals(totalAmount, 75);

    }


    @Test
    void getDepartmentBudgetAndCheckExpense() {

    }


}