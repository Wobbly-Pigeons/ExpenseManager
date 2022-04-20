package wobbly.pigeons.expensemanager.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wobbly.pigeons.expensemanager.model.Policy;
import wobbly.pigeons.expensemanager.repository.PolicyRepository;


@Service
@AllArgsConstructor
public class PolicyService {

    PolicyRepository policyRepository;

    public Policy addNewPolicy(Policy policy)
    {
        return policyRepository.save(policy);
    }


}
