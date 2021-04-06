package com.kauruck.AS;

import com.kauruck.Backbone.City;
import com.kauruck.Backbone.Street;
import com.kauruck.TSP;

import java.util.ArrayList;
import java.util.List;

public class Colony {

    static final double ALPHA = 1;
    static final double BETA = 5;
    static final double RHO = 0.1f;
    static final double Q = 1;

    public List<Ant> ants = new ArrayList<>();

    public static Colony instance;

    public List<Street> walkedStreets = new ArrayList<>();

    public Colony(){
        /*for(City current : TSP.cities){
            ants.add(new Ant(current));
        }*/

        ants.add(new Ant(TSP.cities.get(0)));

        instance = this;
    }

    public void round(){
        for(int i = 0; i < ants.size(); i++){
            Ant current = ants.get(i);
            boolean res = current.move();
            if(!res)
                ants.remove(current);
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

    public void renderUpdate(){
        for(Ant current : ants){
            current.renderMove();
        }
    }
}
