package wobbly.pigeons.expensemanager.controller;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import wobbly.pigeons.expensemanager.service.PolicyService;

@RestController
@AllArgsConstructor
public class PolicyController {

    private final PolicyService policyService;
}
