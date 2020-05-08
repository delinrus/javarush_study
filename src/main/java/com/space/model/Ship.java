package com.space.model;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Entity
@Table(name = "ship")
public class Ship {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String planet;
    @Enumerated(EnumType.STRING)
    private ShipType shipType;
    private Date prodDate;
    private Boolean isUsed;
    private Double speed;
    private Integer crewSize;
    private Double rating;

    @Override
    public String toString() {
        return String.format("Ship [id=%d, name=%s, planet=%s, shipType=%s, prodDate=%s," +
                        " isUsed=%b, speed=%f, crewSize=%d, rating=%.2f] ",
                id, name, planet, shipType, new SimpleDateFormat("dd.MM.yyyy").format(prodDate),
                isUsed, speed, crewSize, rating );
    }


    public static void main(String[] args) {
        Ship ship = new Ship(3L, "Enterprise", "Earth", ShipType.MILITARY,
                new Date(), true, 124.0, 34, 9.0);

        System.out.println(ship);
    }

    protected Ship() {

    }

    private int getProdYear() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(prodDate);
        return calendar.get(Calendar.YEAR);
    }

    public void updateRating() {
        double NEW_SHIP_WEIGHT = 1.0;
        double OLD_SHIP_WEIGHT = 0.5;
        rating = 80 * speed * (isUsed? OLD_SHIP_WEIGHT : NEW_SHIP_WEIGHT) / (3019 - getProdYear() + 1);
        rating =  Math.round(rating * 100) / 100.0;
    }

    public boolean isEmptyBody() {
        return name == null && planet == null && shipType == null && prodDate == null &&
                isUsed == null && speed == null && crewSize == null;
    }

    public void update(Ship ship) {
        if (ship.name != null) {
            name = ship.name;
        }

        if (ship.planet != null) {
            planet = ship.planet;
        }

        if (ship.shipType != null) {
            shipType = ship.shipType;
        }

        if (ship.prodDate != null) {
            prodDate = ship.prodDate;
        }

        if (ship.isUsed != null) {
            isUsed = ship.isUsed;
        }

        if (ship.speed != null) {
            speed = ship.speed;
        }

        if (ship.crewSize != null) {
            crewSize = ship.crewSize;
        }
    }


    public boolean isCorrect() {
        if (crewSize < 1 || crewSize > 9999) {
            return false;
        }
        if (getProdYear() < 2800 || getProdYear() > 3019) {
            return false;
        }
        // > 50 chars
        return name.length() != 0;
    }

    public Ship(
            Long id,
            String name,
            String planet,
            ShipType shipType,
            Date prodDate,
            Boolean isUsed,
            Double speed,
            Integer crewSize,
            Double rating
    )
    {
        this.id = id;
        this.name = name;
        this.planet = planet;
        this.shipType = shipType;
        this.prodDate = prodDate;
        this.isUsed = isUsed;
        this.speed = speed;
        this.crewSize = crewSize;
        this.rating = rating;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Integer getCrewSize() {
        return crewSize;
    }

    public void setCrewSize(Integer crewSize) {
        this.crewSize = crewSize;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

}
