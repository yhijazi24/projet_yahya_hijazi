package com.proxibanque.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class CompteEpargne extends Compte {

    private double tauxInteret = 3.0;
}
