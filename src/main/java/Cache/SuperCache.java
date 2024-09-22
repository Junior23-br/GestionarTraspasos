package Cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuperCache {
    private Map<String, List<?>> cacheMap; // Mapa para almacenar datos en caché

    public SuperCache() {
        this.cacheMap = new HashMap<>();
    }

    public void agregarFilas(String nombreArchivo, List<?> filas) {
        cacheMap.put(nombreArchivo, filas);
    }

    public List<?> obtenerFilas(String nombreArchivo) {
        return cacheMap.getOrDefault(nombreArchivo, Collections.emptyList());
    }

}
