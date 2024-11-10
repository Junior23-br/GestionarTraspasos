package Model;


public class Cotizante implements Comparable<Cotizante> {
    private String tipoDeDocumento;
    private String documento;
    private String nombre;
    private int edad;
    private int semanasCotizadas;
    private boolean embargo;
    private boolean inhabilitado;
    private boolean Renta;

    public Cotizante(){
        this.embargo=false;
        this.inhabilitado=false;
        this.Renta=false;
    }
    public String getTipoDeDocumento() {
        return tipoDeDocumento;
    }

    public void setTipoDeDocumento(String tipoDeDocumento) {
        this.tipoDeDocumento = tipoDeDocumento;
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

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getSemanasCotizadas() {
        return semanasCotizadas;
    }

    public void setSemanasCotizadas(int semanasCotizadas) {
        this.semanasCotizadas = semanasCotizadas;
    }

    public boolean isEmbargo() {
        return embargo;
    }

    public void setEmbargo(boolean embargo) {
        this.embargo = embargo;
    }

    public boolean isInhabilitado() {
        return inhabilitado;
    }

    public void setInhabilitado(boolean inhabilitado) {
        this.inhabilitado = inhabilitado;
    }

    public boolean isRenta() {
        return Renta;
    }

    public void setRenta(boolean renta) {
        Renta = renta;
    }
    @Override public int compareTo(Cotizante next) {
        return Integer.compare(this.edad, next.getEdad());
    }
}
