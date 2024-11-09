package Controller.Cache;

import Model.Cotizante;

import java.io.IOException;

public class Cotizantes extends ADao<Cotizante,Integer> {
    public Cotizantes() throws IOException {
        super("C:\\Users\\david\\OneDrive\\Escritorio\\Programer\\Estructura de datos\\ED-ProyectoF\\src\\main\\java\\cotizantes.csv");
    }

}
