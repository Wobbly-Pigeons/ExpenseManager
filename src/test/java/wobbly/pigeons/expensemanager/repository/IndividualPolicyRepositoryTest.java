package wobbly.pigeons.expensemanager.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import wobbly.pigeons.expensemanager.model.IndividualPolicy;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class IndividualPolicyRepositoryTest {

    @Autowired
    private IndividualPolicyRepository individualPolicyRepository;

    @Test
    void getAllIndividualPolicy(){
        IndividualPolicy newIPolicy = new IndividualPolicy();
        IndividualPolicy newIPolicy2 = new IndividualPolicy();
        IndividualPolicy newIPolicy3 = new IndividualPolicy();

        individualPolicyRepository.save(newIPolicy);
        individualPolicyRepository.save(newIPolicy2);
        individualPolicyRepository.save(newIPolicy3);

        List<IndividualPolicy> all = individualPolicyRepository.findAll();

        for(IndividualPolicy policy : all){
            System.out.println(policy.getId());
        }

        assertThat(all.size()).isEqualTo(3); //Testing our connection to H2 db (Success)
    }
}