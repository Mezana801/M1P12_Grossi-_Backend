package com.example.grossi.controller;

import com.example.grossi.model.ClientEntity;
import com.example.grossi.service.ClientEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientEntityController {

    private final ClientEntityService clientService;

    @Autowired
    public ClientEntityController(ClientEntityService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<ClientEntity> getClients() {
        return clientService.getAllClients();
    }
}

