package wobbly.pigeons.expensemanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import wobbly.pigeons.expensemanager.model.DTO.UserDTO;
import wobbly.pigeons.expensemanager.model.Employee;
import wobbly.pigeons.expensemanager.repository.EmployeeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<Employee> getEmployeesList() {
        return employeeRepository.findAll();
    }

    public Employee newEmployee(UserDTO userDTO) {
        Employee newEmployee = new Employee(
                userDTO.getEmail(),
                bCryptPasswordEncoder.encode(userDTO.getPassword()),
                userDTO.getFirstName() + " " + userDTO.getLastName(),
                userDTO.getDob()
        );
        return employeeRepository.save(newEmployee);
    }

    public Employee addEmployee(Employee newEmployee) {
        return employeeRepository.save(newEmployee);
    }

    public Employee getEmployee(Long id) {

        return employeeRepository.getById(id);
    }

    public Employee updateEmployee(Employee updatedEmployee, Long id) {

        Employee oldDataEmployee = employeeRepository.getById(id);

        //oldDataEmployee.setName(updatedEmployee.getName());
        oldDataEmployee.setEmail(updatedEmployee.getEmail());
        oldDataEmployee.setManager(updatedEmployee.getManager());
        oldDataEmployee.setDob(updatedEmployee.getDob());
        oldDataEmployee.setPassword(updatedEmployee.getPassword());
        oldDataEmployee.setExpenses(updatedEmployee.getExpenses());

        return employeeRepository.save(oldDataEmployee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public Employee findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }
}
