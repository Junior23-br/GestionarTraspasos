package org.example;

import ManipularCsv.ManipularCSV;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    static String  url ="ruta/al/archivo/datos.csv";

    public static void main(String[] args) {

        ManipularCSV<String> lectorS = new ManipularCSV<>(String.class);
        lectorS.leerArchivo(url);

    }
}