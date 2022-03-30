package wobbly.pigeons.expensemanager.services;

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
}
