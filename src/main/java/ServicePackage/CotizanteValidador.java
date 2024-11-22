package ServicePackage;

import Model.Cotizante;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

public class CotizanteValidador {

    private Queue<Cotizante> cotizantesValidos = new LinkedList<>();

    public Queue<Cotizante> getCotizantesValidos() {
        return cotizantesValidos;
    }

    public List<String> validateAll(Cotizante cotizante) {
        return List.of(
                validarCedula(cotizante.getCedula()),
                validarNombre(cotizante.getNombre()),
                validarEdad(cotizante.getEdad()),
                validarCorreo(cotizante.getCorreo()),
                validarTelefono(cotizante.getTelefono()),
                validarListaNegra(cotizante.isHaEstadoListaNegra()),
                validarPrePensionado(cotizante.isPrePensionado()),
                validarInstitucionPublica(cotizante),
                validarCondecoracion(cotizante.isTieneCondecoracion()),
                validarHijosInpec(cotizante.isTieneHijosInpec()),
                validarFamiliaPolicia(cotizante.isTieneFamPolicia()),
                validarMayorEdad(cotizante.isEsElMayorEdad()),
                validarObservacionDisciplinaria(cotizante.getObservacionDisciplinaria()),
                validarSemanasCotizadas(cotizante.getSemanasCot()),
                validarCiudades(cotizante.getCiudadNacimiento(), cotizante.getCiudadResidencia()),
                validarEdadRPM(cotizante),
                validarFondo(cotizante.getFondo()),
                validarEstado(cotizante.getEstado())
        );
    }

    public String validarCedula(String cedula) {
        if (cedula == null || cedula.trim().isEmpty()) {
            return "La cédula no puede estar vacía";
        }
        // Validación de formato de cédula (puedes personalizar según tus requisitos)
        if (!cedula.matches("^\\d{6,10}$")) {
            return "Formato de cédula inválido";
        }
        return null;
    }

    public String validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return "El nombre no puede estar vacío";
        }
        // Validación de longitud y caracteres
        if (nombre.length() < 2 || !nombre.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+$")) {
            return "Nombre inválido";
        }
        return null;
    }

    public String validarEdad(int edad) {
        if (edad < 18) {
            return "El cotizante debe ser mayor de edad";
        }
        // Límite máximo de edad (ajusta según tus requisitos)
        if (edad > 100) {
            return "Edad fuera de rango válido";
        }
        return null;
    }

    public String validarCorreo(String correo) {
        if (correo == null || correo.trim().isEmpty()) {
            return "El correo no puede estar vacío";
        }
        // Validación de formato de correo más robusta
        if (!correo.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            return "El formato del correo no es válido";
        }
        return null;
    }

    public String validarTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            return "El teléfono no puede estar vacío";
        }
        // Validación de formato de teléfono para Colombia
        if (!telefono.matches("^(\\+57)?\\d{10}$")) {
            return "El formato del teléfono no es válido";
        }
        return null;
    }

    public String validarListaNegra(boolean haEstadoListaNegra) {
        if (haEstadoListaNegra) {
            return "El cotizante ha estado en lista negra";
        }
        return null;
    }

    public String validarPrePensionado(boolean esPrePensionado) {
        if (esPrePensionado) {
            return "El cotizante es pre-pensionado";
        }
        return null;
    }

    public String validarInstitucionPublica(Cotizante cotizante) {
        if (cotizante.isPerteneceInsPublica()) {
            if (cotizante.getInstitucionPublica() == null || cotizante.getInstitucionPublica().trim().isEmpty()) {
                return "Debe especificar la institución pública";
            }
        }
        return null;
    }

    public String validarCondecoracion(boolean tieneCondecoracion) {
        // Puedes agregar lógica específica para condecoraciones si es necesario
        return null;
    }

    public String validarHijosInpec(boolean tieneHijosInpec) {
        // Puedes agregar lógica específica para hijos de Inpec
        return null;
    }

    public String validarFamiliaPolicia(boolean tieneFamPolicia) {
        // Puedes agregar lógica específica para familia de policía
        return null;
    }

    public String validarMayorEdad(boolean esElMayorEdad) {
        // Validación de mayor de edad (puede ser redundante con validarEdad)
        return null;
    }

    public String validarObservacionDisciplinaria(String observacion) {
        // Puedes agregar lógica específica para observaciones disciplinarias
        return null;
    }

    public String validarSemanasCotizadas(int semanasCot) {
        if (semanasCot < 0) {
            return "Las semanas cotizadas no pueden ser negativas";
        }
        // Puedes agregar un límite máximo de semanas si es necesario
        if (semanasCot > 52 * 45) { // Ejemplo: máximo 45 años de cotización
            return "Número de semanas cotizadas excede el límite";
        }
        return null;
    }

    public String validarCiudades(String ciudadNacimiento, String ciudadResidencia) {
        if (ciudadNacimiento == null || ciudadNacimiento.trim().isEmpty()) {
            return "La ciudad de nacimiento no puede estar vacía";
        }
        if (ciudadResidencia == null || ciudadResidencia.trim().isEmpty()) {
            return "La ciudad de residencia no puede estar vacía";
        }
        return null;
    }

    public String validarEdadRPM(Cotizante cotizante) {
        if (!cotizante.isAlcanzoEdadRPM()) {
            return "No ha alcanzado la edad para RPM";
        }
        return null;
    }

    public String validarFondo(String fondo) {
        if (fondo == null || fondo.trim().isEmpty()) {
            return "El fondo no puede estar vacío";
        }
        // Puedes agregar validaciones específicas para fondos permitidos
        return null;
    }

    public String validarEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            return "El estado no puede estar vacío";
        }
        // Puedes agregar validaciones específicas para estados permitidos
        return null;
    }

    public boolean esValido(Cotizante cotizante) {
        List<String> errors = validateAll(cotizante);
        boolean isValid = errors.stream().allMatch(Objects::isNull);
        if (isValid) {
            // Si es válido, lo agregamos a la cola de cotizantes válidos
            cotizantesValidos.add(cotizante);
        }
        return isValid;
    }
}

