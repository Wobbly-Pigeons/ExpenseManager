package wobbly.pigeons.expensemanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wobbly.pigeons.expensemanager.model.Employee;
import wobbly.pigeons.expensemanager.repository.EmployeeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    EmployeeRepository employeeRepository;

    public List<Employee> getEmployeesList() {
        return employeeRepository.findAll();
    }

    public Employee addEmployee(Employee newEmployee) {
        return employeeRepository.save(newEmployee);
    }

    public Employee getEmployee(Long id) {

        return employeeRepository.getById(id);
    }

    public Employee updateEmployee(Employee updatedEmployee, Long id) {

        Employee oldDataEmployee = employeeRepository.getById(id);

        oldDataEmployee.setName(updatedEmployee.getName());
        oldDataEmployee.setEmail(updatedEmployee.getEmail());
        oldDataEmployee.setManager(updatedEmployee.getManager());
        oldDataEmployee.setEmployeeRole((updatedEmployee.getEmployeeRole()));
        oldDataEmployee.setDob(updatedEmployee.getDob());
        oldDataEmployee.setPassword(updatedEmployee.getPassword());
        oldDataEmployee.setExpenses(updatedEmployee.getExpenses());

        return employeeRepository.save(oldDataEmployee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
