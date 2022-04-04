package wobbly.pigeons.expensemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<<<<<<< HEAD
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
=======
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
>>>>>>> 7108d7b6185a8e982d7e386d8431ebf9675df6b9

@SpringBootApplication
public class ExpenseManagerApplication{

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(ExpenseManagerApplication.class, args);
    }

}
