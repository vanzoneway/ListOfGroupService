package listofgroup.service;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import listofgroup.dao.InfoAboutNameEmployeeRepository;
import listofgroup.model.InfoAboutNameEmployee;
import listofgroup.dto.InfoAboutNameEmployeeDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class GettingEmployeeService {

    private final WebClient webClient;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final InfoAboutNameEmployeeRepository infoAboutNameEmployeeRepository;

    public GettingEmployeeService(WebClient webClient, Gson gson, ModelMapper modelMapper, InfoAboutNameEmployeeRepository infoAboutNameEmployeeRepository) {
        this.webClient = webClient;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.infoAboutNameEmployeeRepository = infoAboutNameEmployeeRepository;
    }
    @Transactional
    public void saveAllEmployeeToDatabaseFromApi() {
        String urlApi = "https://iis.bsuir.by/api/v1/employees/all";


        String response = webClient
                .get()
                .uri(urlApi)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        List<InfoAboutNameEmployeeDto> employeeDtoList = gson.fromJson(response, new TypeToken<List<InfoAboutNameEmployeeDto>>() {}.getType());
        assert employeeDtoList != null;
        List<InfoAboutNameEmployee> employeeEntityList = employeeDtoList
                .stream()
                .map(dto -> modelMapper.map(dto, InfoAboutNameEmployee.class))
                .toList();

        if(infoAboutNameEmployeeRepository.findAll().isEmpty()) {
            infoAboutNameEmployeeRepository.saveAll(employeeEntityList);
        }else {
            for(InfoAboutNameEmployee employee : employeeEntityList) {
                if(infoAboutNameEmployeeRepository.existsById(employee.getId())) {

                    InfoAboutNameEmployee infoAboutNameEmployeeFromDb = infoAboutNameEmployeeRepository.findById(employee.getId());
                    infoAboutNameEmployeeFromDb.setFirstName(employee.getFirstName());
                    infoAboutNameEmployeeFromDb.setLastName(employee.getLastName());
                    infoAboutNameEmployeeFromDb.setEmail(employee.getEmail());
                    infoAboutNameEmployeeFromDb.setDegree(employee.getDegree());
                    infoAboutNameEmployeeFromDb.setMiddleName(employee.getMiddleName());
                    infoAboutNameEmployeeFromDb.setCalendarId(employee.getCalendarId());
                    infoAboutNameEmployeeFromDb.setUrlId(employee.getUrlId());
                    infoAboutNameEmployeeFromDb.setDegreeAbbrev(employee.getDegreeAbbrev());
                    infoAboutNameEmployeeFromDb.setPhotoLink(employee.getPhotoLink());
                    infoAboutNameEmployeeRepository.save(infoAboutNameEmployeeFromDb);

                }
            }
        }


    }


    public ResponseEntity<String> getEmployeeByPrefix(String prefix) {
        try{
            if(infoAboutNameEmployeeRepository.findAll().isEmpty()) {
                return ResponseEntity.ok("Post employee at the beginning");
            }
            String mainJson = gson.toJson(infoAboutNameEmployeeRepository
                    .findByBeginOfName(prefix)
                    .stream()
                    .map(m -> modelMapper.map(m, InfoAboutNameEmployeeDto.class))
                    .toList());
            return ResponseEntity.ok(mainJson);
        }catch(Exception e) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(" ");
    }

    public Page<InfoAboutNameEmployee> getAllEmployees(Integer offset, Integer limit) {
        if (offset == null || offset < 0) {
            offset = 0;
        }
        if(limit == null || limit < 1) {
            limit = 10;
        }
        return infoAboutNameEmployeeRepository.findAll(PageRequest.of(offset, limit));
    }

}
