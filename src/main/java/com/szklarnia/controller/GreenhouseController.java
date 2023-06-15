package com.szklarnia.controller;

import com.szklarnia.model.Greenhouse;
import com.szklarnia.service.GreenhouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/greenhouse")
public class GreenhouseController {

    private GreenhouseService greenhouseService;

    @Autowired
    public GreenhouseController(GreenhouseService greenhouseService) {
        this.greenhouseService = greenhouseService;
    }

    //metoda nazywa się tak samo, jak w serwisie
    //get all greenhouses
    @GetMapping
    public Iterable<Greenhouse> getAllGreenhouses() {
        return greenhouseService.getAllGreenhouses();
    }

    //delete by ID
    @DeleteMapping("/{greenhouseId}")
    public ResponseEntity<Void> deleteGreenhouseById(@PathVariable int greenhouseId) {
        if(greenhouseService.getGreenhouseById(greenhouseId).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            greenhouseService.deleteGreenhouseById(greenhouseId);
            return ResponseEntity.noContent().build();
        }
    }

    //get by ID
    @GetMapping("/{greenhouseId}")
    public ResponseEntity<Greenhouse> getGreenhouseById(@PathVariable int greenhouseId) {
        Optional<Greenhouse> responseFromGreenhouse = greenhouseService.getGreenhouseById(greenhouseId);
        if(responseFromGreenhouse.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(responseFromGreenhouse.get());
        }
    }

    //post/create new entity (nie podawać ID w postmanie, bo jest ustawiony autoincrement w My SQL Workbench
    @PostMapping
    public ResponseEntity<Greenhouse> postNewGreenhouse(@RequestBody Greenhouse newGreenhouse) {
        Optional <Greenhouse> savedGreenhouse = greenhouseService.postNewGreenhouse(newGreenhouse);
        //by nie można było nadpisać ID
        if(savedGreenhouse.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // dla nulla
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(savedGreenhouse.get());
        }
    }

    @PutMapping("/completeUpdate/{greenhouseId}")
    public ResponseEntity<String> completeGreenhouseEntityUpdate(@PathVariable Integer greenhouseId, @RequestBody Greenhouse updatedGreenhouse) {
        Optional<Greenhouse> greenhouseToBeUpdated = greenhouseService.completeGreenhouseEntityUpdate(greenhouseId, updatedGreenhouse);
        if(greenhouseToBeUpdated.isPresent()) {
            return ResponseEntity.ok("Greenhouse updated successfully!"); //dlatego ResponseEntity przyjmuje typ String
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @PatchMapping("/partialUpdate/{greenhouseId}")
//    public Greenhouse partialGreenhouseEntityUpdate(@PathVariable Integer greenhouseId, @RequestBody Map<String, Object> greenhouseFields) { //drugi argument to tylko to, co chcemy zupdatować
//        return greenhouseService.partialGreenhouseEntityUpdate(greenhouseId, greenhouseFields);
//    }

}
