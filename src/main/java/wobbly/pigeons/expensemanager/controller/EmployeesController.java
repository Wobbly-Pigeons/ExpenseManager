package wobbly.pigeons.expensemanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wobbly.pigeons.expensemanager.model.DTO.UserDTO;
import wobbly.pigeons.expensemanager.model.Employee;
import wobbly.pigeons.expensemanager.service.EmployeeService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class EmployeesController {

    private final EmployeeService employeeService;

    @PostMapping("/newEmployee")
    public String addEmployee(@ModelAttribute UserDTO userDTO) {
        employeeService.newEmployee(userDTO);
        return "/login";
    }
}
