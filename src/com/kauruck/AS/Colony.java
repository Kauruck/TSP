package com.kauruck.AS;

import com.kauruck.Backbone.City;
import com.kauruck.Backbone.Street;
import com.kauruck.TSP;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Colony {

    static final double ALPHA = 1;
    static final double BETA = 5;
    static final double RHO = 0.1f;
    static final double Q = 1;

    public List<Ant> ants = new ArrayList<>();
    public List<Ant> finishedAnts = new ArrayList<>();

    public static Colony instance;

    public List<Street> walkedStreets = new ArrayList<>();

    public List<City> bestWay = null;

    public Colony(){

        ants.add(new Ant(TSP.cities.get(0)));

        instance = this;
    }

    public void round(){
        for(int i = 0; i < ants.size(); i++){
            Ant current = ants.get(i);
            boolean res = current.move();
            if(!res) {
                ants.remove(current);
                finishedAnts.add(current);
                Random r = new Random();
                int j = r.nextInt(TSP.cities.size());
                ants.add(new Ant(TSP.cities.get(j)));
                bestWay(current);
            }
        }

        for(City currentCity : TSP.cities){
            for(Street currentStreet :  currentCity){
                currentStreet.setPheromone(currentStreet.getPheromone() * (1 - RHO));
            }
        }

        for(Street currentStreet : walkedStreets){
            currentStreet.setPheromone(currentStreet.getPheromone() + Q/ currentStreet.length());
        }
    }

    private void bestWay(Ant ant){
        if(bestWay == null) {
            bestWay = ant.getVisited();
            refreshPheromoneBestWay();
        }
        else {
            if(lengthOfWay(bestWay) > lengthOfWay(ant.getVisited())){
                bestWay = ant.getVisited();
                refreshPheromoneBestWay();
            }
        }
    }

    public double lengthOfWay(List<City> way){
        double out = 0;
        int i = 0;
        for(City current : way){
            City next;
            if(i < bestWay.size() - 1){
                next = bestWay.get(i + 1);
            }
            else{
                next = bestWay.get(0);
            }
            Street toUpdate = current.getStreetTo(next);
            out += toUpdate.length();
        }
        return out;
    }



    private void refreshPheromoneBestWay(){
        int i = 0;
        for(City current : bestWay){
            City next;
            if(i < bestWay.size() - 1){
                next = bestWay.get(i + 1);
            }
            else{
                next = bestWay.get(0);
            }
            Street toUpdate = current.getStreetTo(next);
            //TODO: Variable for benefit
            toUpdate.setPheromone(toUpdate.getPheromone() + 5);
            i++;
        }
    }

    public void renderUpdate(){
        for(Ant current : ants){
            current.renderMove();
        }
    }
}
