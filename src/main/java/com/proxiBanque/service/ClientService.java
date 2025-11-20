package com.proxibanque.service;

import com.proxibanque.model.Client;
import com.proxibanque.model.CompteCourant;
import com.proxibanque.model.CompteEpargne;
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

        // Vérifier si le client n'a pas déjà de compte (pour éviter double création si update)
        if (c.getCompteCourant() == null) {
            CompteCourant cc = new CompteCourant();
            cc.setSolde(0);
            cc.setDecouvertAutorise(1000);
            c.setCompteCourant(cc);
        }

        if (c.getCompteEpargne() == null) {
            CompteEpargne ce = new CompteEpargne();
            ce.setSolde(0);
            c.setCompteEpargne(ce);
        }


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
