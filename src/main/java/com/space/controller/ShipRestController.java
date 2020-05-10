package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import com.space.service.ShipServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/ships")
public class ShipRestController {
    private final ShipService shipService;

    @Autowired
    public ShipRestController(ShipService shipService) {
        this.shipService = shipService;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Ship> getShip(@PathVariable("id") Long shipId) {
        if(shipId == 0) {
            return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
        }
        Ship ship = shipService.getById(shipId);
        if (ship == null) {
            return new ResponseEntity<> (HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ship, HttpStatus.OK);
    }

    @RequestMapping(path = "/count", method = RequestMethod.GET)
    public ResponseEntity<Integer> getShipsCount(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "planet", required = false) String planet,
            @RequestParam(value = "shipType", required = false) ShipType shipType,
            @RequestParam(value = "after", required = false) Long after,
            @RequestParam(value = "before", required = false) Long before,
            @RequestParam(value = "isUsed", required = false) Boolean isUsed,
            @RequestParam(value = "minSpeed", required = false) Double minSpeed,
            @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
            @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
            @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
            @RequestParam(value = "minRating", required = false) Double minRating,
            @RequestParam(value = "maxRating", required = false) Double maxRating
    ) {

        Integer count = (int) shipService.count(name, planet, shipType, after, before, isUsed,
                minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    public ResponseEntity<Ship> updateShip(@PathVariable("id") Long shipId, @RequestBody Ship ship) {
        if (ship == null || shipId == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Ship result = shipService.update(shipId, ship);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (ShipServiceImpl.ShipNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (ShipServiceImpl.DataNotValidException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Ship> deleteShip(@PathVariable("id") Long shipId) {
        if(shipId <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            shipService.delete(shipId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (ShipServiceImpl.ShipNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Ship> createShip(@RequestBody Ship ship) {
        if (ship.getUsed() == null) {
            ship.setUsed(false);
        }

        if (!ship.isCorrect()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ship.updateRating();
        Ship result = shipService.save(ship);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
