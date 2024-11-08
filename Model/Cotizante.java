package Model;


public class Cotizante {
    private String tipoDeDocumento;
    private String documento;
    private String nombre;
    private String edad;
    private String semanasCotizadas;
    private boolean embargo;
    private boolean inhabilitado;

    public Cotizante(){
        this.embargo=false;
        this.inhabilitado=false;
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

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getSemanasCotizadas() {
        return semanasCotizadas;
    }

    public void setSemanasCotizadas(String semanasCotizadas) {
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
}
