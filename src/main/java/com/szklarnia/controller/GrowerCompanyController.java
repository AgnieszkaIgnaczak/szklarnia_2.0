package com.szklarnia.controller;

import com.szklarnia.model.Greenhouse;
import com.szklarnia.model.GrowerCompany;
import com.szklarnia.service.GrowerCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/growerCompany")
public class GrowerCompanyController {

    private GrowerCompanyService growerCompanyService;

    @Autowired
    public GrowerCompanyController(GrowerCompanyService growerCompanyService) {
        this.growerCompanyService = growerCompanyService;
    }

    @GetMapping
    public Iterable<GrowerCompany> getAllGrowerCompanies() {
        return growerCompanyService.getAllGrowerCompanies();
    }

    @DeleteMapping("/{growerCompanyId}")
    public ResponseEntity<Void> deleteGrowerCompanyById(@PathVariable int growerCompanyId) {
        if(growerCompanyService.getGrowerCompanyById(growerCompanyId).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            growerCompanyService.deleteGrowerCompanyById(growerCompanyId);
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{growerCompanyId}")
    public ResponseEntity<GrowerCompany> getGrowerCompanyById(@PathVariable int growerCompanyId) {
        Optional<GrowerCompany> responseFromGrowerCompany = growerCompanyService.getGrowerCompanyById(growerCompanyId);
        if(responseFromGrowerCompany.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(responseFromGrowerCompany.get());
        }
    }

    @PostMapping
    public ResponseEntity<GrowerCompany> postNewGrowerCompany(@RequestBody GrowerCompany newGrowerCompany) {
        Optional <GrowerCompany> savedGrowerCompany = growerCompanyService.postNewGrowerCompany(newGrowerCompany);

        if(savedGrowerCompany.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // dla nulla
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(savedGrowerCompany.get());
        }
    }

    @PutMapping("/completeUpdate/{growerCompanyId}")
    public ResponseEntity<String> completeGrowerCompanyEntityUpdate(@PathVariable Integer growerCompanyId, @RequestBody GrowerCompany updatedGrowerCompany) {
        Optional<GrowerCompany> growerCompanyToBeUpdated = growerCompanyService.completeGrowerCompanyEntityUpdate(growerCompanyId, updatedGrowerCompany);
        if(growerCompanyToBeUpdated.isPresent()) {
            return ResponseEntity.ok("Grower company updated successfully!"); //dlatego ResponseEntity przyjmuje typ String
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{growerCompanyId}/setProduct/{productId}")
    public ResponseEntity<String> setProductForGrowerCompany(@PathVariable Integer productId, @PathVariable Integer growerCompanyId) { //tu kolejność nie jest istotna
        Optional<GrowerCompany> productForGrowerCompany = growerCompanyService.setProductForGrowerCompany(productId, growerCompanyId); //ta linia vs. metoda w service
        if(productForGrowerCompany.isPresent()) {
            return ResponseEntity.ok("Product set for Grower Company successfully");
        }
        return ResponseEntity.notFound().build();
    }



}
