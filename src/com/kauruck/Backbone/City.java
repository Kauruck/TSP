package com.kauruck.Backbone;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class City implements  Iterable<Street>{

    private int x;
    private int y;

    private List<Street> streets = new ArrayList<>();

    private String name;

    public City(int x, int y) throws IOException {
        this.x = x;
        this.y = y;
        CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(new InputStreamReader(getClass().getResourceAsStream("names/worldcities.csv")));
        Random r = new Random();
        List<CSVRecord> records = parser.getRecords();
        int cIndex = r.nextInt(records.size());
        this.name = records.get(cIndex).get("city");
    }

    public City(int x, int y, String name){
        this.x = x;
        this.y = y;
        this.name = name;
    }

    @Override
    public String toString() {
        return "City{" +
                "x=" + x +
                ", y=" + y +
                ", name='" + name + '\'' +
                '}';
    }

    public Street getStreetTo(City other){
        Street out = streets.stream().filter(current -> current.getA() == other || current.getB() == other).findAny().orElse(null);
        return out;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public double distanceTo(City b){
        int dx = this.x - b.x;
        int dy = this.y - b.y;

        return Math.sqrt(dx * dx + dy * dy);
    }


    public double distanceTo(int x, int y){
        int dx = this.x - x;
        int dy = this.y - y;

        return Math.sqrt(dx * dx + dy * dy);
    }

    public void connectTo(City B){
        Street connection = new Street(this, B);
        this.streets.add(connection);
        B.streets.add(connection);
    }

    @Override
    public Iterator<Street> iterator() {
        return new CityIterator(streets);
    }

    public int numberOfConnectedStreets(){
        return streets.size();
    }



}
class CityIterator implements Iterator<Street>{

    int i = 0;
    List<Street> allStreets = new ArrayList<>();

    public CityIterator(List<Street> allStreets) {
        this.allStreets = allStreets;
    }

    @Override
    public boolean hasNext() {
        return i < allStreets.size() - 1;
    }

    @Override
    public Street next() {
        int tmpI = i;
        i++;
        return allStreets.get(tmpI);
    }
}

