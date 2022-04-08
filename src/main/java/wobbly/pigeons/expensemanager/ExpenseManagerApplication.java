package wobbly.pigeons.expensemanager;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import wobbly.pigeons.expensemanager.model.Employee;
import wobbly.pigeons.expensemanager.model.Expense;
import wobbly.pigeons.expensemanager.model.Manager;
import wobbly.pigeons.expensemanager.model.Role;
import wobbly.pigeons.expensemanager.repository.EmployeeRepository;
import wobbly.pigeons.expensemanager.repository.ExpenseRepository;
import wobbly.pigeons.expensemanager.repository.ManagerRepository;
import wobbly.pigeons.expensemanager.repository.RoleRepository;

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
                                    ExpenseRepository expenseRepository) {
    return (args) -> {
      roleRepository.save(new Role("ROLE_EMPLOYEE"));
      roleRepository.save(new Role("ROLE_MANAGER"));
      roleRepository.save(new Role("ROLE_ADMIN"));
      roleRepository.save(new Role("ROLE_SUPER_ADMIN"));
      Employee e1 = new Employee("test@gmail.com",
              "hello",
              "henry",
              LocalDate.of(1957, 2, 3));
        Employee e2 = new Employee("test2@gmail.com",
                "howdy",
                "hank",
                LocalDate.of(1999, 7, 23));
        Employee e3 = new Employee("test3@gmail.com",
                "holler",
                "hannibal",
                LocalDate.of(1985, 6, 8));
        List.of(e1, e2, e3).forEach(employee -> employee.getRoles().add(roleRepository.getById(1L)));
        employeeRepository.saveAll(List.of(e1, e2, e3));
        Manager m1 = new Manager("mest@gmail.com",
                "ball",
                "bella",
                LocalDate.of(1957, 2, 3));
        Manager m2 = new Manager("mest2@gmail.com",
                "balloon",
                "bellara",
                LocalDate.of(1999, 7, 23));
        Manager m3 = new Manager("mest3@gmail.com",
                "ballista",
                "bellaga",
                LocalDate.of(1985, 6, 8));
        List.of(e1, e2, e3).forEach(employee -> employee.getRoles().add(roleRepository.getById(2L)));
        m3.getRoles().add(roleRepository.getById(3L));
        managerRepository.saveAll(List.of(m1, m2, m3));
      expenseRepository.save(new Expense(83, employeeRepository.getById(1l)));
    };
        }

}
