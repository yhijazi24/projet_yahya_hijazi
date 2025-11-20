package com.proxibanque.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class CompteCourant extends Compte {
    private double decouvertAutorise = 1000.00;
}
