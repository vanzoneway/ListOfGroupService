package listofgroup.controller;


import listofgroup.dao.InfoAboutNameEmployeeRepository;
import listofgroup.service.ScheduleGroupService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class ScheduleGroupController {

    private final ScheduleGroupService scheduleGroupService;
    private final InfoAboutNameEmployeeRepository infoAboutNameEmployeeRepository;

    public ScheduleGroupController(ScheduleGroupService scheduleGroupService,
                                   InfoAboutNameEmployeeRepository infoAboutNameEmployeeRepository) {
        this.scheduleGroupService = scheduleGroupService;
        this.infoAboutNameEmployeeRepository = infoAboutNameEmployeeRepository;

    }


    @GetMapping(value = "/getSchedule/{groupNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSchedule(@PathVariable String groupNumber) {

        if(infoAboutNameEmployeeRepository.findAll().isEmpty())
            return ResponseEntity.ok("You have to do GET-response: /getEmployee first of all!");

        return scheduleGroupService.getGeneralInfoGroupAsJsonString(groupNumber);
    }

    @DeleteMapping("/removeGroupFromDatabase/{groupNumber}")
    public ResponseEntity<String> removeGroup(@PathVariable String groupNumber) {
        return scheduleGroupService.removeGroupFromDatabase(groupNumber);
    }

    @PostMapping(value = "/postSchedule/{groupNumber}")
    public ResponseEntity<String> postGroup(@PathVariable String groupNumber) {
        return scheduleGroupService.saveScheduleToDatabaseFromApi(groupNumber);
    }

}