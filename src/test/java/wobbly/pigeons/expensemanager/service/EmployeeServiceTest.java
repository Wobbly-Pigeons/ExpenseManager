package wobbly.pigeons.expensemanager.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import wobbly.pigeons.expensemanager.model.Employee;
import wobbly.pigeons.expensemanager.model.Expense;
import wobbly.pigeons.expensemanager.model.ExpenseCategory;
import wobbly.pigeons.expensemanager.model.ReceiptStatuses;
import wobbly.pigeons.expensemanager.repository.EmployeeRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EmployeeServiceTest {

    @Autowired
    private EmployeeRepository employeeRepository;


    @Test
    void getEmployeesList() {

        //Given
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();

        //When
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        List<Employee> all = employeeRepository.findAll();

        // Then
        assertThat(all.size()).isEqualTo(2);
    }

    @Test
    void addEmployee() {
        //Given
        LocalDate dob1 = LocalDate.of(1987, 2, 11);

        Employee otherEmployee = new Employee(
                "bob@gmail.com",
                "1234",
                "Bob",
                LocalDate.of(1987, 2, 11));

        Employee save = employeeRepository.save(otherEmployee);

        List<Employee> all = employeeRepository.findAll();

        for (Employee employee : all) {
            System.out.println(employee.getName() + " "+ employee.getId());
        }

        System.out.println( save.getId());

        assertThat(employeeRepository.getById(1L).getPassword()).isEqualTo("1234");

    }

    @Test
    void getEmployee() {

        //Given
        Employee newEmployee = new Employee();
        Employee newEmployee2 = new Employee();
        Employee newEmployee3 = new Employee();

        employeeRepository.save(newEmployee);
        employeeRepository.save(newEmployee2);
        employeeRepository.save(newEmployee3);

        // When
        Employee byId1 = employeeRepository.getById(1L);
        Employee byId2 = employeeRepository.getById(2L);
        Employee byId3 = employeeRepository.getById(3L);

        // Then
        assertThat(byId1.getId()).isEqualTo(1);
        assertThat(byId2.getId()).isEqualTo(2);
        assertThat(byId3.getId()).isEqualTo(3);


    }

    @Test
    void updateEmployee() {

        //Given
        Employee newEmployee = new Employee();

        Employee updateThisEmployee = new Employee();
        updateThisEmployee.setName("Miguel");

        Employee withThisEmployee = new Employee();
        withThisEmployee.setName("Silva");

        employeeRepository.save(newEmployee);
        employeeRepository.save(updateThisEmployee);
        employeeRepository.save(withThisEmployee);

        System.out.println(updateThisEmployee.getName() + " " + withThisEmployee.getName());


        //Testing method
        Employee oldDataEmployee = employeeRepository.getById(2L);

        oldDataEmployee.setName(withThisEmployee.getName());

        employeeRepository.save(oldDataEmployee);
        // until here

        assertThat(updateThisEmployee.getName()).isEqualTo("Silva");

        System.out.println(updateThisEmployee.getId() + " " + withThisEmployee.getId());

    }

    @Test
    void deleteEmployee() {

        // Given
        Employee newEmployee = new Employee();
        Employee newEmployee2 = new Employee();

        employeeRepository.save(newEmployee);
        employeeRepository.save(newEmployee2);


        List<Employee> all = employeeRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        employeeRepository.delete(newEmployee);

        List<Employee> all2 = employeeRepository.findAll();
        assertThat(all2.size()).isEqualTo(1);
    }

    @Test
    void getExpensesByEmployeeId() {

        //Given

        Byte[] receiptByte = {123, 124, 12, 42};
        ExpenseCategory food = null;
        ExpenseCategory travel = null; //is making trouble with this ExpenseCategory Object
        ReceiptStatuses currentStatus = ReceiptStatuses.INCOMPLETE;

        // 2 Employees
        Employee newEmployee = new Employee();
        Employee newEmployee2 = new Employee();

        // 3 Expenses: 2 for employee1 and 1 for employee2 saved in a List
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
                newEmployee // Employee
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
                newEmployee // Employee
        );

        Expense exp3 = new Expense(
                receiptByte,
                travel,     // Category
                "USD",      // Local Currency
                LocalDateTime.of(2022, Month.MARCH, 31, 11, 32), //Date of purchase
                24L,        // Amount
                true,       // CC from company
                currentStatus, //Status of the expense
                "American Lunch", // name of the item
                "Some description here", //Description
                newEmployee2 // Employee
        );


        Set<Expense> ListOfEmployee1 = new HashSet<Expense>();
        Set<Expense> ListOfEmployee2 = new HashSet<Expense>();

        ListOfEmployee1.add(exp1);
        ListOfEmployee1.add(exp2);
        ListOfEmployee2.add(exp3);


        newEmployee.setExpenses(ListOfEmployee1);
        newEmployee2.setExpenses(ListOfEmployee2);

        employeeRepository.save(newEmployee);
        employeeRepository.save(newEmployee2);

        //When
        Employee byId = employeeRepository.getById(1L);
        Collection<Expense> expenses1 = byId.getExpenses();

        Employee byId2 = employeeRepository.getById(2L);
        Collection<Expense> expenses2 = byId2.getExpenses();

        //Then
        assertThat(expenses1.size()).isEqualTo(2);
        assertThat(expenses2.size()).isEqualTo(1);

        for (Expense expense : expenses1) {
            assertThat(expense.getItemName()).isEqualTo("American Lunch");
            System.out.println(expense.getAmount());
        }


    }
}