/**
 * Representa los diferentes estados en los que puede estar una cita.
 * Utilizamos un 'enum' porque los estados son fijos y conocidos.
 */
public enum EstadoCita {
    PENDIENTE,   // La cita aún no ha sucedido
    COMPLETADA,  // La cita ya se llevó a cabo
    CANCELADA,   // La cita fue cancelada por el usuario
    VENCIDA      // La fecha y hora pasaron sin que se marcara como completada
}
