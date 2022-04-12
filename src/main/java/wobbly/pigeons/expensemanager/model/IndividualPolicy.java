package wobbly.pigeons.expensemanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import wobbly.pigeons.expensemanager.repository.EmployeeRepository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class IndividualPolicy extends DepartmentPolicy {

    EmployeeRepository employeeRepository;

    public Long getIndividualBudget(String department){

        List<Employee> all = employeeRepository.findAll();
        List<Employee> allEmployeesOfMyDepartment = new ArrayList<Employee>();

        for (Employee employee : all){
            if(employee.getDepartment().toString().equalsIgnoreCase(department)){
                allEmployeesOfMyDepartment.add(employee);
            }
        }

        long departmentPolicyBudget = getDepartmentPolicyBudget();

        return Long.valueOf(Math.round(departmentPolicyBudget / allEmployeesOfMyDepartment.size()));
    }

}
