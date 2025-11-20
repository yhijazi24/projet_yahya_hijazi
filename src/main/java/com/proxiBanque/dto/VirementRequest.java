package com.proxibanque.dto;

import lombok.Data;

@Data
public class VirementRequest {
    private Long sourceId;
    private Long destinationId;
    private double montant;
    private String type;
}

