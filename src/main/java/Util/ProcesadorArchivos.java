package Util;
import Cache.SuperCache;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.concurrent.atomic.AtomicBoolean;

public class ProcesadorArchivos implements Runnable {
    // Rutas de los directorios para el procesamiento de archivos
    private static final String BASE_PATH = "src/main/resources/archivos/";
    public static final String SOLICITUDES_ENTRANTES = BASE_PATH + "SolicitudesEntrantes";
    private static final String SOLICITUDES_PROCESADAS = BASE_PATH + "SolicitudesProcesadas";
    private static final String RESULTADOS = BASE_PATH + "ArchivosResultantes";
    private static final String CSV_EXTENSION = ".csv";

    // Componentes principales del procesador
    private final SuperCache superCache;           // Cache para almacenar datos temporalmente
    private final Logger logger = Logger.getLogger(ProcesadorArchivos.class.getName());
    private final AtomicBoolean isRunning;        // Control de estado de ejecución

    /**
     * Constructor del procesador.
     * Inicializa el cache y el estado de ejecución.
     */
    public ProcesadorArchivos(SuperCache superCache) {
        this.superCache = superCache;
        this.isRunning = new AtomicBoolean(true);
    }

    /**
     * Detiene el procesamiento de archivos.
     */
    public void detener() {
        isRunning.set(false);
    }

    /**
     * Implementación del método run de Runnable.
     * Procesa un lote completo de archivos (modo legacy).
     */
    @Override
    public void run() {
        if (!isRunning.get()) return;

        try {
            procesarLote();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error en el procesamiento del lote", e);
        }
    }

    /**
     * Procesa un archivo individual.
     * Este es el nuevo método principal para el procesamiento uno a uno.
     */
    public void procesarArchivoIndividual(File archivo) {
        try {
            // Asegurar que existan los directorios necesarios
            crearDirectoriosSiNoExisten();

            logger.info("Iniciando procesamiento del archivo: " + archivo.getName());

            // Proceso principal del archivo
            cargarDatosAlCache(archivo);
            generarResultados(archivo.getName());
            moverArchivoAProcesados(archivo);

            logger.info("Archivo procesado exitosamente: " + archivo.getName());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error procesando archivo: " + archivo.getName(), e);
        } finally {
            // Limpiar cache después del procesamiento
            superCache.limpiarCache();
        }
    }

    /**
     * Procesa un lote completo de archivos (modo legacy).
     */
    private void procesarLote() {
        try {
            crearDirectoriosSiNoExisten();
            procesarArchivosPendientes();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error procesando lote de archivos", e);
        } finally {
            superCache.limpiarCache();
        }
    }

    /**
     * Crea los directorios necesarios si no existen.
     */
    private void crearDirectoriosSiNoExisten() {
        createDirectoryIfNotExists(SOLICITUDES_ENTRANTES);
        createDirectoryIfNotExists(SOLICITUDES_PROCESADAS);
        createDirectoryIfNotExists(RESULTADOS);
    }

    /**
     * Crea un directorio específico si no existe.
     */
    private void createDirectoryIfNotExists(String directoryPath) {
        Path path = Paths.get(directoryPath);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error creando directorio: " + directoryPath, e);
            }
        }
    }

    /**
     * Procesa todos los archivos pendientes (modo legacy).
     */
    private void procesarArchivosPendientes() {
        File carpetaEntrantes = new File(SOLICITUDES_ENTRANTES);
        File[] archivos = carpetaEntrantes.listFiles((dir, name) -> name.endsWith(CSV_EXTENSION));

        if (archivos == null || archivos.length == 0) {
            logger.info("No hay archivos nuevos para procesar");
            return;
        }

        for (File archivo : archivos) {
            if (!isRunning.get()) break;
            procesarArchivo(archivo);
        }
    }

    /**
     * Procesa un archivo individual (usado en modo legacy).
     */
    private void procesarArchivo(File archivo) {
        logger.info("Iniciando procesamiento del archivo: " + archivo.getName());

        try {
            cargarDatosAlCache(archivo);
            generarResultados(archivo.getName());
            moverArchivoAProcesados(archivo);

            logger.info("Archivo procesado exitosamente: " + archivo.getName());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error procesando archivo: " + archivo.getName(), e);
        }
    }

    /**
     * Carga los datos del archivo al cache.
     */
    private void cargarDatosAlCache(File archivo) throws IOException {
        superCache.cargarCotizantesDesdeArchivo(archivo.getAbsolutePath());
    }

    /**
     * Mueve el archivo procesado al directorio de procesados.
     */
    private void moverArchivoAProcesados(File archivo) throws IOException {
        Path origen = archivo.toPath();
        Path destino = Paths.get(SOLICITUDES_PROCESADAS, archivo.getName());
        Files.move(origen, destino);
    }

    /**
     * Genera los archivos de resultados.
     */
    private void generarResultados(String nombreArchivoOriginal) throws IOException {
        String nombreBase = nombreArchivoOriginal.replace(CSV_EXTENSION, "");
        String rutaBase = RESULTADOS + File.separator + nombreBase;
        generarArchivosResultado(rutaBase);
    }

    /**
     * Genera los diferentes archivos de resultado por categoría.
     */
    private void generarArchivosResultado(String rutaBase) throws IOException {
        // Exportar todos los cotizantes
        CsvUtils.exportarCotizantesACsv(
                rutaBase + "_todos.csv",
                superCache
        );

        // Exportar cotizantes por categorías específicas
        exportarCategoriaEspecifica(rutaBase + "_listaNegra.csv", "ListaNegra");
        exportarCategoriaEspecifica(rutaBase + "_prePensionados.csv", "PrePensionados");
        exportarCategoriaEspecifica(rutaBase + "_institucionPublica.csv", "InstitucionPublica");
    }

    /**
     * Exporta cotizantes de una categoría específica.
     */
    private void exportarCategoriaEspecifica(String ruta, String categoria) throws IOException {
        CsvUtils.exportarCotizantesFiltrados(ruta, superCache, categoria);
    }
}