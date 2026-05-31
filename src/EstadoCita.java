/**
 * Representa los diferentes estados en los que puede estar una cita.
 * Utilizamos un 'enum' porque los estados son fijos y conocidos.
 *
 * Equivalencias con la interfaz web (maquetaProyecto.html):
 *   PENDIENTE    → "Pendiente"    (amarillo)
 *   ASISTIDA     → "Asistida"     (verde)   — antes COMPLETADA
 *   CANCELADA    → "Cancelada"    (rojo)
 *   NO_ASISTIDA  → "No asistida"  (violeta) — antes VENCIDA
 */
public enum EstadoCita {
    PENDIENTE,    // La cita aún no ha sucedido
    ASISTIDA,     // El paciente asistió a la cita (antes COMPLETADA)
    CANCELADA,    // La cita fue cancelada por el usuario
    NO_ASISTIDA   // La fecha y hora pasaron sin que el paciente se presentara (antes VENCIDA)
}
