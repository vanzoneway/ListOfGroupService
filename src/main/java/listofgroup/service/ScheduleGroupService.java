package listofgroup.service;


import com.google.gson.*;
import listofgroup.dao.GeneralInfoGroupRepository;
import listofgroup.dao.InfoAboutNameEmployeeRepository;
import listofgroup.dao.InfoAboutNameGroupRepository;
import listofgroup.entity.*;
import listofgroup.model.GeneralInfoGroupDto;
import listofgroup.model.InfoAboutNameEmployeeDto;
import listofgroup.model.InfoAboutNameGroupDto;
import listofgroup.model.ScheduleDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ScheduleGroupService {

    private final InfoAboutNameGroupRepository infoAboutNameGroupRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final GeneralInfoGroupRepository generalInfoGroupRepository;

    private final listofgroup.dao.InfoAboutNameEmployeeRepository infoAboutNameEmployeeRepository;

    @Transactional
    public ResponseEntity<String> removeGroupFromDatabase(String groupNumber) {
        GeneralInfoGroupEntity generalInfoGroup = infoAboutNameGroupRepository.findByName(groupNumber).getGeneralInfoGroup();

        if (generalInfoGroup != null) {
            try {
                generalInfoGroupRepository.delete(generalInfoGroup);
                return ResponseEntity.ok("Group removed successfully");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.ok().build();
        }
    }

    public ScheduleGroupService(InfoAboutNameGroupRepository infoAboutNameGroupRepository,
                                Gson gson, ModelMapper modelMapper,
                                GeneralInfoGroupRepository generalInfoGroupRepository,
                                InfoAboutNameEmployeeRepository infoAboutNameEmployeeRepository) {

        this.infoAboutNameGroupRepository = infoAboutNameGroupRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.generalInfoGroupRepository = generalInfoGroupRepository;
        this.infoAboutNameEmployeeRepository = infoAboutNameEmployeeRepository;
    }

    @Transactional
    public void saveScheduleToDatabaseFromApi(String groupNumber) {
        String apiUrl = "https://iis.bsuir.by/api/v1/schedule?studentGroup=" + groupNumber;
        InfoAboutNameGroupEntity infoAboutNameGroupFromDb = infoAboutNameGroupRepository.findByName(groupNumber);

        WebClient webClient = WebClient.create();

        if (infoAboutNameGroupFromDb == null) {


            String response = webClient.get()
                    .uri(apiUrl).accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            assert response != null;
            JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
            JsonObject infoAboutNameGroupJson = jsonResponse.getAsJsonObject("studentGroupDto");

            GeneralInfoGroupDto generalInfoGroupDto = gson.fromJson(jsonResponse, GeneralInfoGroupDto.class);
            GeneralInfoGroupEntity generalInfoGroup = modelMapper.map(generalInfoGroupDto, GeneralInfoGroupEntity.class);

            InfoAboutNameGroupDto infoAboutNameGroupDto = gson.fromJson(infoAboutNameGroupJson, InfoAboutNameGroupDto.class);
            InfoAboutNameGroupEntity infoAboutNameGroup = modelMapper.map(infoAboutNameGroupDto, InfoAboutNameGroupEntity.class);

            JsonObject schedulesJson = jsonResponse.getAsJsonObject("schedules");

            TypeToken<HashMap<String, JsonArray>> typeToken = new TypeToken<>() {};

            Map<String, JsonArray> schedulesJsonMap = gson.fromJson(schedulesJson, typeToken.getType());
            Map<String, ScheduleListEntity> schedulesEntityMap = new HashMap<>();

            convertSchedulesJsonMapToEntityMap
                    (schedulesJsonMap,
                    schedulesEntityMap,
                    generalInfoGroup,
                    infoAboutNameGroup);

            generalInfoGroup.setInfoAboutNameGroup(infoAboutNameGroup);
            generalInfoGroup.setId(infoAboutNameGroup.getId());

            infoAboutNameGroup.setGeneralInfoGroup(generalInfoGroup);

            generalInfoGroup.setScheduleListMap(schedulesEntityMap);

            generalInfoGroupRepository.save(generalInfoGroup);
        }


    }

    private void convertSchedulesJsonMapToEntityMap(Map<String, JsonArray> schedulesJsonMap,
                                                    Map<String, ScheduleListEntity> schedulesEntityMap,
                                                    GeneralInfoGroupEntity generalInfoGroup,
                                                    InfoAboutNameGroupEntity infoAboutNameGroup) {

        for(Map.Entry<String, JsonArray> entry : schedulesJsonMap.entrySet()){
            ScheduleListEntity scheduleList = new ScheduleListEntity();
            scheduleList.setScheduleList(gson.fromJson(entry.getValue(), new TypeToken<List<ScheduleEntity>>(){}.getType()));
            scheduleList.setGeneralInfoGroup(generalInfoGroup);

            for(ScheduleEntity scheduleEntity : scheduleList.getScheduleList()){
                scheduleEntity.setScheduleList(scheduleList);
            }
            for(JsonElement innerSchedulesJson : entry.getValue()) {
                JsonArray schedulesJsonArray = innerSchedulesJson.getAsJsonObject()
                        .getAsJsonArray("employees");
                for(JsonElement element : schedulesJsonArray) {

                    int employeeId = element.getAsJsonObject()
                            .getAsJsonPrimitive("id")
                            .getAsInt();

                    InfoAboutNameEmployeeEntity infoAboutNameEmployee = infoAboutNameEmployeeRepository.findById(employeeId);

                    if (!infoAboutNameEmployee.getInfoAboutNameGroupList().contains(infoAboutNameGroup)
                            && !infoAboutNameGroup.getInfoAboutNameEmployeeList().contains(infoAboutNameEmployee)) {

                        infoAboutNameGroup.getInfoAboutNameEmployeeList().add(infoAboutNameEmployee);

                    }
                }

            }

            schedulesEntityMap.put(entry.getKey(), scheduleList);
        }
    }

    public String getGeneralInfoGroupAsJsonString(String groupNumber) {

        InfoAboutNameGroupEntity infoAboutNameGroupFromDb = infoAboutNameGroupRepository.findByName(groupNumber);

        if (infoAboutNameGroupFromDb == null)
            return "Not found";

        GeneralInfoGroupEntity generalInfoGroup= infoAboutNameGroupFromDb.getGeneralInfoGroup();


        String generalInfoGroupJsonString = gson.toJson(modelMapper.map(generalInfoGroup, GeneralInfoGroupDto.class));
        JsonObject mainJson = JsonParser.parseString(generalInfoGroupJsonString).getAsJsonObject();

        String infoAboutNameGroupJsonString = gson.toJson(modelMapper.map(infoAboutNameGroupFromDb, InfoAboutNameGroupDto.class));
        JsonObject infoAboutNameGroupJson = JsonParser.parseString(infoAboutNameGroupJsonString).getAsJsonObject();
        mainJson.add("studentGroupDto", infoAboutNameGroupJson);
        mainJson.remove("id");

        Map<String, JsonObject> schedulesJsonMap = new HashMap<>();

        for(Map.Entry<String, ScheduleListEntity> entry : generalInfoGroup.getScheduleListMap().entrySet()) {
            JsonObject schedulesListJson = new JsonObject();
            int i = 0;

            for(ScheduleEntity scheduleEntity : entry.getValue().getScheduleList()) {
                String elementOfListJsonString = gson.toJson(modelMapper.map(scheduleEntity, ScheduleDto.class));
                JsonObject elementOfListJson = JsonParser.parseString(elementOfListJsonString).getAsJsonObject();
                elementOfListJson.remove("id");
                schedulesListJson.add(Integer.toString(i++), elementOfListJson);
            }
            schedulesJsonMap.put(entry.getKey(), schedulesListJson);
        }

        JsonObject schedulesJsonMapInLine = new JsonObject();
        for(Map.Entry<String, JsonObject> entry : schedulesJsonMap.entrySet()) {
            schedulesJsonMapInLine.add(entry.getKey(), entry.getValue());
        }

        mainJson.add("allEmployees", allEmployeesJsonInLine(infoAboutNameGroupFromDb));
        mainJson.add("schedules", schedulesJsonMapInLine);



        return gson.toJson(mainJson);

    }

    private JsonObject allEmployeesJsonInLine (InfoAboutNameGroupEntity infoAboutNameGroupFromDb) {

        List<InfoAboutNameEmployeeEntity> employees = infoAboutNameGroupFromDb.getInfoAboutNameEmployeeList();
        JsonObject jsonObject = new JsonObject();

        int i = 0;
        for(InfoAboutNameEmployeeEntity employee : employees) {
            JsonObject employeeJson = JsonParser
                    .parseString(gson.toJson(modelMapper.map(employee, InfoAboutNameEmployeeDto.class)))
                    .getAsJsonObject();
            jsonObject.add(String.valueOf(i++), employeeJson);
        }
        return jsonObject;
    }


}
