package org.example;


import Util.ProcesadorArchivos;

import Cache.SuperCache;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;
import java.util.logging.Level;

class CotizantesProcessor {
    private static final Logger LOGGER = Logger.getLogger(CotizantesProcessor.class.getName());
    private static final long PROCESSING_INTERVAL_MILLIS = TimeUnit.HOURS.toMillis(1);
    private static final AtomicBoolean isRunning = new AtomicBoolean(true);
    private static ScheduledExecutorService schedulerService;
    private static ExecutorService processingService;

    public static void main(String[] args) {
        SuperCache superCache = new SuperCache();
        initializeSystem(superCache);

        // Agregar shutdown hook para limpieza
        Runtime.getRuntime().addShutdownHook(new Thread(() -> shutdown()));
    }

    private static void initializeSystem(SuperCache superCache) {
        // Servicio para la programación de tareas
        schedulerService = Executors.newScheduledThreadPool(1);
        // Servicio para el procesamiento de archivos
        processingService = Executors.newFixedThreadPool(1);

        // Programar la tarea de procesamiento
        schedulerService.scheduleAtFixedRate(
                () -> {
                    if (isRunning.get()) {
                        processingService.submit(new ProcesadorArchivos(superCache));
                    }
                },
                0, // inicio inmediato
                PROCESSING_INTERVAL_MILLIS,
                TimeUnit.MILLISECONDS
        );

        LOGGER.info("Sistema iniciado. Procesando archivos cada hora...");
    }

    private static void shutdown() {
        LOGGER.info("Iniciando apagado del sistema...");
        isRunning.set(false);

        // Apagar el servicio de programación
        if (schedulerService != null) {
            schedulerService.shutdown();
            try {
                if (!schedulerService.awaitTermination(30, TimeUnit.SECONDS)) {
                    schedulerService.shutdownNow();
                }
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, "Error durante el apagado del planificador", e);
                schedulerService.shutdownNow();
            }
        }

        // Apagar el servicio de procesamiento
        if (processingService != null) {
            processingService.shutdown();
            try {
                if (!processingService.awaitTermination(60, TimeUnit.SECONDS)) {
                    processingService.shutdownNow();
                }
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, "Error durante el apagado del procesador", e);
                processingService.shutdownNow();
            }
        }

        LOGGER.info("Sistema apagado correctamente");
    }
}