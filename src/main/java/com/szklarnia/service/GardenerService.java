package com.szklarnia.service;

import com.szklarnia.exception_handler.ApiRequestException;
import com.szklarnia.model.Gardener;
import com.szklarnia.model.Greenhouse;
import com.szklarnia.repository.GardenerRepository;
import com.szklarnia.repository.GreenhouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GardenerService {

    @Autowired
    private GardenerRepository gardenerRepository;

    @Autowired
    private GreenhouseRepository greenhouseRepository;

    //GET ALL
    //bez wyjątku!!!
    public Iterable<Gardener> getAllGardeners() {
        return gardenerRepository.findAll();
    }

    //DELETE BY ID
    public void deleteGardenerById(int gardenerId) {
       if(gardenerRepository.existsById(gardenerId)) {
           gardenerRepository.deleteById(gardenerId);
       } else {
           throw new ApiRequestException("Cannot delete gardener with non-existing ID.");
       }
    }

    //GET BY ID
    public Gardener getGardenerById(int gardenerId) {
        Optional<Gardener> optionalGardener = gardenerRepository.findById(gardenerId);
        if(optionalGardener.isPresent()) {
            return optionalGardener.get();
        } else {
            throw new ApiRequestException("Cannot get gardener with non-existing ID.");
        }
    }

//    public Optional<Gardener> getGardenerById(int gardenerId) {
//        return gardenerRepository.findById(gardenerId);
//    }

    //POST
    public Gardener postNewGardener(Gardener newGardener) {
        //jeśli id nie jest null'em, bo ktoś podał id w Postmanie && jeśli istnieje już na bazie, to zwróć błąd, jeśli istnieje to true, jeśli nie false
        if(newGardener.getGardenerId() != null && gardenerRepository.existsById(newGardener.getGardenerId())) {
            throw new ApiRequestException("Cannot post gardener with existing ID.");
        }
        return gardenerRepository.save(newGardener);
    }

//    public Gardener saveNewGardener(Gardener gardener) {
//        return gardenerRepository.save(gardener);
//    }

    //PUT/UPDATE
    //(entire entity overwritten, wszystkie pola nadpisane w Postmannie, bo będą null'em)
    public Gardener completeGardenerEntityUpdated(Integer gardenerId, Gardener updateGardener) {
        if(gardenerRepository.existsById(gardenerId)) {
            updateGardener.setGardenerId(gardenerId);
            return gardenerRepository.save(updateGardener);
        }
        throw new ApiRequestException("Cannot update gardener with provided ID.");
    }

    //FIND BY NAME
    //custom method from repo
    //bez wyjątku, bo w przypadku braku imienia zwróci pustą listę
    public List<Gardener> findAllGardenersByName(String name) {
        return gardenerRepository.findAllByNameContaining(name);
    }


    //sprawdzam, czy istnieje taki ogrodnik i szklarnia po ich ID
    //jeśli tak, to dla obiektu gardener ustawić (set) szklarnię greenhouse
    //zapisać ogrodnika, żeby je złączyć
    //OneToOne
    //2 wyjątki
    public Gardener setGreenhouseForGardener(Integer greenhouseId, Integer gardenerId) {
        if(gardenerRepository.existsById(gardenerId)) {
            Gardener gardener = gardenerRepository.findById(gardenerId).get();
            if(greenhouseRepository.existsById(greenhouseId)) {
                Greenhouse greenhouse = greenhouseRepository.findById(greenhouseId).get();
                gardener.setGreenhouse(greenhouse);
                return gardenerRepository.save(gardener);
            } else {
                throw new ApiRequestException("Cannot set greenhouse for gardener because greenhouse ID does not exist.");
            }
        } else {
            throw new ApiRequestException("Cannot set greenhouse for gardener because gardener ID does not exist.");
        }
    }

}
