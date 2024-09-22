package org.example;

import ManipularCsv.ManipularCSV;
import ManipularCsv.MyEntity;

import java.util.List;

public class Main {

    static String  ARCHIVEROUTE ="C:\\Users\\user\\Documents\\Proyectos Intellij\\Proyecto ED\\GestionarTraspasos\\src\\main\\java\\CSV\\Libro1.csv";

    public static void main(String[] args) {
        // Create an instance of ManipularCSV, passing the entity class type
        ManipularCSV<MyEntity> csvManipulator = new ManipularCSV(MyEntity.class);

        // Read all rows from the CSV file
        List<MyEntity> entities = csvManipulator.leerTodasLasFilas(ARCHIVEROUTE);
        System.out.println("Entities read from CSV file:");
        for (MyEntity entity : entities) {
            System.out.println(entity.toString());
        }
    }
}