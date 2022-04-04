package wobbly.pigeons.expensemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class ExpenseManagerApplication{

    public static void main(String[] args) {
        SpringApplication.run(ExpenseManagerApplication.class, args);
    }

}
