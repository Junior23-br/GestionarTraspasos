package CSV;

import Model.Cotizante;

import java.io.IOException;

public class   CotizantesDao extends ADao<Cotizante, Integer> {
    public CotizantesDao(String rutaArchivo) throws IOException {
        super(rutaArchivo);
    }
}
