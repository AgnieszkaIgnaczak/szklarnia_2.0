package com.szklarnia.repository;

import com.szklarnia.model.GrowerCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrowerCompanyRepository extends JpaRepository<GrowerCompany, Integer> {
}
