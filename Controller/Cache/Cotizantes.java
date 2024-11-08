package Controller.Cache;

import Model.Cotizante;

import java.io.IOException;

public class Cotizantes extends ADao<Cotizante,Integer> {
    public Cotizantes() throws IOException {
        super("ruta");
    }

}
