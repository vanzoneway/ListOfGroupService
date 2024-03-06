package listofgroup.controller;

import listofgroup.dao.InfoAboutNameEmployeeRepository;
import listofgroup.service.ScheduleGroupService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ScheduleGroupController {

    private final ScheduleGroupService scheduleGroupService;
    private final InfoAboutNameEmployeeRepository infoAboutNameEmployeeRepository;


    public ScheduleGroupController(ScheduleGroupService scheduleGroupService, InfoAboutNameEmployeeRepository infoAboutNameEmployeeRepository) {
        this.scheduleGroupService = scheduleGroupService;
        this.infoAboutNameEmployeeRepository = infoAboutNameEmployeeRepository;
    }


    @GetMapping(value = "/schedule/{groupNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getSchedule(@PathVariable String groupNumber) {

        if(infoAboutNameEmployeeRepository.findAll().isEmpty())
            return "You have to do GET-response: /getEmployee first of all!";

        scheduleGroupService.saveScheduleToDatabaseFromApi(groupNumber);

        return scheduleGroupService.getGeneralInfoGroupAsJsonString(groupNumber);
    }


}