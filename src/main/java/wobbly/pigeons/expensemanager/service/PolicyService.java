package wobbly.pigeons.expensemanager.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wobbly.pigeons.expensemanager.model.DepartmentPolicy;
import wobbly.pigeons.expensemanager.model.IndividualPolicy;
import wobbly.pigeons.expensemanager.repository.DepartmentPolicyRepository;
import wobbly.pigeons.expensemanager.repository.IndividualPolicyRepository;

@Service
@AllArgsConstructor
public class PolicyService {

    private final DepartmentPolicyRepository departmentPolicyRepository;
    private final IndividualPolicyRepository individualPolicyRepository;

    public IndividualPolicy findIPolicy(Long id) {
        return individualPolicyRepository.getById(id);
    }

    public DepartmentPolicy findDPolicy(Long id) {
        return departmentPolicyRepository.getById(id);
    }

    public IndividualPolicy createIPolicy(IndividualPolicy newIPolicy) {
        return individualPolicyRepository.save(newIPolicy);
    }

    public void deleteIPolicy (Long id) {
        individualPolicyRepository.deleteById(id);
    }

    public IndividualPolicy updateIPolicy(IndividualPolicy updatedPolicy, Long id) {
        IndividualPolicy oldPolicy = updatedPolicy;
        return individualPolicyRepository.save(oldPolicy);
    }



}
