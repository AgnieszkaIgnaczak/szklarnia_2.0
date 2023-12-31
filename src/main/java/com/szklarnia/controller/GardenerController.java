package com.szklarnia.controller;

import com.szklarnia.model.Gardener;
import com.szklarnia.service.GardenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gardener")
public class GardenerController {

    private GardenerService gardenerService;

    @Autowired
    public GardenerController(GardenerService gardenerService) {
        this.gardenerService = gardenerService;
    } //wstrzykiwanie zależności

    @GetMapping
    public Iterable<Gardener> getAllGardeners() {
        return gardenerService.getAllGardeners();
    }

    @DeleteMapping("/{gardenerId}")
    public ResponseEntity<Void> deleteGardenerById(@PathVariable int gardenerId) {
        gardenerService.deleteGardenerById(gardenerId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{gardenerId}")
    public ResponseEntity<Gardener> getGardenerById(@PathVariable int gardenerId) {
        Gardener gardenerById = gardenerService.getGardenerById(gardenerId);
        return ResponseEntity.ok(gardenerById);
    }

    @PostMapping
    public ResponseEntity<Gardener> postNewGardener(@RequestBody Gardener newGardener) {
        Gardener savedGardener = gardenerService.postNewGardener(newGardener);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGardener);
    }

    @PutMapping("/completeUpdate/{gardenerId}")
    public ResponseEntity<String> completeGardenerEntityUpdate(@PathVariable Integer gardenerId, @RequestBody Gardener updatedGardener) {
        gardenerService.completeGardenerEntityUpdated(gardenerId, updatedGardener);
        return ResponseEntity.ok("Gardener updated successfully!");
    }

    @GetMapping("/getGardenersByName/{name}")
    public List<Gardener> findAllGardenersByName(@PathVariable String name) {
        return gardenerService.findAllGardenersByName(name);
    }

    //Patch przyjmuje id szklarni i id ogrodnika
    //2 endpointy
    //OneToOne
    @PatchMapping("/{gardenerId}/setGreenhouse/{greenhouseId}")
    public ResponseEntity<String> setGreenhouseForGardener(@PathVariable Integer gardenerId, @PathVariable Integer greenhouseId) { //zmienna ze ścieżki, linijka 70
        gardenerService.setGreenhouseForGardener(greenhouseId, gardenerId);
        return ResponseEntity.ok("Greenhouse set for Gardener successfully!");
    }




}
