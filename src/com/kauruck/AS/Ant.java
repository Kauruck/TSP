package com.kauruck.AS;

import com.kauruck.Backbone.City;
import com.kauruck.Backbone.Street;
import com.kauruck.TSP;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ant {

    private City currentCity;
    private City oldCity;

    private int x;
    private int y;

    private int currentSpeed = 1;

    private List<City> visited = new ArrayList<>();

    private int moveIteration = 1;

    public Ant(City startCity){
        currentCity = startCity;
        visited.add(currentCity);
        x = startCity.getX();
        y = startCity.getY();
        oldCity = currentCity;
    }


    private double P(Street street){
        if((visited.contains(street.getA()) && street.getA() != currentCity) || (visited.contains(street.getB()) && street.getB() != currentCity))
            return 0;
        return (street.getPheromone() * Colony.ALPHA) * (1/(street.length()) * Colony.BETA);
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
        if(oldCity != currentCity)
            return;
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
        System.out.println("Moved to: "  + currentCity);
    }

    public City getCurrentCity() {
        return currentCity;
    }



    public void renderMove(){
        if(oldCity != currentCity){

            double k = (oldCity.distanceTo(currentCity)/ TSP.STEPS)/oldCity.distanceTo(currentCity);
            x = (int) (k * currentCity.getX() + (1 - k) * oldCity.getX());
            y = (int) (k * currentCity.getY() + (1 - k) * oldCity.getY());

            /*double dx = currentCity.getX() - oldCity.getX();
            double dy = currentCity.getY() - oldCity.getY();
            double xs = 5/dx;
            double ys = 5/dy;

            x += xs;
            y += ys;*/

            moveIteration++;

            if(moveIteration >= TSP.STEPS) {
                oldCity = currentCity;
                moveIteration = 1;
            }


        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
