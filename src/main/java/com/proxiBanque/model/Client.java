package com.proxibanque.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Client {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String adresse;
    private String codePostal;
    private String ville;
    private String telephone;

    @OneToOne(cascade = CascadeType.ALL)
    private CompteCourant compteCourant;

    @OneToOne(cascade = CascadeType.ALL)
    private CompteEpargne compteEpargne;
}
