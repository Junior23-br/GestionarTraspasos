package Controller.Cache;

import Model.Embargado;

import java.io.IOException;

public class Embargos extends ADao<Embargado,Integer>{
    public Embargos() throws IOException {
        super("C:\\Users\\user\\Desktop\\ED-ProyectoF\\src\\main\\java\\embargados.csv");
    }
}
