package wobbly.pigeons.expensemanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wobbly.pigeons.expensemanager.model.Employee;
import wobbly.pigeons.expensemanager.model.Manager;
import wobbly.pigeons.expensemanager.repository.ManagerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerService {

    private ManagerRepository managerRepository;

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
        oldDataManager.setEmployeeRole((updatedManager.getEmployeeRole()));
        oldDataManager.setDob(updatedManager.getDob());
        oldDataManager.setPassword(updatedManager.getPassword());
        oldDataManager.setExpenses(updatedManager.getExpenses());

        return managerRepository.save(oldDataManager);

    }

    public void deleteManager(Long id){
        managerRepository.deleteById(id);
    }

    public Manager findByEmail(String email) {
        return managerRepository.findByEmail(email);
    }
}
