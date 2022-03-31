package wobbly.pigeons.expensemanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wobbly.pigeons.expensemanager.models.Department;
import wobbly.pigeons.expensemanager.repositories.DepartmentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private DepartmentRepository departmentRepository;

    public List<Department> getDepartmentsList() {
        return departmentRepository.findAll();
    }

    public Department addDepartment(Department newDepartment) {
        return departmentRepository.save(newDepartment);
    }

    public Department getDepartment(Long id) {
        return departmentRepository.getById(id);
    }

    public Department updateDepartment(Long id, Department updatedDepartment) {

        Department oldDataDepartment = departmentRepository.getById(id);

        oldDataDepartment.setName(updatedDepartment.getName());
        oldDataDepartment.setEmployee(updatedDepartment.getEmployee());
        oldDataDepartment.setPolicy(updatedDepartment.getPolicy());

        return departmentRepository.save(oldDataDepartment);

    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }


}
