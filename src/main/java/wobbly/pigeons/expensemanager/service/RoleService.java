package wobbly.pigeons.expensemanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wobbly.pigeons.expensemanager.model.Role;
import wobbly.pigeons.expensemanager.repository.RoleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<Role> getRolesList() {
        return roleRepository.findAll();
    }

    public Role addRole(Role newRole) {
        return roleRepository.save(newRole);
    }

    public Role getRolesById(Long id) {
        return roleRepository.getById(id);
    }

    public Role updateRole(Role updatedRole, Long id) {

        Role oldRole = roleRepository.getById(id);

        oldRole.setRole(updatedRole.getRole()); // Since the only updatable field is a String, is better performance to get
        // a String instead an object Roles ????

        return roleRepository.save(oldRole);
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}
