package com.Programming;

import java.io.*;

public class Map {

    public int width, height;
    public String mapData;

    public Map(String path) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            System.out.println("File: " + path + " doesn't exist!");
        }

        try {
            String line;
            while((line = in.readLine()) != null) {
                mapData.concat(line);
                width = line.length();
                height++;
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
