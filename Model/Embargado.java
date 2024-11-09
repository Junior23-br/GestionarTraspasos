package Model;

import java.util.Date;

public class Embargado {
    private String documento, nombre, entidadEmbargante;
    private String fechaEmbargo;

    public Embargado() {
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEntidadEmbargante() {
        return entidadEmbargante;
    }

    public void setEntidadEmbargante(String entidadEmbargante) {
        this.entidadEmbargante = entidadEmbargante;
    }

    public String getFechaEmbargo() {
        return fechaEmbargo;
    }

    public void setFechaEmbargo(String fechaEmbargo) {
        this.fechaEmbargo = fechaEmbargo;
    }
}
