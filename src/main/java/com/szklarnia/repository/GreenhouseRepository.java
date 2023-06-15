package com.szklarnia.repository;


import com.szklarnia.model.Greenhouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GreenhouseRepository extends JpaRepository<Greenhouse, Integer> {



}
