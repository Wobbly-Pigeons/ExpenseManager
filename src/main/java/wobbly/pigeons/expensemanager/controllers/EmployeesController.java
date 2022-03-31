package wobbly.pigeons.expensemanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wobbly.pigeons.expensemanager.models.Employees;
import wobbly.pigeons.expensemanager.services.EmployeesService;

import java.util.List;

@Controller
@RequestMapping(value = "/api/v1/Employees")
public class EmployeesController {

    private EmployeesService employeesService;

    @GetMapping
    public List<Employees> getEmployeesList() {
        return employeesService.getEmployeesList();
    }

    @PostMapping("/newEmployee")
    public Employees addEmployee(@RequestBody Employees employee) {
        return employeesService.addEmployee(employee);
    }

    @GetMapping("/{id}")
    public Employees getEmployee(@PathVariable Long id) {
        return employeesService.getEmployee(id);
    }

    @PutMapping("/{id}")
    public Employees updateEmployee(@RequestBody Employees updatedEmployee, @PathVariable Long id) {
        return employeesService.updateEmployee(updatedEmployee, id);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeesService.deleteEmployee(id);
    }
}
