package wobbly.pigeons.expensemanager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import wobbly.pigeons.expensemanager.model.Expense;
import wobbly.pigeons.expensemanager.model.ReceiptStatuses;
import wobbly.pigeons.expensemanager.model.Role;
import wobbly.pigeons.expensemanager.repository.RoleRepository;
import wobbly.pigeons.expensemanager.service.EmployeeService;
import wobbly.pigeons.expensemanager.service.ManagerService;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TestNewFeatures {


    @Autowired
    private RoleRepository roleRepository;
    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @MockBean
    private ManagerService managerService;
    @MockBean
    private EmployeeService employeeService;

    @Test
    void roundLongNumber(){

        Role newRole = new Role();

        roleRepository.save(newRole);

        assertThat(newRole.getId()).isEqualTo(1L);
    }



}
