package com.space.service;

import com.space.config.AppConfig;
import com.space.model.Ship;
import org.apache.log4j.BasicConfigurator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class TestApp {

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "prod");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        ShipServiceImpl shipService = context.getBean(ShipServiceImpl.class);

        System.out.println("Jpa test");
        System.out.println("Элементов в таблице " + shipService.getAll().size());

        Ship ship = shipService.getById(14);
        System.out.println(ship + "\n");

        for (Ship item: shipService.getAll()) {
            System.out.println(item);
        }

    }
}
