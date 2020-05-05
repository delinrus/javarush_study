package com.space.service;

import com.space.model.Ship;

import java.util.List;

public interface ShipService {
    Ship getById(long id);
    void save(Ship ship);
    void delete(Long id);
    List<Ship> getAll();
}
