package listofgroup.controller;


import listofgroup.service.GettingEmployeeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GettingEmployeeController {

    private final GettingEmployeeService gettingEmployeeService;

    public GettingEmployeeController(GettingEmployeeService gettingEmployeeService) {
        this.gettingEmployeeService = gettingEmployeeService;
    }

    @PostMapping("/getEmployee")
    public String getEmployee() {
        gettingEmployeeService.saveAllEmployeeToDatabaseFromApi();
        return "Succesfully saved/updated all employees";
    }
}
