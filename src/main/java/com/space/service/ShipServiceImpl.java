package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.Date;
import java.util.List;
import java.util.Objects;


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
    public Ship save(Ship ship) {
        return shipRepository.save(ship);
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
    public void delete(Long id) throws IllegalArgumentException {
        try {
            shipRepository.deleteById(id);
        }
        catch(Exception e) {
            throw new ShipNotFoundException();
        }
    }


    public Specification<Ship> getSpec(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed,
                             Double minSpeed, Double maxSpeed, Integer minCrewSize, Integer maxCrewSize,
                             Double minRating, Double maxRating, ShipOrder order)
    {
        return (Specification<Ship>) (root, cq, cb) -> {
            Predicate p = cb.conjunction();
            if (Objects.nonNull(name)) {
                p = cb.and(p, cb.like(root.get("name"), '%' + name + '%'));
            }

            if (Objects.nonNull(planet)) {
                p = cb.and(p, cb.like(root.get("planet"), '%' + planet + '%'));
            }

            if (Objects.nonNull(shipType)) {
                p = cb.and(p, cb.equal(root.get("shipType"), shipType));
            }

            if (Objects.nonNull(after)) {
                p = cb.and(p, cb.greaterThanOrEqualTo(root.get("prodDate"), new Date(after)));
            }

            if (Objects.nonNull(before)) {
                p = cb.and(p, cb.lessThanOrEqualTo(root.get("prodDate"), new Date(before)));
            }

            if (Objects.nonNull(isUsed)) {
                p = cb.and(p, cb.equal(root.get("isUsed"), isUsed));
            }

            if (Objects.nonNull(minSpeed)) {
                p = cb.and(p, cb.greaterThanOrEqualTo(root.get("speed"), minSpeed));
            }

            if (Objects.nonNull(maxSpeed)) {
                p = cb.and(p, cb.lessThanOrEqualTo(root.get("speed"), maxSpeed));
            }

            if (Objects.nonNull(minCrewSize)) {
                p = cb.and(p, cb.greaterThanOrEqualTo(root.get("crewSize"), minCrewSize));
            }

            if (Objects.nonNull(maxCrewSize)) {
                p = cb.and(p, cb.lessThanOrEqualTo(root.get("crewSize"), maxCrewSize));
            }

            if (Objects.nonNull(minRating)) {
                p = cb.and(p, cb.greaterThanOrEqualTo(root.get("rating"), minRating));
            }

            if (Objects.nonNull(maxRating)) {
                p = cb.and(p, cb.lessThanOrEqualTo(root.get("rating"), maxRating));
            }

            if  (Objects.nonNull(order)) {
                cq.orderBy(cb.asc(root.get(order.getFieldName())));
            }
            return p;
        };
    }

    @Override
    public List<Ship> getList(Specification<Ship> spec, Integer pageNum, Integer pageSize)
    {

        if (pageNum == null) {
            pageNum = 0;
        }

        if (pageSize == null) {
            pageSize = 3;
        }

        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return shipRepository.findAll(spec, pageable).getContent();
    }

    @Override
    public long count(Specification<Ship> spec)
    {
        return shipRepository.count(spec);
    }

    public static class ShipNotFoundException extends IllegalArgumentException {
    }

    public static class DataNotValidException extends IllegalArgumentException {
    }

}
