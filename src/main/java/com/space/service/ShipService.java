package com.space.service;

import com.space.model.Ship;

import java.util.List;

public interface ShipService {
    Ship getById(long id);
    void save(Ship ship);
    Ship update(long id, Ship ship) throws IllegalArgumentException;
    void delete(Long id);
    List<Ship> getAll();
    long count();
}
