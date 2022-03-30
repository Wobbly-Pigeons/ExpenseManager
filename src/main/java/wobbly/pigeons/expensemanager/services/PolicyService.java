package wobbly.pigeons.expensemanager.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wobbly.pigeons.expensemanager.models.Policy;
import wobbly.pigeons.expensemanager.repositories.PolicyRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PolicyService {

    private PolicyRepository policyRepository;

    public List<Policy> getPolicyList() {
        return policyRepository.findAll();
    }
}
