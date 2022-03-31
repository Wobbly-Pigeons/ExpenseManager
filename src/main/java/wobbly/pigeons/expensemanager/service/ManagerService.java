package wobbly.pigeons.expensemanager.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wobbly.pigeons.expensemanager.models.Managers;
import wobbly.pigeons.expensemanager.repositories.ManagersRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagersService {

    private ManagersRepository managersRepository;

    public List<Managers> getManagersList() {
        return managersRepository.findAll();
    }

    public Managers addManager(Managers newManager) {
        return managersRepository.save(newManager);
    }

    public Managers getManager(Long id) {
        return managersRepository.getById(id);
    }

    public Managers updateManager(Managers updatedManager, Long id) {

        Managers oldDataManager = managersRepository.getById(id);

        oldDataManager.setName(updatedManager.getName());
        oldDataManager.setEmail(updatedManager.getEmail());
        oldDataManager.setManagers(updatedManager.getManagers());
        oldDataManager.setEmployeeRole((updatedManager.getEmployeeRole()));
        oldDataManager.setDob(updatedManager.getDob());
        oldDataManager.setPassword(updatedManager.getPassword());
        oldDataManager.setExpenses(updatedManager.getExpenses());

        return managersRepository.save(oldDataManager);

    }

    public void deleteManager(Long id){
        managersRepository.deleteById(id);
    }
}
