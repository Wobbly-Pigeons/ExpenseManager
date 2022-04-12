//package wobbly.pigeons.expensemanager.model.listener;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import wobbly.pigeons.expensemanager.model.Expense;
//import wobbly.pigeons.expensemanager.model.Policy;
//
//import javax.persistence.*;
//
//
//public class PolicyListeners {
//
//    @Autowired
//    Policy policy;
//
//    @PrePersist
//    public void prePersist(Expense expense) {
//
//        expense.setDepartmentPolicyBudget(policy.getBudgetMonthly());
//
//    }
//
//    @PreUpdate
//    public void preUpdate(Object o) {
//
//    }
//
//    @PreRemove
//    public void preRemove(Object o) {
//
//    }
//
//    @PostLoad
//    public void postLoad(Object o) {
//
//    }
//
//    @PostRemove
//    public void postRemove(Object o) {
//
//    }
//
//    @PostUpdate
//    public void postUpdate(Object o) {
//
//    }
//
//    @PostPersist
//    public void postPersist(Object o) {
//
//    }
//}
