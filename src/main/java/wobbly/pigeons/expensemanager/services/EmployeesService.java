package wobbly.pigeons.expensemanager.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wobbly.pigeons.expensemanager.models.Employees;
import wobbly.pigeons.expensemanager.repositories.EmployeesRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeesService {

    EmployeesRepository employeesRepository;

    public List<Employees> getEmployeesList() {
        return employeesRepository.findAll();
    }

    public Employees addEmployee(Employees newEmployee) {
        return employeesRepository.save(newEmployee);
    }

    public Employees getEmployee(Long id) {

        return employeesRepository.getById(id);
    }

    public Employees updateEmployee(Employees updatedEmployee, Long id) {

        Employees oldDataEmployee = employeesRepository.getById(id);

        oldDataEmployee.setName(updatedEmployee.getName());
        oldDataEmployee.setEmail(updatedEmployee.getEmail());
        oldDataEmployee.setManagers(updatedEmployee.getManagers());
        oldDataEmployee.setEmployeeRole((updatedEmployee.getEmployeeRole()));
        oldDataEmployee.setDob(updatedEmployee.getDob());
        oldDataEmployee.setPassword(updatedEmployee.getPassword());
        oldDataEmployee.setExpenses(updatedEmployee.getExpenses());

        return employeesRepository.save(oldDataEmployee);
    }

    public void deleteEmployee(Long id) {
        employeesRepository.deleteById(id);
    }
}
