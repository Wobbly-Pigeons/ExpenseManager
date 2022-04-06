package wobbly.pigeons.expensemanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wobbly.pigeons.expensemanager.model.DTO.UserDTO;
import wobbly.pigeons.expensemanager.model.Employee;
import wobbly.pigeons.expensemanager.service.EmployeeService;

import java.util.List;

@Controller
@RequestMapping(value = "/api/v1/employees")
@RequiredArgsConstructor
public class EmployeesController {

    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getEmployeesList() {
        return employeeService.getEmployeesList();
    }

    @PostMapping("/newEmployee")
    public Employee addEmployee(@RequestBody UserDTO userDTO) {
        return employeeService.addEmployee(userDTO);
    }

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable Long id) {
        return employeeService.getEmployee(id);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@RequestBody Employee updatedEmployee, @PathVariable Long id) {
        return employeeService.updateEmployee(updatedEmployee, id);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }
}
