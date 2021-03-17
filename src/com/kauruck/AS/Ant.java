package com.kauruck.AS;

import com.kauruck.Backbone.City;
import com.kauruck.Backbone.Street;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ant {

    private City currentCity;
    private City oldCity = null;

    private int x;
    private int y;

    private List<City> visited = new ArrayList<>();

    private boolean inTransition;

    public Ant(City startCity){
        currentCity = startCity;
        visited.add(currentCity);
        x = startCity.getX();
        y = startCity.getY();
    }


    private double P(Street street){
        return (street.getPheromone() * Colony.ALPHA) * ((1/street.length()) * Colony.BETA);
    }

    private double sum(){
        double out = 0;
        for(Street current : currentCity){
            out += P(current);
        }
        return out;
    }

    private double p(Street street){
        return P(street)/sum();
    }

    private Street selectStreet(){
        Random r = new Random();
        double gate = r.nextDouble();
        double total = 0;
        for (Street current : currentCity){
            if(total >= gate)
                return current;
            total += p(current);
        }
        return null;
    }

    public void move(){
        Street toMove = selectStreet();
        if(toMove == null)
            return;
        visited.add(currentCity);
        oldCity = currentCity;
        if(currentCity == toMove.getA()) {
            currentCity = toMove.getB();
        }
        else {
            currentCity = toMove.getA();
        }
        Colony.instance.walkedStreets.add(toMove);
    }

    public City getCurrentCity() {
        return currentCity;
    }

    public void renderMove(){
        if(oldCity != currentCity){
            double s = oldCity.distanceTo(currentCity) / 5;
        }
    }
}
