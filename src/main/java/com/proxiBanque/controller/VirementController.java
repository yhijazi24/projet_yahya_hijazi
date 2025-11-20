package com.proxibanque.controller;

import com.proxibanque.dto.VirementRequest;
import com.proxibanque.service.VirementService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/virements")
public class VirementController {

    private final VirementService service;

    public VirementController(VirementService service) {
        this.service = service;
    }

    @PostMapping
    public String virement(@RequestBody VirementRequest request) {
        return service.effectuerVirement(request);
    }
}
