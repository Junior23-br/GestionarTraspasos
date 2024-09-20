package ManipularCsv;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class ManipularCSV {
    private BufferedReader lector;
    private String linea;
    private String partes [];

    /**
     *
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
     * que se envió
     */
    public void imprimirLinea() {
        for (int i = 0; i < partes.length; i++) {
            System.out.println(partes[i] + "|");
        }

    }
}
