package ServicePackage;

import Model.Cotizante;

import java.util.*;

public class CaracterizacionValidator {
    // Enumeración para categorizar los resultados de validación
    public enum CategoriaValidacion {
        ACEPTADO,
        RECHAZADO,
        EMBARGADO,
        PENDIENTE_REVISION
    }

    // Interfaz para definir reglas de validación
    private interface ReglaCotizante {
        boolean validar(Cotizante cotizante);
        String getMensajeRechazo();
    }

    // Conjunto de reglas de validación
    private List<ReglaCotizante> reglasValidacion;
    private List<ReglaCotizante> reglasEmbargo;

    // Mapas para almacenar resultados de validación
    private Map<CategoriaValidacion, List<Cotizante>> resultadosValidacion;

    public CaracterizacionValidator() {
        inicializarReglas();
        inicializarResultados();
    }

    private void inicializarReglas() {
        // Reglas de validación generales
        reglasValidacion = Arrays.asList(
                new ReglaCotizante() {
                    public boolean validar(Cotizante cotizante) {
                        return !cotizante.isHaEstadoListaNegra();
                    }
                    public String getMensajeRechazo() {
                        return "Registrado en lista negra";
                    }
                },
                new ReglaCotizante() {
                    public boolean validar(Cotizante cotizante) {
                        return cotizante.getSemanasCot() >= 52;
                    }
                    public String getMensajeRechazo() {
                        return "Semanas de cotización insuficientes";
                    }
                },
                new ReglaCotizante() {
                    public boolean validar(Cotizante cotizante) {
                        return cotizante.isAlcanzoEdadRPM();
                    }
                    public String getMensajeRechazo() {
                        return "No ha alcanzado la edad para RPM";
                    }
                },
                new ReglaCotizante() {
                    public boolean validar(Cotizante cotizante) {
                        return !cotizante.isPrePensionado();
                    }
                    public String getMensajeRechazo() {
                        return "Es pre-pensionado";
                    }
                }
        );

        // Reglas de embargo
        reglasEmbargo = Arrays.asList(
                new ReglaCotizante() {
                    public boolean validar(Cotizante cotizante) {
                        return !(cotizante.isTieneHijosInpec() && cotizante.getEdad() < 40);
                    }
                    public String getMensajeRechazo() {
                        return "Hijo de empleado INPEC menor de 40 años";
                    }
                },
                new ReglaCotizante() {
                    public boolean validar(Cotizante cotizante) {
                        return !(cotizante.isTieneFamPolicia() && cotizante.getEdad() < 35);
                    }
                    public String getMensajeRechazo() {
                        return "Familiar de policía con edad restringida";
                    }
                },
                new ReglaCotizante() {
                    public boolean validar(Cotizante cotizante) {
                        return !(cotizante.isPerteneceInsPublica() &&
                                cotizante.getInstitucionPublica() != null);
                    }
                    public String getMensajeRechazo() {
                        return "Pertenece a institución pública";
                    }
                }
        );
    }

    private void inicializarResultados() {
        resultadosValidacion = new EnumMap<>(CategoriaValidacion.class);
        for (CategoriaValidacion categoria : CategoriaValidacion.values()) {
            resultadosValidacion.put(categoria, new ArrayList<>());
        }
    }

    public CategoriaValidacion evaluarCotizante(Cotizante cotizante) {
        // Primero verificamos reglas de embargo
        for (ReglaCotizante regla : reglasEmbargo) {
            if (!regla.validar(cotizante)) {
                resultadosValidacion.get(CategoriaValidacion.EMBARGADO).add(cotizante);
                return CategoriaValidacion.EMBARGADO;
            }
        }

        // Luego validamos las reglas generales
        List<String> motivosRechazo = new ArrayList<>();
        for (ReglaCotizante regla : reglasValidacion) {
            if (!regla.validar(cotizante)) {
                motivosRechazo.add(regla.getMensajeRechazo());
            }
        }

        // Determinamos la categoría final
        CategoriaValidacion categoria;
        if (!motivosRechazo.isEmpty()) {
            categoria = CategoriaValidacion.RECHAZADO;
            // Puedes almacenar los motivos de rechazo si lo necesitas
            resultadosValidacion.get(CategoriaValidacion.RECHAZADO).add(cotizante);
        } else {
            categoria = CategoriaValidacion.ACEPTADO;
            resultadosValidacion.get(CategoriaValidacion.ACEPTADO).add(cotizante);
        }

        return categoria;
    }

    public void procesarCotizantes(List<Cotizante> cotizantes) {
        for (Cotizante cotizante : cotizantes) {
            evaluarCotizante(cotizante);
        }
    }

    // Métodos para obtener los resultados
    public List<Cotizante> obtenerPorCategoria(CategoriaValidacion categoria) {
        return resultadosValidacion.get(categoria);
    }

    // Método para agregar o modificar reglas dinámicamente
    public void agregarReglaValidacion(ReglaCotizante nuevaRegla) {
        reglasValidacion.add(nuevaRegla);
    }

    public void agregarReglaEmbargo(ReglaCotizante nuevaRegla) {
        reglasEmbargo.add(nuevaRegla);
    }
}

