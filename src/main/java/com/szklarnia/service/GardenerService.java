package com.szklarnia.service;

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
    public void deleteGardenerById(int gardenerId) {
       gardenerRepository.deleteById(gardenerId);
    }

    //get gardener by ID
    public Optional<Gardener> getGardenerById(int gardenerId) {
        return gardenerRepository.findById(gardenerId);
    }

    //save an entity/post new entity to DB
    public Optional<Gardener> postNewGardener(Gardener newGardener) {
        if(newGardener.getGardenerId() != null && gardenerRepository.existsById(newGardener.getGardenerId())) {
            return Optional.empty();
        }
        return Optional.of(gardenerRepository.save(newGardener));
    }

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
