package Cache;

import Model.Cotizante;
import Util.ListaEnlazada;

public class ListaNegra {
    private ListaEnlazada<Cotizante> listaNegra = new ListaEnlazada<>();

    public void addCotizante(Cotizante cotizante) {
        listaNegra.add(cotizante);
    }

    public ListaEnlazada<Cotizante> getListaNegra() {
        return listaNegra;
    }

}