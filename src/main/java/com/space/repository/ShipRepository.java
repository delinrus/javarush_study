package com.space.repository;

import com.space.model.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ShipRepository extends JpaRepository<Ship, Long> {
    // List<Ship> findByName(String name);
}
