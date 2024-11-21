package Cache;

import Model.Cotizante;
import Util.LectorArchivosUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class SuperCache {
    private Map<String, Cotizante> cache = new HashMap<>();
    private Map<String, Cotizante> listaNegraCotizantes = new HashMap<>();
    private Map<String, Cotizante> prePensionadosCotizantes = new HashMap<>();
    private Map<String, Cotizante> institucionPublicaCotizantes = new HashMap<>();

    /**
     * Agregar un Cotizante al cache y categorizarlo.
     */
    public void addCache(String key, Cotizante value) {
        cache.put(key, value);

        // Categorizar según las propiedades del cotizante
        if (Boolean.TRUE.equals(value.isHaEstadoListaNegra())) {
            listaNegraCotizantes.put(key, value);
        }
        if (Boolean.TRUE.equals(value.isPrePensionado())) {
            prePensionadosCotizantes.put(key, value);
        }
        if (Boolean.TRUE.equals(value.isPerteneceInsPublica())) {
            institucionPublicaCotizantes.put(key, value);
        }
    }

    /**
     * Obtener un Cotizante del cache por su identificación.
     */
    public Cotizante getCache(String key) {
        return cache.get(key);
    }

    /**
     * Obtener todos los Cotizantes en el cache.
     */
    public Map<String, Cotizante> getAllCotizantes() {
        return cache;
    }

    /**
     * Cargar Cotizantes desde un archivo CSV.
     */
    public void cargarCotizantesDesdeArchivo(String rutaArchivo) throws IOException {
        LinkedList<String[]> lineas = LectorArchivosUtil.leerTodasLasLineasCsv(rutaArchivo);

        if (lineas.isEmpty()) {
            throw new IllegalArgumentException("El archivo está vacío: " + rutaArchivo);
        }

        // Número mínimo de campos requeridos
        final int CAMPOS_MINIMOS = 19;

        for (int i = 1; i < lineas.size(); i++) {
            String[] linea = lineas.get(i);

            // Validación de campos mínimos
            if (linea.length < CAMPOS_MINIMOS) {
                System.out.println("Línea inválida (campos insuficientes) en "
                        + rutaArchivo + ". Línea: " + String.join(";", linea));
                continue;
            }

            try {
                Cotizante cotizante = crearCotizante(linea);

                if (cotizante != null) {
                    addCache(cotizante.getCedula(), cotizante);
                }
            } catch (Exception e) {
                System.out.println("Error procesando línea en " + rutaArchivo
                        + ". Línea: " + String.join(";", linea));
                e.printStackTrace();
            }
        }
    }

    // Método extracto para crear Cotizante
    private Cotizante crearCotizante(String[] linea) {
        Cotizante cotizante = new Cotizante();

        try {
            // Campos obligatorios con validaciones
            cotizante.setCedula(validarCampoObligatorio(linea, 0, "Cédula"));
            cotizante.setNombre(validarCampoObligatorio(linea, 1, "Nombre"));

            // Edad con manejo de error
            cotizante.setEdad(parseEdad(linea, 2));

            // Campos de contacto
            cotizante.setCorreo(linea[3]);
            cotizante.setTelefono(linea[4]);

            // Campos booleanos
            cotizante.setHaEstadoListaNegra(parseBoolean(linea[5]));
            cotizante.setPrePensionado(parseBoolean(linea[6]));
            cotizante.setPerteneceInsPublica(parseBoolean(linea[7]));

            cotizante.setInstitucionPublica(linea[8] != null ? linea[8] : "");

            cotizante.setTieneCondecoracion(parseBoolean(linea[9]));
            cotizante.setTieneHijosInpec(parseBoolean(linea[10]));
            cotizante.setTieneFamPolicia(parseBoolean(linea[11]));
            cotizante.setEsElMayorEdad(parseBoolean(linea[12]));

            cotizante.setObservacionDisciplinaria(linea[13] != null ? linea[13] : "");

            // Semanas cotizadas
            cotizante.setSemanasCot(parseSemanas(linea, 14));

            cotizante.setCiudadNacimiento(linea[15]);
            cotizante.setCiudadResidencia(linea[16]);

            cotizante.setAlcanzoEdadRPM(parseBoolean(linea[17]));
            cotizante.setFondo(linea[18]);

            // Estado (opcional)
            if (linea.length > 19) {
                cotizante.setEstado(parseEstado(linea[19]));
            }

            return cotizante;

        } catch (Exception e) {
            System.out.println("Error en la creación del Cotizante: " + e.getMessage());
            return null;
        }
    }

    // Validación de campos obligatorios
    private String validarCampoObligatorio(String[] linea, int indice, String nombreCampo) {
        if (linea[indice] == null || linea[indice].trim().isEmpty()) {
            throw new IllegalArgumentException("Campo " + nombreCampo + " es obligatorio");
        }
        return linea[indice].trim();
    }

    // Parseo seguro de edad
    private int parseEdad(String[] linea, int indice) {
        try {
            return Integer.parseInt(linea[indice]);
        } catch (NumberFormatException e) {
            System.out.println("Edad inválida: " + linea[indice]);
            return 0;
        }
    }

    // Parseo seguro de semanas cotizadas
    private int parseSemanas(String[] linea, int indice) {
        try {
            return Integer.parseInt(linea[indice]);
        } catch (NumberFormatException e) {
            System.out.println("Semanas cotizadas inválidas: " + linea[indice]);
            return 0;
        }
    }

    // Parseo de estado
    private String parseEstado(String estado) {
        return (estado == null || "null".equalsIgnoreCase(estado)) ? "" : estado.trim();
    }

    // Método auxiliar para parseo seguro de booleanos
    private Boolean parseBoolean(String value) {
        if (value == null) return false;
        return Boolean.parseBoolean(value.trim().toLowerCase());
    }

    /**
     * Cargar Cotizantes desde múltiples directorios.
     */
    public void cargarDatosDesdeDirectorios(String[] directorios) throws IOException {
        for (String directorio : directorios) {
            File dir = new File(directorio);
            if (dir.isDirectory()) {
                for (File archivo : dir.listFiles()) {
                    if (archivo.getName().endsWith(".csv")) {
                        cargarCotizantesDesdeArchivo(archivo.getAbsolutePath());
                    }
                }
            }
        }
    }

    /**
     * Obtener Cotizantes en 'Lista Negra'.
     */
    public Map<String, Cotizante> getCotizantesListaNegra() {
        return listaNegraCotizantes;
    }

    /**
     * Obtener Cotizantes pre-pensionados.
     */
    public Map<String, Cotizante> getCotizantesPrePensionados() {
        return prePensionadosCotizantes;
    }

    /**
     * Obtener Cotizantes de instituciones públicas.
     */
    public Map<String, Cotizante> getCotizantesInstitucionPublica() {
        return institucionPublicaCotizantes;
    }

    /**
     * Asociar datos adicionales (ciudades y fondos de pensiones) a los Cotizantes.
     *
    public void asociarDatosAdicionales(Map<String, String> ciudades, Map<String, String> fondosPensiones) {
        for (Cotizante cotizante : cache.values()) {
            String identificacion = cotizante.getCedula();

            String ciudad = ciudades.get(identificacion);
            if (ciudad != null) {
                cotizante.setCiudadResidencia(ciudad);
            }

            String fondo = fondosPensiones.get(identificacion);
            if (fondo != null) {
                cotizante.setFondo(fondo);
            }
        }
    }*/

    public void limpiarCache() {
        cache.clear();
        listaNegraCotizantes.clear();
        prePensionadosCotizantes.clear();
        institucionPublicaCotizantes.clear();
    }

}


