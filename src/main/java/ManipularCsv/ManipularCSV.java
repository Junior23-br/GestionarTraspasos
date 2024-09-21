package ManipularCsv;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class ManipularCSV <T> {
    private BufferedReader lector;
    private String linea;
    private String partes[];
    private Class<T> tipo;

    /**
     * Constructor de inicialización clase tipo generica
     */
    public ManipularCSV(Class<T> tipo) {
        this.tipo = tipo;
    }
    /**
     * @param nombreArchivo
     *  nombreArchivo es la ruta el CSV
     */
    public void leerArchivo(String nombreArchivo) {
        try {
            lector = new BufferedReader(new FileReader(nombreArchivo));
            while ((linea = lector.readLine()) != null) {
                partes = linea.split(",");
                imprimirLinea();

            }
            lector.close();
            linea = null;
            partes = null;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    /**
     * Esta función se utiliza para imprimir la linea
     * Creamos una variable de tipo T para llamar al metodo que le especifica el tipo de dato
     */
    public void imprimirLinea() {
        for (String parte : partes) {
            T valor = tipoDato(parte);
            System.out.println(valor + "|");
        }
        System.out.println(); //Salto de linea
    }


    /**
     * Usar reflexión para convertir a tipo T de tipo string o conversión comprobando tipo a tipo como abajo
     */
    private T tipoDato(String parte) {
        if(tipo==Integer.class) {
            return (T) Integer.valueOf(parte);
        }
        else if(tipo==Double.class) {
            return (T) Double.valueOf(parte);
        }
        else if(tipo==Boolean.class) {
            return (T) Boolean.valueOf(parte);
        }
        else {
            return (T) parte;
        }
    }

}


