import java.util.List;

/**
 * Este módulo se encarga de analizar los datos de las citas y devolver estadísticas.
 * De esta forma separamos la lógica matemática/analítica.
 */
public class ModuloEstadisticas {

    /**
     * Calcula qué porcentaje de las citas se han completado con éxito.
     * @param usuario Usuario al que se evaluará
     * @return El porcentaje en número decimal (ejemplo: 75.5 porciento)
     */
    public static double calcularPorcentajeCompletadas(Usuario usuario) {
        List<Cita> citas = usuario.getListaCitas();
        
        // Para evitar errores matemáticos si el usuario no tiene citas
        if (citas.isEmpty()) {
            return 0.0;
        }

        int completadas = 0;
        
        // Recorremos la lista de citas del usuario
        for (Cita cita : citas) {
            if (cita.getEstado() == EstadoCita.COMPLETADA) {
                completadas++;
            }
        }

        // Operación matemática para sacar porcentaje y retornarlo
        return ((double) completadas / citas.size()) * 100.0;
    }

    /**
     * Cuenta cuántas citas hay en un estado específico (ej. cuántas están VENCIDAS o PENDIENTES).
     */
    public static int contarCitasPorEstado(Usuario usuario, EstadoCita estadoBuscado) {
        int contador = 0;
        for (Cita cita : usuario.getListaCitas()) {
            if (cita.getEstado() == estadoBuscado) {
                contador++;
            }
        }
        return contador;
    }
}
