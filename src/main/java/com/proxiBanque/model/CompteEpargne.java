package com.proxibanque.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class CompteEpargne extends Compte {

    private double tauxRemuneration;
}
