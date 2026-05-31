import java.util.List;

/**
 * Este módulo se encarga de analizar los datos de las citas y devolver estadísticas.
 * De esta forma separamos la lógica matemática/analítica del resto de la aplicación.
 *
 * Regla de visibilidad (espejada en la interfaz web):
 *   Las citas CANCELADAS o NO_ASISTIDAS se muestran automáticamente solo
 *   durante las 48 horas siguientes al cambio de estado; después, quedan
 *   ocultas a menos que el usuario las busque explícitamente.
 */
public class ModuloEstadisticas {

    /**
     * Calcula la tasa de cumplimiento: porcentaje de citas asistidas
     * sobre el total de citas que ya tienen un estado final
     * (ASISTIDA + CANCELADA + NO_ASISTIDA).
     *
     * @param usuario Usuario al que se evaluará.
     * @return Porcentaje en número decimal (p. ej. 75.5 para 75,5 %).
     */
    public static double calcularTasaCumplimiento(Usuario usuario) {
        List<Cita> citas = usuario.getListaCitas();

        if (citas.isEmpty()) return 0.0;

        long finalizadas = citas.stream()
                .filter(c -> c.getEstado() == EstadoCita.ASISTIDA
                          || c.getEstado() == EstadoCita.CANCELADA
                          || c.getEstado() == EstadoCita.NO_ASISTIDA)
                .count();

        if (finalizadas == 0) return 0.0;

        long asistidas = citas.stream()
                .filter(c -> c.getEstado() == EstadoCita.ASISTIDA)
                .count();

        return ((double) asistidas / finalizadas) * 100.0;
    }

    /**
     * Porcentaje de citas ASISTIDAS sobre el total (incluye pendientes).
     * Equivalente al método original {@code calcularPorcentajeCompletadas}.
     *
     * @param usuario Usuario al que se evaluará.
     * @return Porcentaje en número decimal.
     */
    public static double calcularPorcentajeAsistidas(Usuario usuario) {
        List<Cita> citas = usuario.getListaCitas();
        if (citas.isEmpty()) return 0.0;

        long asistidas = citas.stream()
                .filter(c -> c.getEstado() == EstadoCita.ASISTIDA)
                .count();

        return ((double) asistidas / citas.size()) * 100.0;
    }

    /**
     * Cuenta cuántas citas hay en un estado específico.
     *
     * @param usuario       Usuario a analizar.
     * @param estadoBuscado Estado que se quiere contar.
     * @return Número de citas con ese estado.
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

    /**
     * Cuenta cuántas citas hay de un tipo determinado.
     *
     * @param usuario   Usuario a analizar.
     * @param tipoCita  Tipo de cita que se quiere contar.
     * @return Número de citas de ese tipo.
     */
    public static int contarCitasPorTipo(Usuario usuario, TipoCita tipoCita) {
        int contador = 0;
        for (Cita cita : usuario.getListaCitas()) {
            if (cita.getTipoCita() == tipoCita) {
                contador++;
            }
        }
        return contador;
    }
}
