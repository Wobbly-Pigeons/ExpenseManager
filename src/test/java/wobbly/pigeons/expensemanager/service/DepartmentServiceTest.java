package wobbly.pigeons.expensemanager.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import wobbly.pigeons.expensemanager.model.Department;
import wobbly.pigeons.expensemanager.model.DepartmentPolicy;
import wobbly.pigeons.expensemanager.model.Employee;
import wobbly.pigeons.expensemanager.repository.DepartmentRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DepartmentServiceTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    List<Employee> listOfEmployees = new ArrayList<Employee>();
    List<Department> listOfDepartments = new ArrayList<Department>();
    Employee employee1 = new Employee();
    Employee employee2 = new Employee();
    Employee employee3 = new Employee();


    Department department1 = new Department();
    Department department2 = new Department();
    Department department3 = new Department();
    Department department4 = new Department();

    @Test
    void getDepartmentsList() {

        listOfDepartments.add(department1);
        listOfDepartments.add(department2);
        listOfDepartments.add(department3);
        listOfDepartments.add(department4);

        departmentRepository.saveAll(listOfDepartments);

        List<Department> departments = departmentRepository.findAll();

                assertThat(departments.size()).isEqualTo(4);
    }

    @Test
    void deleteDepartment() {

        departmentRepository.save(department1);
        departmentRepository.save(department2);

        List<Department> all = departmentRepository.findAll();

        assertThat(all.size()).isEqualTo(2);

        departmentRepository.deleteById(2L);

        List<Department> all2 = departmentRepository.findAll();

        assertThat(all2.size()).isEqualTo(1);
    }

    @Test
    void addDepartment() {
        //TODO

//        department1.setName("Miguel");
//        departmentRepository.save(department1);
//        Department save = departmentRepository.getById(1L);
//                assertThat(save.getName()).isEqualTo("Miguel");
//
//        DepartmentPolicy policy = new DepartmentPolicy();
//
//        Department newDepartmentDetails = new Department("IT",listOfEmployees,policy);
//
//        departmentRepository.save(newDepartmentDetails);
//
//        List<Department> all = departmentRepository.findAll();
//        assertThat(all.size()).isEqualTo(2);
//
//        assertThat(departmentRepository.getById(2L).getName()).isEqualTo("IT");
    }

    @Test
    void updateDepartment() {
    }

    @Test
    void getDepartmentById() {
    }

    @Test
    void geDepartmentByName() {
    }



    @Test
    void getDepartment() {
    }
}