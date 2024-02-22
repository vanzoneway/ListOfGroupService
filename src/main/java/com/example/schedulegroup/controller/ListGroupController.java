package com.example.schedulegroup.controller;

import com.example.schedulegroup.service.ListGroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;




@RestController
public class ListGroupController {


    private final WebClient webClient;
    private final ListGroupService listGroupService;

    public ListGroupController(WebClient webClient, ListGroupService listGroupService) {
        this.webClient = webClient;
        this.listGroupService = listGroupService;
    }

    @GetMapping("/student-groups")
    public Mono<ResponseEntity<String>> getSchedule() {
        String url = "https://iis.bsuir.by/api/v1/student-groups";
        Mono<ResponseEntity<String>> response = listGroupService.responseEntityMono(webClient, url);
        listGroupService.writeToDataBase(response);
        return response;
    }


}