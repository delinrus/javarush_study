package com.space.service;

import com.space.model.Ship;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Service
public class ShipServiceImpl implements ShipService {

    private final ShipRepository shipRepository;

    @Autowired
    public ShipServiceImpl(ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }

    @Override
    public Ship getById(long id) {
        return shipRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Ship ship) {
        shipRepository.save(ship);
    }

    @Override
    public Ship update(long id, Ship ship) throws IllegalArgumentException {
        ship.setId(id);
        Ship oldShip = getById(id);
        if (oldShip == null) {
            throw new ShipNotFoundException();
        }
        oldShip.update(ship);
        oldShip.updateRating();
        if (oldShip.isCorrect()) {
            shipRepository.save(oldShip);
            return oldShip;
        }
        else {
            throw new DataNotValidException();
        }
    }

    @Override
    public void delete(Long id) {
        shipRepository.deleteById(id);
    }

    @Override
    public List<Ship> getAll() {
        return shipRepository.findAll();
    }

    @Override
    public long count() {

        return shipRepository.count();

    }

    public static class ShipNotFoundException extends IllegalArgumentException {
    }

    public static class DataNotValidException extends IllegalArgumentException {
    }

}
