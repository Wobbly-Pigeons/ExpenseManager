package wobbly.pigeons.expensemanager;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import wobbly.pigeons.expensemanager.model.Role;
import wobbly.pigeons.expensemanager.repository.RoleRepository;

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
  public CommandLineRunner loadData(RoleRepository roleRepository) {
    return (args) -> {
      roleRepository.save(new Role("ROLE_EMPLOYEE"));
      roleRepository.save(new Role("ROLE_MANAGER"));
      roleRepository.save(new Role("ROLE_ADMIN"));
      roleRepository.save(new Role("ROLE_SUPER_ADMIN"));
    };
        }

}
