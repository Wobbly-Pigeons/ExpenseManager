package wobbly.pigeons.expensemanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import wobbly.pigeons.expensemanager.model.DTO.UserDTO;
import wobbly.pigeons.expensemanager.model.Employee;
import wobbly.pigeons.expensemanager.model.Expense;
import wobbly.pigeons.expensemanager.model.Role;
import wobbly.pigeons.expensemanager.model.User;
import wobbly.pigeons.expensemanager.repository.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeesRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;
    private final ExpenseRepository expenseRepository;
    private final DepartmentRepository departmentRepository;
    private final ManagerRepository managerRepository;

    public List<Employee> getEmployeesList() {
        return employeesRepository.findAll();
    }

    public Employee newEmployee(UserDTO userDTO) {
        Role initialRole = roleRepository.findByRole("ROLE_EMPLOYEE");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Employee newEmployee = new Employee(
                userDTO.getEmail(),
                bCryptPasswordEncoder.encode(userDTO.getPassword()),
                userDTO.getFirstName() + " " + userDTO.getLastName(),
                LocalDate.parse(userDTO.getDob(), formatter)
        );
    newEmployee.setDepartment(departmentRepository.findByName(userDTO.getDepartment()).orElseThrow());
        newEmployee.getRoles().add(initialRole);
        newEmployee.setManager(managerRepository.findByEmail("ILoveManaging@gmail.com"));
        return employeesRepository.save(newEmployee);
    }

    public Employee addEmployee(Employee newEmployee) {
        return employeesRepository.save(newEmployee);
    }

    public Employee getEmployee(Long id) {

        return employeesRepository.getById(id);
    }

    public Employee updateEmployee(Employee updatedEmployee, Long id) {

        Employee oldDataEmployee = employeesRepository.getById(id);

        oldDataEmployee.setName(updatedEmployee.getName());
        oldDataEmployee.setEmail(updatedEmployee.getEmail());
        oldDataEmployee.setManager(updatedEmployee.getManager());
        oldDataEmployee.setDob(updatedEmployee.getDob());
        oldDataEmployee.setPassword(updatedEmployee.getPassword());
        oldDataEmployee.setExpenses(updatedEmployee.getExpenses());

        return employeesRepository.save(oldDataEmployee);
    }

    public void deleteEmployee(Long id) {
        employeesRepository.deleteById(id);
    }

    public Employee findByEmail(String email) {
        return employeesRepository.findByEmail(email);
    }


    public Page<Expense> findPaginatedExpensesByUser(int pageNo, int pageSize,
                                                     String sortField, String sortDir,
                                                     Employee currentUser) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return expenseRepository.findExpensesByUser(currentUser, pageable);

    }

    public void addExpenseToUser(Employee emp, Expense expense) {
        emp.getExpenses().add(expense);
        expense.setUser(emp);
    }


    public User findUserByPrincipal(Principal principal) {
        Employee employee = employeesRepository.findByEmail(principal.getName());
        if (employee == null) {
            employee = managerRepository.findByEmail(principal.getName());
        }
        return employee;
    }
    public int getUserViolations(Principal principal) {

        User userByPrincipal = findUserByPrincipal(principal);

        return userByPrincipal.getViolations();


    }
    public void addViolationToUser(User user) {

        int violations = user.getViolations();
        user.setViolations(violations + 1);

        Employee employee = employeesRepository.findByEmail(user.getEmail());

        if (employee == null) {
            employee = managerRepository.findByEmail(user.getEmail());
            managerRepository.flush();
        } else {
            employeesRepository.flush();

        }
    }
}
