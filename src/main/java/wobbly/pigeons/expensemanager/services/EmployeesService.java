package wobbly.pigeons.expensemanager.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wobbly.pigeons.expensemanager.models.Employees;
import wobbly.pigeons.expensemanager.repositories.EmployeesRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeesService {

    EmployeesRepository employeesRepository;

    public List<Employees> getEmployeesList(){
        return employeesRepository.findAll();
    }
}
