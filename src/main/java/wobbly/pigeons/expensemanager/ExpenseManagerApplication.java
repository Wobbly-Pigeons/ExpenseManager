package wobbly.pigeons.expensemanager;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@SpringBootApplication
@RequiredArgsConstructor
public class ExpenseManagerApplication {


    private final EmployeeService employeeService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(ExpenseManagerApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(EmployeeService employeeService) {
        return (args) -> {
            Employee e1 = new Employee();
        };
    }

}
