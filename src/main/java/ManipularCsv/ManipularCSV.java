package ManipularCsv;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManipularCSV <T> {
        private Map<String, Object> attributeMap;
        private Class<T> entityType;
        private String partes[];

    /**
     * Constructor de la clase Manipular CSV
     * @param entityType
     */
    public ManipularCSV(Class<T> entityType) {
           this.entityType = entityType;
             this.attributeMap = new HashMap<>();
        }

    /**
     *
     * @param nombreArchivo Ruta del CSV
     * @return Lista tipo T
     */

    public List<T> leerTodasLasFilas(String nombreArchivo) {
        List<T> entidades = new ArrayList<>();
        try (BufferedReader lector = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(",");
                Map<String, String> mapa = new HashMap<>();
                for (int i = 0; i < partes.length; i++) {
                    mapa.put("column" + (i + 1), partes[i]);
                }
                T entidad = crearEntidad(mapa);
                entidades.add(entidad);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return entidades;
    }

    /**
     * Escribe entidad al archivo CSV
     * @param nombreArchivo el archivo a escribir
     * @param entidad el objeto a escribir T
     */
    public void escribirFila(String nombreArchivo, T entidad) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo, true))) {
            StringBuilder line = new StringBuilder();
            for (Map.Entry<String, Object> entry : attributeMap.entrySet()) {
                line.append(entry.getValue()).append(",");
            }
            line.deleteCharAt(line.length() - 1); // remove trailing comma
            writer.write(line.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Crea una nueva instancia de la entidad usando map
     * @param mapa the CSV row to create the entity from
     * @return the created entity
     */
    private T crearEntidad(Map<String, String> mapa) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // TO DO: implement the logic to create an object of type T from the mapa
        // For example, using Java Reflection:
        T entidad =entityType.getDeclaredConstructor().newInstance();
        for (Map.Entry<String, String> entry : mapa.entrySet()) {
            String fieldName = entry.getKey();
            String fieldValue = entry.getValue();
            try {
                Field field = entityType.getField(fieldName);
                field.set(entidad, fieldValue);
            } catch (NoSuchFieldException e) {
                // Handle the case where the field does not exist
            } catch (IllegalAccessException e) {
                // Handle the case where the field is not accessible
            }
        }
        return entidad;
    }


}
