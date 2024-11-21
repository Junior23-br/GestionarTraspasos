package Controller.example;

import Controller.Cache.Cotizantes;
import Controller.Cache.Embargos;
import Controller.Cache.Inhabilitados;
import Model.Cotizante;
import Model.Embargado;
import Model.Inhabilitado;

import java.io.IOException;
import java.util.List;

public class App {
    public static void main(String[] args) {
        try {
            // Cargar datos de Cotizantes desde el archivo CSV
            Cotizantes cotizantesDao = new Cotizantes();
            List<Cotizante> cotizantes = cotizantesDao.obtenerTodos();
            System.out.println("Los Cotizantes son:");
            for (Cotizante cotizante : cotizantes) {
                System.out.println(cotizante.getNombre() + " - " + cotizante.getDocumento());
            }

            // Cargar datos de Embargados desde el archivo CSV
            Embargos embargosDao = new Embargos();
            List<Embargado> embargados = embargosDao.obtenerTodos();
            System.out.println("\nLos Embargados son:");
            for (Embargado embargado : embargados) {
                System.out.println(embargado.getDocumento() + " - " + embargado.getNombre());
            }

            // Cargar datos de Inhabilitados desde el archivo CSV
            Inhabilitados inhabilitadosDao = new Inhabilitados();
            List<Inhabilitado> inhabilitados = inhabilitadosDao.obtenerTodos();
            System.out.println("\nLos Inhabilitados son:");
            for (Inhabilitado inhabilitado : inhabilitados) {
                System.out.println(inhabilitado.getNombre() + " - " + inhabilitado.getDocumento());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
