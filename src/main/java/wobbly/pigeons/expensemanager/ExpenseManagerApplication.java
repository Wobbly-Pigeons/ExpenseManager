package wobbly.pigeons.expensemanager;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import wobbly.pigeons.expensemanager.model.*;
import wobbly.pigeons.expensemanager.repository.*;
import wobbly.pigeons.expensemanager.service.EmployeeService;
import wobbly.pigeons.expensemanager.service.ManagerService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class ExpenseManagerApplication {

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  public static void main(String[] args) {
    SpringApplication.run(ExpenseManagerApplication.class, args);
  }

  @Bean
  public CommandLineRunner loadData(
      RoleRepository roleRepository,
      EmployeeRepository employeeRepository,
      ManagerRepository managerRepository,
      BCryptPasswordEncoder bCryptPasswordEncoder,
      ManagerService managerService,
      EmployeeService employeeService,
      ExpenseRepository expenseRepository,
      DepartmentRepository departmentRepository) {
    return (args) -> {
      roleRepository.save(new Role("ROLE_EMPLOYEE"));
      roleRepository.save(new Role("ROLE_MANAGER"));
      roleRepository.save(new Role("ROLE_ADMIN"));
      roleRepository.save(new Role("ROLE_SUPER_ADMIN"));

      departmentRepository.save(new Department("IT"));
      departmentRepository.save(new Department("RH"));
      departmentRepository.save(new Department("Research"));
      departmentRepository.save(new Department("Law"));

      Employee e1 =
          new Employee(
              "henry@gmail.com",
              bCryptPasswordEncoder.encode("hello"),
              "Henry Gams",
              LocalDate.of(1957, 2, 3));

      Employee e2 =
          new Employee(
              "hank@gmail.com",
              bCryptPasswordEncoder.encode("howdy"),
              "Hank Hill",
              LocalDate.of(1999, 7, 23));

      Employee e3 =
          new Employee(
              "teresa@gmail.com",
              bCryptPasswordEncoder.encode("holler"),
              "Teresa Tracy",
              LocalDate.of(1985, 6, 8));

      List.of(e1, e2, e3)
          .forEach(
              employee -> {
                employee.getRoles().add(roleRepository.getById(1L));
                employee.setDepartment(departmentRepository.getById(1L));
                employee.setPolicy(new IndividualPolicy());
              });
      employeeRepository.saveAll(List.of(e1, e2, e3));
      Manager m1 =
          new Manager(
              "mest@gmail.com",
              bCryptPasswordEncoder.encode("ball"),
              "bella",
              LocalDate.of(1957, 2, 3));
      Manager m2 =
          new Manager(
              "mest2@gmail.com",
              bCryptPasswordEncoder.encode("balloon"),
              "bellara",
              LocalDate.of(1999, 7, 23));
      Manager m3 =
          new Manager(
              "ILoveManaging@gmail.com",
              bCryptPasswordEncoder.encode("happypuphappylife"),
              "Spock",
              LocalDate.of(1985, 6, 8));
      List.of(m1, m2, m3)
          .forEach(
              employee -> {
                employee.getRoles().add(roleRepository.getById(2L));
                employee.setDepartment(departmentRepository.getById(1l));
                employee.setPolicy(new IndividualPolicy());
              });
      managerService.addEmployee(e1, m3);
      m3.getRoles().add(roleRepository.getById(3L));

      Expense expense = new Expense(83.0, ReceiptStatuses.SUBMITTED);
      expense.setDateOfPurchase(LocalDate.of(2022, 3, 18));
      expense.setDateModified(LocalDateTime.of(2022, 3, 18, 13, 40));

      Expense expense2 = new Expense(34.3, ReceiptStatuses.INCOMPLETE);
      expense.setDateOfPurchase(LocalDate.of(2022, 4, 13));
      expense.setDateModified(LocalDateTime.of(2022, 4, 13, 10, 33));

      Expense expense3 = new Expense(56.5, ReceiptStatuses.SUBMITTED);
      expense.setDateOfPurchase(LocalDate.of(2022, 4, 8));
      expense.setDateModified(LocalDateTime.of(2022, 4, 8, 11, 16));

      Expense expense4 = new Expense(91.6, ReceiptStatuses.INCOMPLETE);
      expense.setDateOfPurchase(LocalDate.of(2022, 3, 29));
      expense.setDateModified(LocalDateTime.of(2022, 3, 29, 12, 11));

      Expense expense5 = new Expense(51.7, ReceiptStatuses.SUBMITTED);
      expense.setDateOfPurchase(LocalDate.of(2022, 4, 7));
      expense.setDateModified(LocalDateTime.of(2022, 4, 7, 9, 57));

      Expense expense6 = new Expense(73.8, ReceiptStatuses.INCOMPLETE);
      expense.setDateOfPurchase(LocalDate.of(2022, 4, 6));
      expense.setDateModified(LocalDateTime.of(2022, 4, 6, 14, 27));

      //      List.of(expense, expense2, expense3, expense4, expense5, expense6).forEach(expense1 ->
      // employeeService.addExpenseToUser(e1, expense));
      employeeService.addExpenseToUser(e1, expense);
      employeeService.addExpenseToUser(e1, expense2);
      employeeService.addExpenseToUser(e1, expense3);
      employeeService.addExpenseToUser(e1, expense4);
      employeeService.addExpenseToUser(e1, expense5);
      employeeService.addExpenseToUser(e1, expense6);
      managerRepository.saveAll(List.of(m1, m2, m3));
      employeeRepository.saveAll(List.of(e1, e2, e3));
      expenseRepository.saveAll(List.of(expense, expense2, expense3, expense4, expense5, expense6));
    };
  }
}
