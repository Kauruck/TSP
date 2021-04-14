package com.kauruck.UI;

import com.kauruck.AS.Ant;
import com.kauruck.AS.Colony;
import com.kauruck.Backbone.City;
import com.kauruck.Backbone.Street;
import com.kauruck.TSP;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainPanel extends JPanel {



    @Override
    protected void paintComponent(Graphics g) {
        g.fillRect(0,0, this.getWidth(), this.getHeight());
        g.setColor(Color.GREEN);
        /*for(int i = 0; i < TSP.cities.size(); i++){
            City currentCity = TSP.cities.get(i);
            for(Street currentStreet : currentCity){
                if(currentStreet.getA() == currentCity)
                g.drawLine(currentCity.getX(), currentCity.getY(), currentStreet.getB().getX(), currentStreet.getB().getY());
            }
        }*/

        g.setColor(Color.red);
        for(int i = 0; i < TSP.cities.size(); i++){
            City current = TSP.cities.get(i);
            g.fillOval(current.getX() - TSP.CITYRADIUS/2, current.getY() - TSP.CITYRADIUS/2, TSP.CITYRADIUS, TSP.CITYRADIUS);
            g.drawString(current.getName(), current.getX() + TSP.CITYRADIUS, current.getY() + TSP.CITYRADIUS);
        }

        g.setColor(Color.BLUE);
        if(Colony.instance != null) {
            for (int i = 0; i < Colony.instance.ants.size(); i++) {
                Ant current = Colony.instance.ants.get(i);
                g.drawOval(current.getX() - TSP.ANTRADIUS / 2, current.getY() - TSP.ANTRADIUS / 2, TSP.ANTRADIUS, TSP.ANTRADIUS);
            }
        }

        g.setColor(Color.GREEN);
        if(Colony.instance != null && Colony.instance.bestWay != null) {
                List<City> way = Colony.instance.bestWay;
                for(int j = 0; j < way.size(); j ++){
                    City currentCity = way.get(j);
                    City next;
                    if(j < way.size() - 1){
                        next = way.get(j + 1);
                    }
                    else{
                        next = way.get(0);
                    }

                    g.drawLine(currentCity.getX(), currentCity.getY(), next.getX(), next.getY());
                }

            g.drawString("Best: " + Colony.instance.lengthOfWay(Colony.instance.bestWay), 20, 20);
        }




        g.setColor(Color.YELLOW);
        g.drawString("City names from https://simplemaps.com/data/world-cities as of 12.03.2021",0,this.getHeight() - 20);
    }
}
