package wobbly.pigeons.expensemanager.Integration_Tests;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import wobbly.pigeons.expensemanager.controller.EmployeesController;
import wobbly.pigeons.expensemanager.model.Employee;
import wobbly.pigeons.expensemanager.repository.*;
import wobbly.pigeons.expensemanager.service.EmployeeService;
import wobbly.pigeons.expensemanager.service.ManagerService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockUser;


@WebFluxTest(controllers = EmployeesController.class)
@Import(EmployeeService.class)
public class EmployeeControllersTest {

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @MockBean
    private ExpenseRepository expenseRepository;

    @MockBean
    private ManagerRepository managerRepository;

    @MockBean
    private ManagerService managerService;

    @MockBean
    private DepartmentRepository departmentRepository;

    @Autowired
    private WebTestClient webTestClient;

    //Fields
    Employee employee1 = new Employee("Aga@mail.com", "456abc", "Aga",
            LocalDate.of(1989, 2, 1));
    Employee employee2 = new Employee("Andrew@mail.com", "098abc", "Andrew",
            LocalDate.of(1989, 2, 2));
    Employee employee3 = new Employee("Miguel@mail.com", "123abc", "Miguel",
            LocalDate.of(1989, 2, 3));
    Employee employee4 = new Employee("Angela@mail.com", "789abc", "Angela",
            LocalDate.of(1989, 2, 4));


    @Test
    void expectUnauthorizedStatus(){
        //Given
        List<Employee> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(employee1);
        listOfEmployees.add(employee2);
        listOfEmployees.add(employee3);
        listOfEmployees.add(employee4);

        Mockito.when(employeeRepository.findAll()).thenReturn(listOfEmployees);

        //When
        webTestClient.get()
                .uri("/api/v1/employees")
                .exchange()
                .expectStatus().isUnauthorized()
                .expectBody().consumeWith(System.out::println);

    }

    @Test
    @WithMockUser(username = "user",password = "1234")  // users cannot delete other users. Forbidden is expected
    void expectForbiddenStatus(){

        webTestClient.delete()
                .uri("/api/v1/employees/{id}",4L)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .consumeWith(System.out::println);

    }

    @Test
    @WithMockUser(username = "user",password = "1234")
    void getEmployeesList() {

        //Given
        List<Employee> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(employee1);
        listOfEmployees.add(employee2);
        listOfEmployees.add(employee3);
        listOfEmployees.add(employee4);

        Mockito.when(employeeRepository.findAll()).thenReturn(listOfEmployees);

        // The user class have the policy dependency in the constructor...remove to run green

        //When
        webTestClient.get()
                .uri("/api/v1/employees")
                .exchange()
                .expectStatus().isOk()
                .expectBody().consumeWith(System.out::println)
                .jsonPath("$.size()").isEqualTo(4)
                .jsonPath("$[0].name").isEqualTo("Aga")
                .jsonPath("$[1].email").isEqualTo("Andrew@mail.com")
                .jsonPath("$[2].password").isEqualTo("123abc")
                .jsonPath("$[3].dob").isEqualTo(LocalDate.of(1989, 2, 4).toString());
    }

    // 403 forbidden status code...I need to create an Admin privileges in mockUs
    @Test  // or create our own user and use @WithCustomMockUser
    @WithMockUser(username = "user",password = "1234",roles = {"ADMIN"})
    void createNewEmployee() throws Exception {

        Mockito.when(employeeRepository.save(Mockito.any(Employee.class)))
                .thenAnswer(invocation -> invocation.getArguments()[0]);

        webTestClient.mutateWith(csrf())
                .post()
                .uri("/api/v1/employees/newEmployee")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(employee1)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Employee.class)
                .consumeWith(System.out::println)
                .value(employeeResponse -> {
                    assertThat(employeeResponse.getName()).isEqualTo("Aga");
                    assertThat(employeeResponse.getPassword()).isEqualTo("456abc");
                    assertThat(employeeResponse.getDob())
                            .isEqualTo(LocalDate.of(1989,2,1).toString());
                    assertThat(employeeResponse.getEmail()).isEqualTo("Aga@mail.com");

                });

    }

    @Test
    @WithMockUser(username = "user",password = "1234")
    void getEmployeeById(){

        //Repository Mock
        Mockito.when(employeeRepository.getById(2L)).thenReturn(employee2);

        webTestClient.get()
                .uri("/api/v1/employees/{id}",2L)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.name").isEqualTo("Andrew")
                .jsonPath("$.password").isEqualTo("098abc")
                .jsonPath("$.email").isEqualTo("Andrew@mail.com")
                .jsonPath("$.dob").isEqualTo(LocalDate.of(1989,2,2).toString());
    }

    @Test // 403 forbidden status code... It's possible that I need Admin privileges in mockUser
    @WithMockUser(username = "user",password = "1234")
    void updateEmployeeById(){

        // Update Employee with new data
        Employee newDataEmployee = new Employee();
        newDataEmployee.setEmail("MiguelSilva@mail.com");
        newDataEmployee.setPassword("ABC!@#");
        newDataEmployee.setDob(LocalDate.of(1987,2,3));

        //Repository Mock
        Mockito.when(employeeRepository.getById(3L)).thenReturn(employee3);
        Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenAnswer(invocation ->
                invocation.getArguments()[0]);

        webTestClient.mutateWith(csrf())
                .put()
                .uri("/api/v1/employees/{id},",3L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newDataEmployee)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Employee.class)
                .consumeWith(System.out::println)
                .value(employeeResponse ->{
                    assertThat(employeeResponse.getName()).isEqualTo("Miguel");
                    assertThat(employeeResponse.getPassword()).isEqualTo("ABC!@#");
                    assertThat(employeeResponse.getEmail()).isEqualTo("MiguelSilva@mail.com");
                    assertThat(employeeResponse.getDob()).isEqualTo(
                            LocalDate.of(1987,2,3).toString());
                });
    }

    @Test // are we dealing with ResponseEntity or Error handling ??        //403 forbidden
    @WithMockUser(username = "user",password = "1234")
    void deleteEmployeeByID(){

        List<Employee> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(employee1);
        listOfEmployees.add(employee2);
        listOfEmployees.add(employee3);
        listOfEmployees.add(employee4);

        // Mockito.when(employeeRepository.deleteById(4L)).thenAnswer(listOfEmployees.remove(employee4));

        for(Employee employee : listOfEmployees){
            System.out.println(employee.getName());
        }

        webTestClient.delete()
                .uri("/api/v1/employees/{id}",4L)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .consumeWith(System.out::println);

        for(Employee employee : listOfEmployees){
            System.out.println(employee.getName());
        }

    }

}