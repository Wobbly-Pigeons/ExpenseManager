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
}
