package wobbly.pigeons.expensemanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wobbly.pigeons.expensemanager.models.Managers;

import wobbly.pigeons.expensemanager.services.ManagersService;

import java.util.List;

@Controller
@RequestMapping(value ="/api/v1/Managers")
public class ManagersController {

    private ManagersService managersService;

    @GetMapping
    public List<Managers> getEmployeesList() {
        return managersService.getManagersList();
    }

    @PostMapping("/newEmployee")
    public Managers addEmployee(@RequestBody Managers newManager) {
        return managersService.addManager(newManager);
    }

    @GetMapping("/{id}")
    public Managers getEmployee(@PathVariable Long id) {
        return managersService.getManager(id);
    }

    @PutMapping("/{id}")
    public Managers updateEmployee(@RequestBody Managers updatedManager, @PathVariable Long id) {
        return managersService.updateManager(updatedManager, id);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        managersService.deleteManager(id);
    }
}
