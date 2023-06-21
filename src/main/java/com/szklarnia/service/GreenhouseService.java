package com.szklarnia.service;

import com.szklarnia.model.Gardener;
import com.szklarnia.model.Greenhouse;
import com.szklarnia.model.GrowerCompany;
import com.szklarnia.repository.GardenerRepository;
import com.szklarnia.repository.GreenhouseRepository;
import com.szklarnia.repository.GrowerCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GreenhouseService {

    @Autowired
    private GreenhouseRepository greenhouseRepository;

    @Autowired
    private GrowerCompanyRepository growerCompanyRepository;

    @Autowired
    private GardenerRepository gardenerRepository;

    //get all greenhouses
    public Iterable<Greenhouse> getAllGreenhouses() {
        return greenhouseRepository.findAll();
    }

    //delete greenhouse by ID => public void deleteGreenhouseById(int greenhouseId) {greenhouseRepository.deleteById(greenhouseId);}
    //odpięcie gardener
    public void deleteGreenhouseById(int greenhouseId) {
        Optional<Greenhouse> greenhouseToDelete = greenhouseRepository.findById(greenhouseId);
        if(greenhouseToDelete.isPresent()) {
            Gardener gardener = greenhouseToDelete.get().getGardener();
            gardener.setGreenhouse(null); //utrata połączenia
            gardenerRepository.save(gardener);
        }
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

    public Optional<Greenhouse> setGreenhousesForGrowerCompany(Integer greenhouseId, Integer growerCompanyId) {
        if(greenhouseRepository.existsById(greenhouseId) && growerCompanyRepository.existsById(growerCompanyId)) {
            Greenhouse greenhouse = greenhouseRepository.findById(greenhouseId).get();
            GrowerCompany growerCompany = growerCompanyRepository.findById(growerCompanyId).get();
            greenhouse.setGrowerCompany(growerCompany);
            return Optional.of(greenhouseRepository.save(greenhouse));
        }
        return Optional.empty();
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
