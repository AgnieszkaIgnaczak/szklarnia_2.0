package com.szklarnia.controller;

import com.szklarnia.model.Gardener;
import com.szklarnia.service.GardenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/gardener")
public class GardenerController {

    private GardenerService gardenerService;

    @Autowired
    public GardenerController(GardenerService gardenerService) {
        this.gardenerService = gardenerService;
    }

    @GetMapping
    public Iterable<Gardener> getAllGardeners() {
        return gardenerService.getAllGardeners();
    }

    @DeleteMapping("/{gardenerId}")
    public ResponseEntity<Void> deleteGardenerById(@PathVariable int gardenerId) {
        if(gardenerService.getGardenerById(gardenerId).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            gardenerService.deleteGardenerById(gardenerId);
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{gardenerId}")
    public ResponseEntity<Gardener> getGardenerById(@PathVariable int gardenerId) {
        Optional<Gardener> responseFromGardener = gardenerService.getGardenerById(gardenerId);
        if(responseFromGardener.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(responseFromGardener.get());
        }
    }

    @PostMapping
    public ResponseEntity<Gardener> postNewGardener(@RequestBody Gardener newGardener) {
        Optional<Gardener> savedGardener = gardenerService.postNewGardener(newGardener);
        if(savedGardener.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(savedGardener.get());
        }
    }

    @PutMapping("/completeUpdate/{gardenerId}")
    public ResponseEntity<String> completeGardenerEntityUpdate(@PathVariable Integer gardenerId, @RequestBody Gardener updatedGardener) {
        Optional<Gardener> gardenerToBeUpdated = gardenerService.completeGardenerEntityUpdated(gardenerId, updatedGardener);
        if(gardenerToBeUpdated.isPresent()) {
            return ResponseEntity.ok("Gardener updated successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //id gardener i greenhouse, PATCH i zwraca np ok
    //Patch, przyjmuje id szklarni i ogrodnika // endpoint URL 2 id /gardenerid/szkalrnia
    @PatchMapping("/greenhouseForGardener/{gardenerId}/setGreenhouse/{greenhouseId}")
    public ResponseEntity<String> setGreenhouseForGardener(@PathVariable Integer gardenerId, @PathVariable Integer greenhouseId) { //zmienna ze ścieżki, linijka 70
        Optional<Gardener> gardenerInGreenhouse = gardenerService.setGreenhouseForGardener(greenhouseId, gardenerId);
        if(gardenerInGreenhouse.isPresent()) {
            return ResponseEntity.ok("Greenhouse set for Gardener successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
