package Util;

import Cache.SuperCache;
import Model.Cotizante;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;


public class CsvUtils {


    /**
     * Método para guardar los cotizantes en un archivo CSV
     * @param rutaArchivo Ruta donde se guardará el archivo CSV
     * @param superCache Instancia de SuperCache con los cotizantes
     * @throws IOException Si hay un error al escribir el archivo
     */
    public static void exportarCotizantesACsv(String rutaArchivo, SuperCache superCache) throws IOException {
        // Obtener todos los cotizantes de la caché
        Map<String, Cotizante> cotizantes = superCache.getAllCotizantes();


        if (cotizantes == null || cotizantes.isEmpty()) {
            throw new IllegalArgumentException("No hay cotizantes para exportar.");
        }


        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaArchivo))) {
            // Escribir encabezados en el archivo usando el separador definido
            escritor.write(
                    "Cedula" + LectorArchivosUtil.SEPARADOR_CSV +
                            "Nombre" + LectorArchivosUtil.SEPARADOR_CSV +
                            "Edad" + LectorArchivosUtil.SEPARADOR_CSV +
                            "Correo" + LectorArchivosUtil.SEPARADOR_CSV +
                            "Telefono" + LectorArchivosUtil.SEPARADOR_CSV +
                            "ListaNegra" + LectorArchivosUtil.SEPARADOR_CSV +
                            "PrePensionado" + LectorArchivosUtil.SEPARADOR_CSV +
                            "InsPublica" + LectorArchivosUtil.SEPARADOR_CSV +
                            "InstitucionPublica" + LectorArchivosUtil.SEPARADOR_CSV +
                            "Condecoracion" + LectorArchivosUtil.SEPARADOR_CSV +
                            "HijosInpec" + LectorArchivosUtil.SEPARADOR_CSV +
                            "FamPolicia" + LectorArchivosUtil.SEPARADOR_CSV +
                            "MayorEdad" + LectorArchivosUtil.SEPARADOR_CSV +
                            "AlcanzoEdadRPM" + LectorArchivosUtil.SEPARADOR_CSV +
                            "SemanasCotizadas" + LectorArchivosUtil.SEPARADOR_CSV +
                            "CiudadNacimiento" + LectorArchivosUtil.SEPARADOR_CSV +
                            "CiudadResidencia" + LectorArchivosUtil.SEPARADOR_CSV +
                            "Fondo" + LectorArchivosUtil.SEPARADOR_CSV +
                            "Estado" + LectorArchivosUtil.SEPARADOR_CSV +
                            "ObservacionDisciplinaria\n"
            );


            // Escribir los datos de cada cotizante
            for (Cotizante cotizante : cotizantes.values()) {
                String linea = String.join(LectorArchivosUtil.SEPARADOR_CSV,
                        valorOPorDefecto(cotizante.getCedula()),
                        valorOPorDefecto(cotizante.getNombre()),
                        valorOPorDefecto(cotizante.getEdad() != null ? cotizante.getEdad().toString() : ""),
                        valorOPorDefecto(cotizante.getCorreo()),
                        valorOPorDefecto(cotizante.getTelefono()),
                        valorBooleano(cotizante.isHaEstadoListaNegra()),
                        valorBooleano(cotizante.isPrePensionado()),
                        valorBooleano(cotizante.isPerteneceInsPublica()),
                        valorOPorDefecto(cotizante.getInstitucionPublica()),
                        valorBooleano(cotizante.isTieneCondecoracion()),
                        valorBooleano(cotizante.isTieneHijosInpec()),
                        valorBooleano(cotizante.isTieneFamPolicia()),
                        valorBooleano(cotizante.isEsElMayorEdad()),
                        valorBooleano(cotizante.isAlcanzoEdadRPM()),
                        valorOPorDefecto(cotizante.getSemanasCot() != null ? cotizante.getSemanasCot().toString() : ""),
                        valorOPorDefecto(cotizante.getCiudadNacimiento()),
                        valorOPorDefecto(cotizante.getCiudadResidencia()),
                        valorOPorDefecto(cotizante.getFondo()),
                        valorOPorDefecto(cotizante.getEstado()),
                        valorOPorDefecto(cotizante.getObservacionDisciplinaria())
                );
                escritor.write(linea + "\n");
            }


            System.out.println("Archivo CSV exportado exitosamente: " + rutaArchivo);
        }
    }


    /**
     * Método para exportar cotizantes filtrados por categoría
     * @param rutaArchivo Ruta donde se guardará el archivo CSV
     * @param superCache Instancia de SuperCache con los cotizantes
     * @param tipoExportacion Tipo de cotizantes a exportar
     * @throws IOException Si hay un error al escribir el archivo
     */
    public static void exportarCotizantesFiltrados(String rutaArchivo, SuperCache superCache, String tipoExportacion) throws IOException {
        Map<String, Cotizante> cotizantesFiltrados = switch (tipoExportacion) {
            case "ListaNegra" -> superCache.getCotizantesListaNegra();
            case "PrePensionados" -> superCache.getCotizantesPrePensionados();
            case "InstitucionPublica" -> superCache.getCotizantesInstitucionPublica();
            default -> throw new IllegalArgumentException("Tipo de exportación no válido");
        };


        if (cotizantesFiltrados == null || cotizantesFiltrados.isEmpty()) {
            throw new IllegalArgumentException("No hay cotizantes para exportar en la categoría: " + tipoExportacion);
        }


        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaArchivo))) {
            // Escribir encabezados en el archivo usando el separador definido
            escritor.write(
                    "Cedula" + LectorArchivosUtil.SEPARADOR_CSV +
                            "Nombre" + LectorArchivosUtil.SEPARADOR_CSV +
                            "Edad" + LectorArchivosUtil.SEPARADOR_CSV +
                            "Correo" + LectorArchivosUtil.SEPARADOR_CSV +
                            "Telefono" + LectorArchivosUtil.SEPARADOR_CSV +
                            "ListaNegra" + LectorArchivosUtil.SEPARADOR_CSV +
                            "PrePensionado" + LectorArchivosUtil.SEPARADOR_CSV +
                            "InsPublica" + LectorArchivosUtil.SEPARADOR_CSV +
                            "InstitucionPublica" + LectorArchivosUtil.SEPARADOR_CSV +
                            "Condecoracion" + LectorArchivosUtil.SEPARADOR_CSV +
                            "HijosInpec" + LectorArchivosUtil.SEPARADOR_CSV +
                            "FamPolicia" + LectorArchivosUtil.SEPARADOR_CSV +
                            "MayorEdad" + LectorArchivosUtil.SEPARADOR_CSV +
                            "AlcanzoEdadRPM" + LectorArchivosUtil.SEPARADOR_CSV +
                            "SemanasCotizadas" + LectorArchivosUtil.SEPARADOR_CSV +
                            "CiudadNacimiento" + LectorArchivosUtil.SEPARADOR_CSV +
                            "CiudadResidencia" + LectorArchivosUtil.SEPARADOR_CSV +
                            "Fondo" + LectorArchivosUtil.SEPARADOR_CSV +
                            "Estado" + LectorArchivosUtil.SEPARADOR_CSV +
                            "ObservacionDisciplinaria\n"
            );


            // Escribir los datos de cada cotizante filtrado
            for (Cotizante cotizante : cotizantesFiltrados.values()) {
                String linea = String.join(LectorArchivosUtil.SEPARADOR_CSV,
                        valorOPorDefecto(cotizante.getCedula()),
                        valorOPorDefecto(cotizante.getNombre()),
                        valorOPorDefecto(cotizante.getEdad() != null ? cotizante.getEdad().toString() : ""),
                        valorOPorDefecto(cotizante.getCorreo()),
                        valorOPorDefecto(cotizante.getTelefono()),
                        valorBooleano(cotizante.isHaEstadoListaNegra()),
                        valorBooleano(cotizante.isPrePensionado()),
                        valorBooleano(cotizante.isPerteneceInsPublica()),
                        valorOPorDefecto(cotizante.getInstitucionPublica()),
                        valorBooleano(cotizante.isTieneCondecoracion()),
                        valorBooleano(cotizante.isTieneHijosInpec()),
                        valorBooleano(cotizante.isTieneFamPolicia()),
                        valorBooleano(cotizante.isEsElMayorEdad()),
                        valorBooleano(cotizante.isAlcanzoEdadRPM()),
                        valorOPorDefecto(cotizante.getSemanasCot() != null ? cotizante.getSemanasCot().toString() : ""),
                        valorOPorDefecto(cotizante.getCiudadNacimiento()),
                        valorOPorDefecto(cotizante.getCiudadResidencia()),
                        valorOPorDefecto(cotizante.getFondo()),
                        valorOPorDefecto(cotizante.getEstado()),
                        valorOPorDefecto(cotizante.getObservacionDisciplinaria())
                );
                escritor.write(linea + "\n");
            }


            System.out.println("Archivo CSV filtrado exportado exitosamente: " + rutaArchivo);
        }
    }


    /**
     * Método para manejar valores nulos o vacíos
     * @param valor Valor a verificar
     * @return Valor original o cadena vacía si es nulo
     */
    private static String valorOPorDefecto(String valor) {
        return valor != null ? valor : "";
    }


    /**
     * Método para convertir valores booleanos a cadena
     * @param valor Valor booleano
     * @return "Si" para true, "No" para false o nulo
     */
    private static String valorBooleano(Boolean valor) {
        if (valor == null) return "";
        return valor ? "Si" : "No";
    }
}

