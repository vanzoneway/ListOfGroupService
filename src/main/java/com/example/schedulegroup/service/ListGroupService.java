package com.example.schedulegroup.service;

import com.example.schedulegroup.dao.ListGroupRepository;
import com.example.schedulegroup.model.ListGroupDto;
import com.example.schedulegroup.entity.ListGroupEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import com.google.gson.Gson;
import org.modelmapper.*;

import java.util.List;
import java.util.Objects;



@Service
public class ListGroupService {


    private final ModelMapper modelMapper;
    private final ListGroupRepository listGroupRepository;

    public ListGroupService(ModelMapper modelMapper, ListGroupRepository listGroupRepository) {
        this.modelMapper = modelMapper;
        this.listGroupRepository = listGroupRepository;
    }

    public void writeToDataBase(Mono<ResponseEntity<String>> response){
        List<ListGroupDto> listGroupDto = new Gson().fromJson(Objects.requireNonNull(response.block()).getBody(), new TypeToken<List<ListGroupDto>>() {}.getType());
        assert listGroupDto != null;
        List<ListGroupEntity> listGroupEntity = listGroupDto.stream()
                .map(dto -> modelMapper.map(dto, ListGroupEntity.class))
                .toList();
        listGroupRepository.saveAll(listGroupEntity);
    }

    public Mono<ResponseEntity<String>> responseEntityMono (WebClient webClient, String url){
        return webClient.get()
                .uri(url)
                .retrieve()
                .toEntity(String.class);
    }
}
