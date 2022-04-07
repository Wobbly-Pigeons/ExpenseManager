package wobbly.pigeons.expensemanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wobbly.pigeons.expensemanager.model.Manager;
import wobbly.pigeons.expensemanager.service.ManagerService;

import java.util.List;

@Controller
@RequestMapping(value ="/api/v1/Managers")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @GetMapping
    public List<Manager> getEmployeesList() {
        return managerService.getManagersList();
    }

    @PostMapping("/newEmployee")
    public Manager addEmployee(@RequestBody Manager newManager) {
        return managerService.addManager(newManager);
    }

    @GetMapping("/{id}")
    public Manager getEmployee(@PathVariable Long id) {
        return managerService.getManager(id);
    }

    @PutMapping("/{id}")
    public Manager updateEmployee(@RequestBody Manager updatedManager, @PathVariable Long id) {
        return managerService.updateManager(updatedManager, id);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        managerService.deleteManager(id);
    }
}
