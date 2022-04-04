package wobbly.pigeons.expensemanager.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import wobbly.pigeons.expensemanager.model.Employee;
import wobbly.pigeons.expensemanager.model.Expense;
import wobbly.pigeons.expensemanager.model.ExpenseCategory;
import wobbly.pigeons.expensemanager.model.ReceiptStatuses;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ExpenseRepositoryTest {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Test
    void getExpensesList(){

        //Given
        Byte[] receiptByte = {123,124,12,42};
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
                LocalDateTime.of(2022, Month.MARCH,30,10,30), //Date of purchase
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
                LocalDateTime.of(2022, Month.MARCH,31,11,32), //Date of purchase
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

        for (Expense expense : all){
            System.out.println(expense.getId());
            System.out.println(expense.getCategory());
            System.out.println(expense.getUser().getName());
        }

        // Then
        assertThat(all.size()).isEqualTo(2);

    }

    @Test
    void getExpenseByCategory() {
    }

    @Test
    void getExpenseBySubmissionDate() {
        //Given
        Byte[] receiptByte = {123,124,12,42};
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
                LocalDateTime.of(2022, Month.MARCH,30,10,30), //Date of purchase
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
                LocalDateTime.of(2022, Month.MARCH,31,11,32), //Date of purchase
                24L,        // Amount
                true,       // CC from company
                currentStatus, //Status of the expense
                "American Lunch", // name of the item
                "Some description here", //Description
                thisEmployee // Employee
        );
        expenseRepository.save(exp1);
        expenseRepository.save(exp2);


        //When

        List<Expense> expenseBySubmissionDate = expenseRepository.getExpenseBySubmissionDate(LocalDate.of(2022,4,4));

//        List<Expense> all = expenseRepository.findAll();
//        List<Expense> withSubmissionDate = null;
//        for(Expense expense : all){
//            if(expense.getDateOfSubmission().isEqual(LocalDate.of(2022,4,4))){
//                withSubmissionDate.add(expense);
//            }
//        }
        // Then
        assertThat(expenseBySubmissionDate.size()).isEqualTo(2);
    }

    @Test
    void getExpenseByPurchaseDate() {
    }

    @Test
    void getExpenseByEmployeeId() {

        //Given
        Byte[] receiptByte = {123,124,12,42};
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
                LocalDateTime.of(2022, Month.MARCH,30,10,30), //Date of purchase
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
                LocalDateTime.of(2022, Month.MARCH,31,11,32), //Date of purchase
                24L,        // Amount
                true,       // CC from company
                currentStatus, //Status of the expense
                "American Lunch", // name of the item
                "Some description here", //Description
                thisEmployee // Employee
        );
        expenseRepository.save(exp1);
        expenseRepository.save(exp2);

        //When
        List<Expense> expenseByEmployeeId = expenseRepository.getExpenseByEmployeeId(1L);
       // Expense byId = expenseRepository.findAllById();

        //Then
        assertThat(expenseByEmployeeId.size()).isEqualTo(2);
    }
}