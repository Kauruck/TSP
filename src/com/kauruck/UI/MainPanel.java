package com.kauruck.UI;

import com.kauruck.Backbone.City;
import com.kauruck.TSP;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {



    @Override
    protected void paintComponent(Graphics g) {
        g.fillRect(0,0, this.getWidth(), this.getHeight());
        g.setColor(Color.RED);
        for(int i = 0; i < TSP.cities.size(); i++){
            City current = TSP.cities.get(i);
            g.fillOval(current.getX() - TSP.CITYRADIUS/2, current.getY() - TSP.CITYRADIUS/2, TSP.CITYRADIUS, TSP.CITYRADIUS);
            g.drawString(current.getName(), current.getX() + TSP.CITYRADIUS, current.getY() + TSP.CITYRADIUS);
        }

        g.drawString("City names from https://simplemaps.com/data/world-cities as of 12.03.2021",0,this.getHeight() - 20);
    }
}
