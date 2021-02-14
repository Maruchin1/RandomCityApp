package com.example.randomcityapp.core.logic;

import com.example.randomcityapp.core.models.RandomCity;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class RandomCityGenerator {

    private final ArrayList<String> cities = new ArrayList<>();
    private final ArrayList<String> colors = new ArrayList<>();

    private final SystemUtil systemUtil;

    public RandomCityGenerator(SystemUtil systemUtil) {
        this.systemUtil = systemUtil;

        cities.add("Gdańsk");
        cities.add("Warszawa");
        cities.add("Poznań");
        cities.add("Białystok");
        cities.add("Wrocław");
        cities.add("Katowice");
        cities.add("Kraków");

        colors.add("Yellow");
        colors.add("Green");
        colors.add("Blue");
        colors.add("Red");
        colors.add("Black");
        colors.add("White");
    }

    public RandomCity getRandomCity() {
        return new RandomCity(getRandomName(), getRandomColor(), getEmissionTime());
    }

    private String getRandomName() {
        int randomIndex = systemUtil.getRandomInt(cities.size());
        return cities.get(randomIndex);
    }

    private String getRandomColor() {
        int randomIndex = systemUtil.getRandomInt(colors.size());
        return colors.get(randomIndex);
    }

    private LocalDateTime getEmissionTime() {
        return systemUtil.getCurrentDateTime();
    }
}
