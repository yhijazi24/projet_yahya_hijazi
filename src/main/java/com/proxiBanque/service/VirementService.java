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

        Client emetteur = clientService.findById(req.getSourceId());

        if (emetteur == null) {
            return "Client émetteur introuvable";
        }

        switch (req.getType()) {

            case "CLIENT_CC_CC":
                Client recepteur = clientService.findById(req.getDestinationId());
                if (recepteur == null) return "Client destinataire introuvable";

                return virementCCversCC(emetteur, recepteur, req.getMontant());

            case "INTERNE_CC_CE":
                return virementCCversCE(emetteur, req.getMontant());

            case "INTERNE_CE_CC":
                return virementCEversCC(emetteur, req.getMontant());

            default:
                return "Type de virement inconnu.";
        }
    }

    private String virementCCversCC(Client emetteur, Client recepteur, double montant) {

        if (emetteur.getCompteCourant().getSolde() + emetteur.getCompteCourant().getDecouvertAutorise() < montant) {
            return "Solde insuffisant même avec découvert";
        }

        emetteur.getCompteCourant().setSolde(emetteur.getCompteCourant().getSolde() - montant);
        recepteur.getCompteCourant().setSolde(recepteur.getCompteCourant().getSolde() + montant);

        clientService.save(emetteur);
        clientService.save(recepteur);

        return "Virement CLIENT CC → CC réussi";
    }

    private String virementCCversCE(Client client, double montant) {

        if (client.getCompteCourant().getSolde() + client.getCompteCourant().getDecouvertAutorise() < montant) {
            return "Solde insuffisant pour virement CC → CE";
        }

        client.getCompteCourant().setSolde(client.getCompteCourant().getSolde() - montant);
        client.getCompteEpargne().setSolde(client.getCompteEpargne().getSolde() + montant);

        clientService.save(client);

        return "Virement interne CC → CE effectué";
    }

    private String virementCEversCC(Client client, double montant) {

        if (client.getCompteEpargne().getSolde() < montant) {
            return "Solde épargne insuffisant pour CE → CC";
        }

        client.getCompteEpargne().setSolde(client.getCompteEpargne().getSolde() - montant);
        client.getCompteCourant().setSolde(client.getCompteCourant().getSolde() + montant);

        clientService.save(client);

        return "Virement interne CE → CC effectué";
    }
}
