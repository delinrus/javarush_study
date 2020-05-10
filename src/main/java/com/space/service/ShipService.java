package com.space.service;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

public interface ShipService {
    Ship getById(long id);
    Ship save(Ship ship);
    Ship update(long id, Ship ship) throws IllegalArgumentException;
    void delete(Long id)  throws IllegalArgumentException ;
    List<Ship> getAll();
    long count(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed,
               Double minSpeed, Double maxSpeed, Integer minCrewSize, Integer maxCrewSize,
               Double minRating, Double maxRating);

}
