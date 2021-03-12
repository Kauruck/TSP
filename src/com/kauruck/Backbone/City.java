package com.kauruck.Backbone;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;

public class City {

    private int x;
    private int y;

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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public double distance(City b){
        int dx = this.x - b.x;
        int dy = this.y - b.y;

        return Math.sqrt(dx * dx + dy * dy);
    }


    public double distance(int x, int y){
        int dx = this.x - x;
        int dy = this.y - y;

        return Math.sqrt(dx * dx + dy * dy);
    }
}
