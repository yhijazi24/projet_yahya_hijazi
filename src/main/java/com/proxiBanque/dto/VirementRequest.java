package com.proxibanque.dto;

import lombok.Data;

@Data
public class VirementRequest {
    private Long idEmetteur;
    private Long idRecepteur;
    private double montant;
}
