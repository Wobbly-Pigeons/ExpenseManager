package wobbly.pigeons.expensemanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wobbly.pigeons.expensemanager.model.DTO.UserDTO;
import wobbly.pigeons.expensemanager.model.Employee;
import wobbly.pigeons.expensemanager.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/employees")
@RequiredArgsConstructor
public class EmployeesController {

    private final EmployeeService employeeService;

    @GetMapping
    public List<Employee> getEmployeesList() {
        return employeeService.getEmployeesList();
    }

    @PostMapping("/newEmployee")
    public String addEmployee(@ModelAttribute UserDTO userDTO) {
        employeeService.newEmployee(userDTO);
        return "/login";
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
