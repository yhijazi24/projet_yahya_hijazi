package com.proxibanque.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class CompteCourant extends Compte {

    private double decouvertAutorise;
}
