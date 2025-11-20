package com.proxibanque.service;

import com.proxibanque.model.Client;
import com.proxibanque.repository.ClientRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClientService {

    private final ClientRepository repo;

    public ClientService(ClientRepository repo) {
        this.repo = repo;
    }

    public Client save(Client c) {
        return repo.save(c);
    }

    public List<Client> findAll() {
        return repo.findAll();
    }

    public Client findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
