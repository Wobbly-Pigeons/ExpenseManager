package wobbly.pigeons.expensemanager.services;

import org.springframework.stereotype.Service;
import wobbly.pigeons.expensemanager.models.Roles;
import wobbly.pigeons.expensemanager.repositories.RolesRepository;

import java.util.List;

@Service
public class RolesService {

    private RolesRepository rolesRepository;

    public List<Roles> getRolesList() {
        return rolesRepository.findAll();
    }

    public Roles addRole(Roles newRole) {
        return rolesRepository.save(newRole);
    }

    public Roles getRolesById(Long id) {
        return rolesRepository.getById(id);
    }

    public Roles updateRole(Roles updatedRole, Long id) {

        Roles oldRole = rolesRepository.getById(id);

        oldRole.setRole(updatedRole.getRole()); // Since the only updatable field is a String, is better performance to get
        // a String instead an object Roles ????

        return rolesRepository.save(oldRole);
    }

    public void deleteRole(Long id) {
        rolesRepository.deleteById(id);
    }
}
