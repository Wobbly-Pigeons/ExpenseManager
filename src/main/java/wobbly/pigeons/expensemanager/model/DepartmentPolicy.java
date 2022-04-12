package wobbly.pigeons.expensemanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wobbly.pigeons.expensemanager.repository.DepartmentRepository;
import wobbly.pigeons.expensemanager.service.DepartmentService;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class DepartmentPolicy extends Policy{

    DepartmentRepository departmentRepository;

    public Long getDepartmentPolicyBudget(){

        List<Department> all = departmentRepository.findAll();
        int numberOfDepartments = all.size();

       return Long.valueOf(Math.round( budgetMonthly / numberOfDepartments));


    }
}