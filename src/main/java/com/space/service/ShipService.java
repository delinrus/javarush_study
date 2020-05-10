package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ShipService {
    Ship getById(long id);
    Ship save(Ship ship);
    Ship update(long id, Ship ship) throws IllegalArgumentException;
    void delete(Long id)  throws IllegalArgumentException ;
    List<Ship> getList(Specification<Ship> spec, Integer pageNum, Integer pageSize);
    long count(Specification<Ship> spec);
    Specification<Ship> getSpec(String name, String planet, ShipType shipType, Long after, Long before,
                                       Boolean isUsed, Double minSpeed, Double maxSpeed, Integer minCrewSize,
                                       Integer maxCrewSize, Double minRating, Double maxRating, ShipOrder order);

}
