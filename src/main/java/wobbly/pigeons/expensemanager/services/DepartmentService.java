package wobbly.pigeons.expensemanager.services;


import org.springframework.stereotype.Service;
import wobbly.pigeons.expensemanager.models.Department;
import wobbly.pigeons.expensemanager.repositories.DepartmentRepository;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public void deleteDepartment(Long id) {
        this.departmentRepository.deleteById(id);
    }

    public void addDepartment(Department department){
        departmentRepository.save(department);
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


}
