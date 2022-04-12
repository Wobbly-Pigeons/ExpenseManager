package wobbly.pigeons.expensemanager;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import wobbly.pigeons.expensemanager.model.*;
import wobbly.pigeons.expensemanager.repository.EmployeeRepository;
import wobbly.pigeons.expensemanager.repository.ExpenseRepository;
import wobbly.pigeons.expensemanager.repository.ManagerRepository;
import wobbly.pigeons.expensemanager.repository.RoleRepository;
import wobbly.pigeons.expensemanager.service.EmployeeService;
import wobbly.pigeons.expensemanager.service.ManagerService;

import java.time.LocalDate;
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
  public CommandLineRunner loadData(RoleRepository roleRepository,
                                    EmployeeRepository employeeRepository,
                                    ManagerRepository managerRepository,
                                    BCryptPasswordEncoder bCryptPasswordEncoder,
                                    ManagerService managerService,
                                    EmployeeService employeeService,
                                    ExpenseRepository expenseRepository) {
    return (args) -> {
      roleRepository.save(new Role("ROLE_EMPLOYEE"));
      roleRepository.save(new Role("ROLE_MANAGER"));
      roleRepository.save(new Role("ROLE_ADMIN"));
      roleRepository.save(new Role("ROLE_SUPER_ADMIN"));
      Employee e1 =
          new Employee(
              "test@gmail.com",
              bCryptPasswordEncoder.encode("hello"),
              "henry",
              LocalDate.of(1957, 2, 3));
      Employee e2 =
          new Employee(
              "test2@gmail.com",
              bCryptPasswordEncoder.encode("howdy"),
              "hank",
              LocalDate.of(1999, 7, 23));
      Employee e3 =
          new Employee(
              "test3@gmail.com",
              bCryptPasswordEncoder.encode("holler"),
              "hannibal",
              LocalDate.of(1985, 6, 8));
      List.of(e1, e2, e3).forEach(employee -> employee.getRoles().add(roleRepository.getById(1L)));
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
              "mest3@gmail.com",
              bCryptPasswordEncoder.encode("ballista"),
              "bellaga",
              LocalDate.of(1985, 6, 8));
      List.of(m1, m2, m3).forEach(employee -> employee.getRoles().add(roleRepository.getById(2L)));
      managerService.addEmployee(e1, m3);
      m3.getRoles().add(roleRepository.getById(3L));
      Expense expense = new Expense(83, ReceiptStatuses.SUBMITTEDANDPENDING);
      Expense expense2 = new Expense(34, ReceiptStatuses.INCOMPLETE);
      Expense expense3 = new Expense(56, ReceiptStatuses.SUBMITTEDANDPENDING);
      Expense expense4 = new Expense(91, ReceiptStatuses.INCOMPLETE);
      Expense expense5 = new Expense(51, ReceiptStatuses.SUBMITTEDANDPENDING);
      Expense expense6 = new Expense(73, ReceiptStatuses.INCOMPLETE);
//      List.of(expense, expense2, expense3, expense4, expense5, expense6).forEach(expense1 -> employeeService.addExpenseToUser(e1, expense));
      employeeService.addExpenseToUser(e1, expense);
      employeeService.addExpenseToUser(e1, expense2);
        employeeService.addExpenseToUser(e1, expense3);
        employeeService.addExpenseToUser(e1, expense4);
        employeeService.addExpenseToUser(e1, expense5);
        employeeService.addExpenseToUser(e1, expense6);
      managerRepository.saveAll(List.of(m1, m2, m3));
      employeeRepository.saveAll(List.of(e1, e2, e3));
//      expenseRepository.saveAll(List.of(expense, expense2, expense3, expense4, expense5, expense6));
    };
        }

}
