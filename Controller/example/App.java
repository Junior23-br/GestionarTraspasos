package Controller.example;

import Controller.Cache.Cotizantes;
import Controller.Cache.Embargos;
import Controller.Cache.Inhabilitados;
import Model.Cotizante;
import Model.Embargado;
import Model.Inhabilitado;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class App {

    public static void main(String[] args) throws IOException {



        Cotizantes cotizantes = new Cotizantes();
        List<Cotizante> cotizante = cotizantes.obtenerTodos();

        for (Cotizante cotizante1 : cotizante) {
            System.out.println(cotizante1);
        }


        Embargos embargos = new Embargos();
        List<Embargado> embargado = embargos.obtenerTodos();

        for(Embargado embargos1 : embargado) {
            System.out.println(embargos1.toString());
        }

        Inhabilitados inhabilitados = new Inhabilitados();
        List<Inhabilitado> inhabilitado = inhabilitados.obtenerTodos();
        for(Inhabilitado inhabilitados1 : inhabilitado) {
            System.out.println(inhabilitados1.toString());
        }
    }

    public static void ejemploReflection() {
        try {
            Class<Cotizante> claseEntidad = Cotizante.class;
            Cotizante paisEjemplo = claseEntidad.getDeclaredConstructor().newInstance();
            Field field = Cotizante.class.getDeclaredField("nombre");
            field.setAccessible(true);
            field.set(paisEjemplo, "Juan Pérez");
            System.out.println(paisEjemplo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
