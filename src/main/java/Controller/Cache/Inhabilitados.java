package Controller.Cache;

import Model.Inhabilitado;

import java.io.IOException;

public class Inhabilitados extends ADao<Inhabilitado,Integer> {
    public Inhabilitados()throws IOException {
        super("C:\\Users\\david\\OneDrive\\Escritorio\\Programer\\Estructura de datos\\ED-ProyectoF\\src\\main\\java\\inhabilitados.csv");
    }
}