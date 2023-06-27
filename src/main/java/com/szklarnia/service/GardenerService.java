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

    //get all gardeners
    public Iterable<Gardener> getAllGardeners() {
        return gardenerRepository.findAll();
    }

    //delete gardener by ID
    //wyjątek!!!!
    public void deleteGardenerById(int gardenerId) {
       if(gardenerRepository.existsById(gardenerId)) {
           gardenerRepository.deleteById(gardenerId);
       } else {
           throw new ApiRequestException("Cannot delete non-existing gardener.");
       }
    }

    //get gardener by ID
    public Optional<Gardener> getGardenerById(int gardenerId) {
        return gardenerRepository.findById(gardenerId);
    }

    //save an entity/post new entity to DB
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

    //put, entire entity overwritten, wszystkie pola nadpisane w Postmannie, bo będą nulle
    public Optional<Gardener> completeGardenerEntityUpdated(Integer gardenerId, Gardener updateGardener) {
        if(gardenerRepository.existsById(gardenerId)) {
            updateGardener.setGardenerId(gardenerId);
            return Optional.of(gardenerRepository.save(updateGardener));
        }
        return Optional.empty();
    }

    //znajdź ogrodników po imieniu
    public List<Gardener> findAllGardenersByName(String name) {
        return gardenerRepository.findAllByNameContaining(name);
    }


    //w serwisie sprawdzam, czy istnieje taki ogrodnik i szklarnia po ich ID
    //jeśli tak, to dla obiektu ogrodnika gardener ustawić szklarnię setSzklarnia, który przyjmuje szklarnię greenhouse, i zapisać ogrodnika, żeby je złączyć
    //OneToOne
    public Optional<Gardener> setGreenhouseForGardener(Integer greenhouseId, Integer gardenerId) {
        if(gardenerRepository.existsById(gardenerId) && greenhouseRepository.existsById(greenhouseId)) {
            Gardener gardener = gardenerRepository.findById(gardenerId).get();
            Greenhouse greenhouse = greenhouseRepository.findById(greenhouseId).get();
            gardener.setGreenhouse(greenhouse);
            return Optional.of(gardenerRepository.save(gardener));
        }
        return Optional.empty();
    }
}
