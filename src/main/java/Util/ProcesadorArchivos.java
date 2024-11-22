package Util;

import Cache.SuperCache;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProcesadorArchivos implements Runnable {
    private static final String BASE_PATH = "src/main/resources/archivos/";
    private static final String SOLICITUDES_ENTRANTES = BASE_PATH + "SolicitudesEntrantes";
    private static final String SOLICITUDES_PROCESADAS = BASE_PATH + "SolicitudesProcesadas";
    private static final String RESULTADOS = BASE_PATH + "ArchivosResultantes"; // Cambiado de "resultados" a "ArchivosResultantes"
    private static final String CSV_EXTENSION = ".csv";

    private final SuperCache superCache;
    private final Logger logger = Logger.getLogger(ProcesadorArchivos.class.getName());
    private final AtomicBoolean isRunning;

    public ProcesadorArchivos(SuperCache superCache) {
        this.superCache = superCache;
        this.isRunning = new AtomicBoolean(true);
    }

    public void detener() {
        isRunning.set(false);
    }

    @Override
    public void run() {
        if (!isRunning.get()) return;

        try {
            procesarLote();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error en el procesamiento del lote", e);
        }
    }

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

    private void crearDirectoriosSiNoExisten() {
        createDirectoryIfNotExists(SOLICITUDES_ENTRANTES);
        createDirectoryIfNotExists(SOLICITUDES_PROCESADAS);
        createDirectoryIfNotExists(RESULTADOS);
    }

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

    private void cargarDatosAlCache(File archivo) throws IOException {
        superCache.cargarCotizantesDesdeArchivo(archivo.getAbsolutePath());
    }

    private void moverArchivoAProcesados(File archivo) throws IOException {
        Path origen = archivo.toPath();
        Path destino = Paths.get(SOLICITUDES_PROCESADAS, archivo.getName());
        Files.move(origen, destino);
    }

    private void generarResultados(String nombreArchivoOriginal) throws IOException {
        String nombreBase = nombreArchivoOriginal.replace(CSV_EXTENSION, "");
        String rutaBase = RESULTADOS + File.separator + nombreBase;
        generarArchivosResultado(rutaBase);
    }

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

    private void exportarCategoriaEspecifica(String ruta, String categoria) throws IOException {
        CsvUtils.exportarCotizantesFiltrados(ruta, superCache, categoria);
    }
}