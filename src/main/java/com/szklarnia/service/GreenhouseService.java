package com.szklarnia.service;

import com.szklarnia.model.Greenhouse;
import com.szklarnia.repository.GreenhouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GreenhouseService {

    @Autowired
    private GreenhouseRepository greenhouseRepository;

    //get all greenhouses
    public Iterable<Greenhouse> getAllGreenhouses() {
        return greenhouseRepository.findAll();
    }

    //delete greenhouse by ID
    public void deleteGreenhouseById(int greenhouseId) {
        greenhouseRepository.deleteById(greenhouseId);
    }

    //get greenhouse by ID
    public Optional<Greenhouse> getGreenhouseById(int greenhouseId) {
        return greenhouseRepository.findById(greenhouseId);
    }

    //save an entity/post new entity to DB
    public Optional<Greenhouse> postNewGreenhouse(Greenhouse newGreenhouse) {
        if(newGreenhouse.getGreenhouseId() != null && greenhouseRepository.existsById(newGreenhouse.getGreenhouseId())) {
            return Optional.empty();
        }
        return Optional.of(greenhouseRepository.save(newGreenhouse));
    }

    //put, cała encja nadpisana
    public Optional<Greenhouse> completeGreenhouseEntityUpdate(Integer greenhouseId, Greenhouse updateGreenhouse) {

        if(greenhouseRepository.existsById(greenhouseId)) { //existById z CrudRepository
            updateGreenhouse.setGreenhouseId(greenhouseId); //napisanie starego ID
            return Optional.of(greenhouseRepository.save(updateGreenhouse)); //save z CrudRepository
        }
        return Optional.empty(); //by nie zwracał nulla
    }

//    public Greenhouse partialGreenhouseEntityUpdate(Integer greenhouseId, Map<String, Object> greenhouseFields) {
//        Optional <Greenhouse> existingGreenhouse = Optional.of(greenhouseRepository.findById(greenhouseId).get());
//
//        if(existingGreenhouse.isPresent()) {
//            greenhouseFields.forEach((key, value) -> {
//                Field field = ReflectionUtils.findField(Greenhouse.class, key);
//                field.setAccessible(true); //access to modify the object
//                ReflectionUtils.setField(field, existingGreenhouse.get(), value);
//            });
//
//            return greenhouseRepository.save(existingGreenhouse.get());
//        }
//        return null;
//    }
}
