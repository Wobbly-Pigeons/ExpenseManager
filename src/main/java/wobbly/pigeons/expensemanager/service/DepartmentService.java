package wobbly.pigeons.expensemanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wobbly.pigeons.expensemanager.model.Department;
import wobbly.pigeons.expensemanager.repository.DepartmentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public void deleteDepartment(Long id) {
        this.departmentRepository.deleteById(id);
    }

    public Department addDepartment(Department department){
       return departmentRepository.save(department);
    }

    public Department updateDepartment(Long id, Department newDepartmentDetails) {
        Department oldDepartment = departmentRepository.findById(id).orElseThrow();
        oldDepartment.setName(newDepartmentDetails.getName());
        oldDepartment.setEmployee(newDepartmentDetails.getEmployee());

        return oldDepartment;
    }
    public Department getDepartmentById(long id) {
        return departmentRepository.findById(id).orElseThrow();
    }

    public Department geDepartmentByName(String name) {
        return departmentRepository.findByName(name).orElseThrow();

    }

    public List<String> getDepartmentsListbyNames() {
        List<String> departmentNames = new ArrayList<>();
        for (Department x : getDepartmentsList()) {
            departmentNames.add(x.getName());
        }
        return departmentNames;
    }

    public List<Department> getDepartmentsList() {
        return departmentRepository.findAll();
    }

    public Department getDepartment(Long id) {
        return departmentRepository.getById(id);
    }
}
