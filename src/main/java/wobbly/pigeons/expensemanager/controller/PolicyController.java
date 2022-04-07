package wobbly.pigeons.expensemanager.controller;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import wobbly.pigeons.expensemanager.service.PolicyService;

@RestController
@RequiredArgsConstructor
public class PolicyController {

    private final PolicyService policyService;
}
