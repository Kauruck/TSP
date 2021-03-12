package com.kauruck;

import com.kauruck.Backbone.City;
import com.kauruck.UI.MainFrame;

import java.io.IOException;
import java.util.*;

public class TSP {

    public static final int CITYRADIUS = 20;

    public static MainFrame frame;
    public static Timer timer;

    public static List<City> cities = new ArrayList<>();

    public static void main(String[] args){
        timer = new Timer("Updater");
        frame = new MainFrame();
        timer.schedule(new UpdaterTask(), 100);
        try {
            generateCities(10);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(Arrays.toString(cities.toArray()));

    }

    public static class UpdaterTask extends TimerTask{

        @Override
        public void run() {
            timer.schedule(new UpdaterTask(), 100);
            TSP.frame.repaint();
        }
    }

    public static void generateCities(int amount) throws IOException {
        cities.clear();
        Random r = new Random();
        for(int i  = 0; i < amount; i++){
            int j = 0;
            int x = r.nextInt(frame.getWidth());
            int y = r.nextInt(frame.getHeight());
            while (!canBePlaced(x , y) && j < 10) {
                x = r.nextInt(frame.getWidth());
                y = r.nextInt(frame.getHeight());
                j++;
            }
            cities.add(new City(x,y));
        }
    }

    public static boolean canBePlaced(City what){
        boolean canBePlaced = true;
        for (int i = 0; i < cities.size(); i++) {
            City current = cities.get(i);
            if(current.distance(what) <= CITYRADIUS * 3)
                canBePlaced = false;

        }

        return canBePlaced;
    }

    public static boolean canBePlaced(int x, int y){
        boolean canBePlaced = true;
        for (int i = 0; i < cities.size(); i++) {
            City current = cities.get(i);
            if(current.distance(x, y) <= CITYRADIUS * 3)
                canBePlaced = false;

        }

        return canBePlaced;
    }
}
