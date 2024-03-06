package listofgroup.controller;


import listofgroup.service.GettingEmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GettingEmployeeController {

    private final GettingEmployeeService gettingEmployeeService;

    public GettingEmployeeController(GettingEmployeeService gettingEmployeeService) {
        this.gettingEmployeeService = gettingEmployeeService;
    }

    @GetMapping("/getEmployee")
    public String getEmployee() {
        gettingEmployeeService.saveAllEmployeeToDatabaseFromApi();
        return "Succesfully saved all employees";
    }
}
