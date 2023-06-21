package com.szklarnia.service;

import com.szklarnia.model.Gardener;
import com.szklarnia.model.Greenhouse;
import com.szklarnia.model.GrowerCompany;
import com.szklarnia.repository.GreenhouseRepository;
import com.szklarnia.repository.GrowerCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GrowerCompanyService {

    @Autowired
    private GrowerCompanyRepository growerCompanyRepository;

    @Autowired
    private GreenhouseRepository greenhouseRepository;

    public Iterable<GrowerCompany> getAllGrowerCompanies() {
        return growerCompanyRepository.findAll();
    }

    public void deleteGrowerCompanyById(int growerCompanyId) {
        growerCompanyRepository.deleteById(growerCompanyId);
    }

    public Optional<GrowerCompany> getGrowerCompanyById(int growerCompanyId) {
        return growerCompanyRepository.findById(growerCompanyId);
    }

    public Optional<GrowerCompany> postNewGrowerCompany(GrowerCompany newGrowerCompany) {
        if(newGrowerCompany.getCompanyId() != null && growerCompanyRepository.existsById(newGrowerCompany.getCompanyId())) {
            return Optional.empty();
        }
        return Optional.of(growerCompanyRepository.save(newGrowerCompany));
    }

    public Optional<GrowerCompany> completeGrowerCompanyEntityUpdate(Integer growerCompanyId, GrowerCompany updateGrowerCompany) {
        if(growerCompanyRepository.existsById(growerCompanyId)) {
            updateGrowerCompany.setCompanyId(growerCompanyId);
            return Optional.of(growerCompanyRepository.save(updateGrowerCompany));
        }
        return Optional.empty();
    }

}
