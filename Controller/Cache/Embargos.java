package Controller.Cache;

import Model.Embargado;

import java.io.IOException;

public class Embargos extends ADao<Embargado,Integer>{
    public Embargos() throws IOException {
        super("C:\\Users\\david\\OneDrive\\Escritorio\\Programer\\Estructura de datos\\ED-ProyectoF\\src\\main\\java\\embargados.csv");
    }
}
