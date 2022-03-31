package wobbly.pigeons.expensemanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wobbly.pigeons.expensemanager.model.Department;
import wobbly.pigeons.expensemanager.service.DepartmentService;

import java.util.List;

@Controller
@RequestMapping(value = "/api/v1/Departments")
public class DepartmentController {

    private DepartmentService departmentService;

    @GetMapping
    public List<Department> getDepartmentsList() {
        return departmentService.getDepartmentsList();
    }

    @PostMapping
    public Department addDepartment(@RequestBody Department department) {
        return departmentService.addDepartment(department);
    }

    @GetMapping("/{id}")
    public Department getDepartment(@PathVariable Long id) {
        return departmentService.getDepartment(id);
    }

    @PutMapping("/{id}")
    public Department updateDepartment(@PathVariable Long id, @RequestBody Department department) {
        return departmentService.updateDepartment(id, department);
    }

    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
    }
}
