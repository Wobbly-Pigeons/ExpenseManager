package wobbly.pigeons.expensemanager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wobbly.pigeons.expensemanager.model.Employee;
import wobbly.pigeons.expensemanager.model.Expense;
import wobbly.pigeons.expensemanager.model.Manager;
import wobbly.pigeons.expensemanager.model.ReceiptStatuses;
import wobbly.pigeons.expensemanager.repository.ExpenseRepository;
import wobbly.pigeons.expensemanager.repository.ManagerRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManagerService {

  private final ManagerRepository managerRepository;
  private final ExpenseRepository expenseRepository;

  public List<Manager> getManagersList() {
    return managerRepository.findAll();
  }

  public Manager addManager(Manager newManager) {
    return managerRepository.save(newManager);
  }

  public Manager getManager(Long id) {
    return managerRepository.getById(id);
  }

  public Manager updateManager(Manager updatedManager, Long id) {


        Manager oldDataManager = managerRepository.getById(id);

        oldDataManager.setName(updatedManager.getName());
        oldDataManager.setEmail(updatedManager.getEmail());
        oldDataManager.setManager(updatedManager.getManager());
        oldDataManager.setDob(updatedManager.getDob());
        oldDataManager.setPassword(updatedManager.getPassword());
        oldDataManager.setExpenses(updatedManager.getExpenses());
    return managerRepository.save(oldDataManager);
  }

  public void deleteManager(Long id) {
    managerRepository.deleteById(id);
  }

  public Manager findByEmail(String email) {
    return managerRepository.findByEmail(email);
  }

  public Page<Expense> findPaginatedExpensesByUser(
      int pageNo, int pageSize, String sortField, String sortDir, Manager currentUser) {
    Sort sort =
        sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
            ? Sort.by(sortField).ascending()
            : Sort.by(sortField).descending();
    Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
    List<Expense> expenses = new ArrayList<>();

    //the following code gets the employees for the current manager, then converts each employee's expenses into a stream
    currentUser.getEmployees().forEach(employee -> employee.getExpenses().stream()
            //then it filters each employee's expenses to only those that are submittedandpending status
            .filter(expense -> expense.getCurrentStatus() == ReceiptStatuses.SUBMITTEDANDPENDING)
            //then it adds each of those filtered expenses to a list of expenses
            .forEach(expenses::add));
    // this could have all been accomplished with a sql query, but the experience with java8 was interesting

    Page<Expense> page = new PageImpl<>(expenses, pageable, expenses.size());
    return page;
  }

  public void addEmployee(Employee emp, Manager manager) {
    emp.setManager(manager);
    manager.getEmployees().add(emp);
  }
}
