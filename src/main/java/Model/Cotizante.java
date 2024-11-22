package Model;

public class Cotizante {
    private String cedula;
    private String nombre;
    private Integer edad;
    private String correo;
    private String telefono;
    private Boolean haEstadoListaNegra;
    private Boolean prePensionado;
    private Boolean perteneceInsPublica;
    private String institucionPublica;
    private Boolean tieneCondecoracion;
    private Boolean tieneHijosInpec;
    private Boolean tieneFamPolicia;
    private Boolean esElMayorEdad;
    private Boolean alcanzoEdadRPM;
    private Integer semanasCot;
    private String ciudadNacimiento;
    private String ciudadResidencia;
    private String fondo;
    private String estado;
    private String observacionDisciplinaria;

    // Constructor vacío
    public Cotizante() {}

    // Constructor completo
    public Cotizante(String cedula, String nombre, Integer edad, String correo, String telefono,
                     Boolean haEstadoListaNegra, Boolean prePensionado, Boolean perteneceInsPublica,
                     String institucionPublica, Boolean tieneCondecoracion, Boolean tieneHijosInpec,
                     Boolean tieneFamPolicia, Boolean esElMayorEdad, Boolean alcanzoEdadRPM,
                     Integer semanasCot, String ciudadNacimiento, String ciudadResidencia,
                     String fondo, String estado, String observacionDisciplinaria) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.edad = edad;
        this.correo = correo;
        this.telefono = telefono;
        this.haEstadoListaNegra = haEstadoListaNegra;
        this.prePensionado = prePensionado;
        this.perteneceInsPublica = perteneceInsPublica;
        this.institucionPublica = institucionPublica;
        this.tieneCondecoracion = tieneCondecoracion;
        this.tieneHijosInpec = tieneHijosInpec;
        this.tieneFamPolicia = tieneFamPolicia;
        this.esElMayorEdad = esElMayorEdad;
        this.alcanzoEdadRPM = alcanzoEdadRPM;
        this.semanasCot = semanasCot;
        this.ciudadNacimiento = ciudadNacimiento;
        this.ciudadResidencia = ciudadResidencia;
        this.fondo = fondo;
        this.estado = estado;
        this.observacionDisciplinaria = observacionDisciplinaria;
    }

    // Getters y Setters
    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean isHaEstadoListaNegra() {
        return haEstadoListaNegra;
    }

    public void setHaEstadoListaNegra(Boolean haEstadoListaNegra) {
        this.haEstadoListaNegra = haEstadoListaNegra;
    }

    public Boolean isPrePensionado() {
        return prePensionado;
    }

    public void setPrePensionado(Boolean prePensionado) {
        this.prePensionado = prePensionado;
    }

    public Boolean isPerteneceInsPublica() {
        return perteneceInsPublica;
    }

    public void setPerteneceInsPublica(Boolean perteneceInsPublica) {
        this.perteneceInsPublica = perteneceInsPublica;
    }

    public String getInstitucionPublica() {
        return institucionPublica;
    }

    public void setInstitucionPublica(String institucionPublica) {
        this.institucionPublica = institucionPublica;
    }

    public Boolean isTieneCondecoracion() {
        return tieneCondecoracion;
    }

    public void setTieneCondecoracion(Boolean tieneCondecoracion) {
        this.tieneCondecoracion = tieneCondecoracion;
    }

    public Boolean isTieneHijosInpec() {
        return tieneHijosInpec;
    }

    public void setTieneHijosInpec(Boolean tieneHijosInpec) {
        this.tieneHijosInpec = tieneHijosInpec;
    }

    public Boolean isTieneFamPolicia() {
        return tieneFamPolicia;
    }

    public void setTieneFamPolicia(Boolean tieneFamPolicia) {
        this.tieneFamPolicia = tieneFamPolicia;
    }

    public Boolean isEsElMayorEdad() {
        return esElMayorEdad;
    }

    public void setEsElMayorEdad(Boolean esElMayorEdad) {
        this.esElMayorEdad = esElMayorEdad;
    }

    public Boolean isAlcanzoEdadRPM() {
        return alcanzoEdadRPM;
    }

    public void setAlcanzoEdadRPM(Boolean alcanzoEdadRPM) {
        this.alcanzoEdadRPM = alcanzoEdadRPM;
    }

    public Integer getSemanasCot() {
        return semanasCot;
    }

    public void setSemanasCot(Integer semanasCot) {
        this.semanasCot = semanasCot;
    }

    public String getCiudadNacimiento() {
        return ciudadNacimiento;
    }

    public void setCiudadNacimiento(String ciudadNacimiento) {
        this.ciudadNacimiento = ciudadNacimiento;
    }

    public String getCiudadResidencia() {
        return ciudadResidencia;
    }

    public void setCiudadResidencia(String ciudadResidencia) {
        this.ciudadResidencia = ciudadResidencia;
    }

    public String getFondo() {
        return fondo;
    }

    public void setFondo(String fondo) {
        this.fondo = fondo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservacionDisciplinaria() {
        return observacionDisciplinaria;
    }

    public void setObservacionDisciplinaria(String observacionDisciplinaria) {
        this.observacionDisciplinaria = observacionDisciplinaria;
    }

    // toString para representación en texto
    @Override
    public String toString() {
        return "Cotizante{" +
                "cedula='" + cedula + '\'' +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", fondo='" + fondo + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}

