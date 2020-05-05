package com.space.controller;

import com.space.model.Ship;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

}
