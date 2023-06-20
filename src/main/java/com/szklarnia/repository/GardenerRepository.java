package com.szklarnia.repository;

import com.szklarnia.model.Gardener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GardenerRepository extends JpaRepository<Gardener, Integer> {

    List<Gardener> findAllByNameContaining(String name);

}
