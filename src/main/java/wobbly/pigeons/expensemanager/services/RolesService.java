package wobbly.pigeons.expensemanager.services;

import org.springframework.stereotype.Service;
import wobbly.pigeons.expensemanager.models.Roles;
import wobbly.pigeons.expensemanager.repositories.RolesRepository;

import java.util.List;

@Service
public class RolesService {

    private RolesRepository rolesRepository;

    public List<Roles> getRolesList(){
        return rolesRepository.findAll();
    }
}
