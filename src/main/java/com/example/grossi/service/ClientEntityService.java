package com.example.grossi.service;


import com.example.grossi.model.ClientEntity;
import com.example.grossi.repository.ClientEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientEntityService {

    private final ClientEntityRepository clientEntityRepository;

    @Autowired
    public ClientEntityService(ClientEntityRepository clientEntityRepository) {
        this.clientEntityRepository = clientEntityRepository;
    }

    // Récupérer la liste de tous les clients
    public List<ClientEntity> getAllClients() {
        return clientEntityRepository.findAll();
    }
}
