package com.kauruck.Backbone;

public class Street {
    private final City A;
    private final City B;
    private double pheromone = 1;

    public Street(City a, City b) {
        A = a;
        B = b;
    }

    public City getA() {
        return A;
    }

    public City getB() {
        return B;
    }

    public double length(){
        return A.distanceTo(B);
    }

    public double getPheromone() {
        return pheromone;
    }

    public void setPheromone(double pheromone) {
        this.pheromone = pheromone;
    }
}
