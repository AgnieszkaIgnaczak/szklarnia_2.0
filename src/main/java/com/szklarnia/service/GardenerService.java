package com.szklarnia.service;

import com.szklarnia.model.Gardener;
import com.szklarnia.repository.GardenerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GardenerService {

    @Autowired
    private GardenerRepository gardenerRepository;

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

    //put, entire entity overwritten
    public Optional<Gardener> completeGardenerEntityUpdated(Integer gardenerId, Gardener updateGardener) {
        if(gardenerRepository.existsById(gardenerId)) {
            updateGardener.setGardenerId(gardenerId);
            return Optional.of(gardenerRepository.save(updateGardener));
        }
        return Optional.empty();
    }
}
