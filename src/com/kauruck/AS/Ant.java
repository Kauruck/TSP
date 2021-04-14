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

    private List<City> visited = new ArrayList<>();

    private int moveIteration = 1;

    public Ant(City startCity){
        currentCity = startCity;
        visited.add(currentCity);
        x = startCity.getX();
        y = startCity.getY();
        oldCity = currentCity;
        System.out.println("Starting from: " + currentCity);
    }


    private double P(Street street){
        if((visited.contains(street.getA()) && street.getA() != currentCity) || (visited.contains(street.getB()) && street.getB() != currentCity))
            return 0;
        return (street.getPheromone() * Colony.ALPHA) * (1/(street.length()) * Colony.BETA);
    }

    public List<City> getVisited() {
        return visited;
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
        for (City current : TSP.cities){
            if(current == currentCity)
                continue;
            if (total >= gate && !visited.contains(current))
                return current.getStreetTo(currentCity);
            total += p(current.getStreetTo(currentCity));
        }
        City missing = getFirstMissing();
        if(missing == null)
            return null;
        return getFirstMissing().getStreetTo(currentCity);
    }

    private City getFirstMissing(){
        for(City current : TSP.cities){
            if(!visited.contains(current) && current != currentCity)
                return current;
        }
        return null;
    }



    public boolean move(){
        if(oldCity != currentCity)
            return true;

        if(visited.size() == TSP.cities.size()) {
            System.out.println("End: " + currentCity);
            visited.add(currentCity);
            return false;
        }
        int i = 0;
        City target = null;
        Street toMove = null;
        do {
            toMove = selectStreet();
            if(toMove == null)
                continue;
            target = toMove.getA() == currentCity ? toMove.getB() : toMove.getA();
            i++;
        }while (visited.contains(target) && i < TSP.SEARCHCAP);
        if(toMove == null) {
            System.out.println("End: " + currentCity);
            visited.add(currentCity);
            return false;
        }
        if(!visited.contains(currentCity))
            visited.add(currentCity);
        oldCity = currentCity;
        currentCity = target;
        Colony.instance.walkedStreets.add(toMove);
        System.out.println("Moved to: "  + currentCity + "(Visited: "  + visited.size() + " cities)");
        return true;
    }

    public City getCurrentCity() {
        return currentCity;
    }



    public void renderMove(){
        if(oldCity != currentCity){

            double k = (oldCity.distanceTo(currentCity)/ TSP.STEPS)/oldCity.distanceTo(currentCity) * moveIteration;
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
