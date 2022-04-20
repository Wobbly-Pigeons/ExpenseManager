package wobbly.pigeons.expensemanager.Unit_Tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import wobbly.pigeons.expensemanager.model.Employee;
import wobbly.pigeons.expensemanager.repository.EmployeeRepository;
import wobbly.pigeons.expensemanager.service.EmployeeService;
import wobbly.pigeons.expensemanager.service.ManagerService;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @MockBean
    private ManagerService managerService;

    @MockBean
    private EmployeeService employeeService;

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
    }

    @Test
    void testingSuccessEmployeeFindByEmail() throws Exception {

        // Given
        String email = "testing@mail.com";

        Employee testingEmployee = new Employee(email,
                "123",
                "Bob",
                LocalDate.of(1988,4,3));

        employeeRepository.save(testingEmployee);

        // when
        Employee expectedEmployee = employeeRepository.findByEmail(email);

        // Then
        assertThat(expectedEmployee).isNotNull();

    }

    @Test
    void testingUnsuccessfulEmployeeFindByEmail() throws Exception {

        // Given
        String email = "testing@mail.com";

        // when
        Employee expectedEmployee = employeeRepository.findByEmail(email);

        // Then
        assertThat(expectedEmployee).isNull();

    }

}
