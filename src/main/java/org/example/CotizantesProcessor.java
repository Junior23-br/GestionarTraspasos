package org.example;
import Util.ProcesadorArchivos;
import Cache.SuperCache;
import java.io.File;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;
import java.util.logging.Level;

public class CotizantesProcessor {
    // Logger para registrar eventos y errores del sistema
    private static final Logger LOGGER = Logger.getLogger(CotizantesProcessor.class.getName());

    // Intervalo de tiempo entre procesamientos (1 hora en milisegundos)
    private static final long PROCESSING_INTERVAL_MILLIS = TimeUnit.HOURS.toMillis(1);

    // Bandera atómica para controlar el estado de ejecución del sistema
    private static final AtomicBoolean isRunning = new AtomicBoolean(true);

    // Servicio programado para ejecutar tareas periódicamente
    private static ScheduledExecutorService schedulerService;

    /**
     * Punto de entrada principal del sistema.
     * Inicializa el cache y configura el hook de apagado.
     */
    public static void main(String[] args) {
        // Crear instancia del cache que almacenará los datos temporalmente
        SuperCache superCache = new SuperCache();
        // Inicializar el sistema con el cache
        initializeSystem(superCache);

        // Configurar el hook de apagado para una terminación limpia
        Runtime.getRuntime().addShutdownHook(new Thread(() -> shutdown()));
    }

    /**
     * Inicializa el sistema de procesamiento.
     * Configura un scheduler que procesará un archivo cada hora.
     */
    private static void initializeSystem(SuperCache superCache) {
        // Crear un pool de un solo hilo para el scheduler
        schedulerService = Executors.newScheduledThreadPool(1);

        // Crear una instancia del procesador que se reutilizará
        ProcesadorArchivos procesador = new ProcesadorArchivos(superCache);

        // Programar la tarea de procesamiento para ejecutarse cada hora
        schedulerService.scheduleAtFixedRate(
                () -> {
                    if (isRunning.get()) {
                        try {
                            // Obtener lista de archivos CSV pendientes
                            File[] archivos = new File(ProcesadorArchivos.SOLICITUDES_ENTRANTES)
                                    .listFiles((dir, name) -> name.endsWith(".csv"));

                            if (archivos != null && archivos.length > 0) {
                                // Procesar solo el primer archivo encontrado
                                procesador.procesarArchivoIndividual(archivos[0]);
                                LOGGER.info("Archivo " + archivos[0].getName() +
                                        " procesado. Esperando 1 hora para el siguiente.");
                            } else {
                                LOGGER.info("No hay archivos pendientes para procesar.");
                            }
                        } catch (Exception e) {
                            LOGGER.log(Level.SEVERE, "Error al procesar el archivo", e);
                        }
                    }
                },
                0,                           // Delay inicial (comienza inmediatamente)
                PROCESSING_INTERVAL_MILLIS,   // Período entre ejecuciones (1 hora)
                TimeUnit.MILLISECONDS
        );

        LOGGER.info("Sistema iniciado. Procesando un archivo cada hora...");
    }

    /**
     * Realiza un apagado controlado del sistema.
     * Detiene el scheduler y espera a que las tareas terminen.
     */
    private static void shutdown() {
        LOGGER.info("Iniciando apagado del sistema...");
        isRunning.set(false);

        if (schedulerService != null) {
            schedulerService.shutdown();
            try {
                // Esperar hasta 30 segundos para que termine la tarea actual
                if (!schedulerService.awaitTermination(30, TimeUnit.SECONDS)) {
                    schedulerService.shutdownNow();
                }
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, "Error durante el apagado del planificador", e);
                schedulerService.shutdownNow();
            }
        }

        LOGGER.info("Sistema apagado correctamente");
    }
}