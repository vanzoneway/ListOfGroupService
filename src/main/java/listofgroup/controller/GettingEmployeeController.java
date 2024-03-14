package listofgroup.controller;


import listofgroup.model.InfoAboutNameEmployee;
import listofgroup.service.GettingEmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GettingEmployeeController {

    private final GettingEmployeeService gettingEmployeeService;

    public GettingEmployeeController(GettingEmployeeService gettingEmployeeService) {
        this.gettingEmployeeService = gettingEmployeeService;
    }

    @PostMapping("/postEmployee")
    public String postEmployee() {
        gettingEmployeeService.saveAllEmployeeToDatabaseFromApi();
        return "Succesfully saved/updated all employees";
    }

    @GetMapping("/getEmployeeStartedWith/{prefix}")
    public ResponseEntity<String> getEmployeeStartedWith(@PathVariable String prefix) {
        return gettingEmployeeService.getEmployeeByPrefix(prefix);
    }

    @GetMapping("/getAllEmployees")
    public Page<InfoAboutNameEmployee> getAllEmployees(
            @RequestParam(defaultValue = "1") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit) {
        return gettingEmployeeService.getAllEmployees(offset, limit);
    }
}
