package wobbly.pigeons.expensemanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wobbly.pigeons.expensemanager.repository.DepartmentRepository;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentPolicy extends Policy{

    DepartmentRepository departmentRepository;

    Long departmentBudget = getDepartmentBudget();

    public Long getDepartmentPolicyBudget(){

        List<Department> all = departmentRepository.findAll();
        int numberOfDepartments = all.size();

       return Long.valueOf(Math.round( budgetMonthly / numberOfDepartments));


    }
}