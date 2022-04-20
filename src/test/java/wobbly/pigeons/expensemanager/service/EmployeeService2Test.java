package wobbly.pigeons.expensemanager.service;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import wobbly.pigeons.expensemanager.model.DTO.UserDTO;
import wobbly.pigeons.expensemanager.model.Employee;
import wobbly.pigeons.expensemanager.repository.EmployeeRepository;
import wobbly.pigeons.expensemanager.repository.ExpenseRepository;
import wobbly.pigeons.expensemanager.repository.ManagerRepository;
import wobbly.pigeons.expensemanager.repository.RoleRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployeeService2Test {

    @Mock
    private EmployeeRepository employeeRepository;

    private EmployeeService employeeService;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private ExpenseRepository expenseRepository;
    @Mock
    private ManagerRepository managerRepository;

    @BeforeEach
    void setup() throws Exception {
        employeeService = new EmployeeService(employeeRepository,
                bCryptPasswordEncoder,
                roleRepository,
                expenseRepository,
                managerRepository);
    }

    @Test
    void getEmployeesList() {
        // When
        employeeService.getEmployeesList();
        // Then
        Mockito.verify(employeeRepository).findAll();
    }

    @Test
    void addEmployee() {
        // Given
        Employee testingEmployee = new Employee(
                "test@mail.com",
                "1234",
                "bob",
                LocalDate.of(1987,5,5));

        // When
        employeeService.addEmployee(testingEmployee);

        // Then
        ArgumentCaptor<Employee> employeeArgumentCaptor =
                ArgumentCaptor.forClass(Employee.class);

        Mockito.verify(employeeRepository).save(employeeArgumentCaptor.capture());

        Employee captureEmployee = employeeArgumentCaptor.getValue();

        assertThat(captureEmployee).isEqualTo(testingEmployee);
    }

    @Test
    void getEmployee() {
        // Given
        Employee testingEmployee = new Employee(
                "test@mail.com",
                "1234",
                "bob",
                LocalDate.of(1987,5,5));

        testingEmployee.setId(23L);

        given(employeeRepository.getById(23L)).willReturn(testingEmployee);

        // When
        Employee employee = employeeService.getEmployee(23L);

        // Then
        assertThat(employee).isEqualTo(testingEmployee);
    }

    @Test
    void updateEmployee() {
        // Given
        Employee testingEmployee = new Employee(
                "test@mail.com",
                "1234",
                "bob",
                LocalDate.of(1987,5,5));
        testingEmployee.setId(23L);

        Employee updateThisEmployee = new Employee();
        updateThisEmployee.setName("Bob Marley");

        given(employeeRepository.getById(23L)).willReturn(testingEmployee);

        // When
        employeeService.updateEmployee(updateThisEmployee,23L);

        // Then
        Assertions.assertThat(testingEmployee.getName().equals("Bob Marley"));


    }

    @Test
    void deleteEmployee() {

        // Given
        Employee testingEmployee = new Employee(
                "test@mail.com",
                "1234",
                "bob",
                LocalDate.of(1987,5,5));

        testingEmployee.setId(23L);

        ArgumentCaptor<Employee> employeeArgumentCaptor =
                ArgumentCaptor.forClass(Employee.class);


        // When
//        Mockito.when(employeeRepository.findById(23L)).thenReturn(Optional.of(testingEmployee)).thenReturn(null);
        employeeService.deleteEmployee(23L);
        // Then

        Mockito.verify(employeeRepository).delete(employeeArgumentCaptor.capture());

        Employee captureEmployee = employeeArgumentCaptor.getValue();

        assertThat(captureEmployee).isNull();




    }
}