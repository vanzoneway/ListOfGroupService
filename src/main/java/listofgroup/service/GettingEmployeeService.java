package listofgroup.service;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import listofgroup.dao.InfoAboutNameEmployeeRepository;
import listofgroup.entity.InfoAboutNameEmployeeEntity;
import listofgroup.model.InfoAboutNameEmployeeDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
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
        List<InfoAboutNameEmployeeEntity> employeeEntityList = employeeDtoList
                .stream()
                .map(dto -> modelMapper.map(dto, InfoAboutNameEmployeeEntity.class))
                .toList();

        if(infoAboutNameEmployeeRepository.findAll().isEmpty()) {
            infoAboutNameEmployeeRepository.saveAll(employeeEntityList);
        }else {
            for(InfoAboutNameEmployeeEntity employee : employeeEntityList) {
                if(infoAboutNameEmployeeRepository.existsById(employee.getId())) {

                    InfoAboutNameEmployeeEntity infoAboutNameEmployeeFromDb = infoAboutNameEmployeeRepository.findById(employee.getId());
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

}
