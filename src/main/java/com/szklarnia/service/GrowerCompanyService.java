package com.szklarnia.service;

import com.szklarnia.model.Gardener;
import com.szklarnia.model.Greenhouse;
import com.szklarnia.model.GrowerCompany;
import com.szklarnia.model.Product;
import com.szklarnia.repository.GreenhouseRepository;
import com.szklarnia.repository.GrowerCompanyRepository;
import com.szklarnia.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class GrowerCompanyService {

    @Autowired
    private GrowerCompanyRepository growerCompanyRepository;

    @Autowired
    private GreenhouseRepository greenhouseRepository;

    @Autowired
    private ProductRepository productRepository;

    public Iterable<GrowerCompany> getAllGrowerCompanies() {
        return growerCompanyRepository.findAll();
    }

    public void deleteGrowerCompanyById(int growerCompanyId) { // => { growerCompanyRepository.deleteById(growerCompanyId) };

        Optional<GrowerCompany> growerCompanyToDelete = growerCompanyRepository.findById(growerCompanyId);
        if(growerCompanyToDelete.isPresent()) {
            for (Greenhouse greenhouse : growerCompanyToDelete.get().getGreenhouses()) {
                greenhouse.setGrowerCompany(null);
                greenhouseRepository.save(greenhouse);
            }
            growerCompanyRepository.deleteById(growerCompanyId);
        }
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

    //ustawić produkt dla company
    public Optional<GrowerCompany> setProductForGrowerCompany(Integer productId, Integer growerCompanyId) {
        if(productRepository.existsById(productId) && growerCompanyRepository.existsById(growerCompanyId)) {
            Product product = productRepository.findById(productId).get();
            GrowerCompany growerCompany = growerCompanyRepository.findById(growerCompanyId).get();
            growerCompany.addProduct(product);
            return Optional.of(growerCompanyRepository.save(growerCompany));
        }
        return Optional.empty();
    }
}
