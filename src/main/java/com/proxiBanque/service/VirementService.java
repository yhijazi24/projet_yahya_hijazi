package com.proxibanque.service;

import com.proxibanque.dto.VirementRequest;
import com.proxibanque.model.Client;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VirementService {

    private final ClientService clientService;

    public VirementService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Transactional
    public String effectuerVirement(VirementRequest req) {

        Client emetteur = clientService.findById(req.getIdEmetteur());
        Client recepteur = clientService.findById(req.getIdRecepteur());

        if (emetteur == null || recepteur == null) {
            return "Client introuvable";
        }

        double soldeSource = emetteur.getCompteCourant().getSolde();

        if (soldeSource + emetteur.getCompteCourant().getDecouvertAutorise() < req.getMontant()) {
            return "Solde insuffisant avec découvert";
        }

        // Débit
        emetteur.getCompteCourant().setSolde(soldeSource - req.getMontant());

        // Crédit
        recepteur.getCompteCourant().setSolde(
                recepteur.getCompteCourant().getSolde() + req.getMontant()
        );

        clientService.save(emetteur);
        clientService.save(recepteur);

        return "Virement réussi";
    }
}
