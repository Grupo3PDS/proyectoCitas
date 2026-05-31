import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Representa un compromiso o tarea programada. Es el núcleo principal de la aplicación.
 *
 * Estados posibles (ver {@link EstadoCita}):
 *   PENDIENTE    → cita agendada, aún no ocurre
 *   ASISTIDA     → el paciente asistió
 *   CANCELADA    → cancelada por el usuario (solo desde PENDIENTE)
 *   NO_ASISTIDA  → la hora pasó sin que el paciente se presentara
 *
 * Regla de visibilidad en la UI:
 *   Las citas CANCELADAS y NO_ASISTIDAS se muestran automáticamente solo
 *   durante las 48 horas posteriores al cambio de estado (fechaCambioEstado).
 */
public class Cita {
    private String id;
    private String titulo;
    private String descripcion;
    private LocalDate fecha;              // Solo fecha (Año, Mes, Día)
    private LocalTime hora;              // Solo hora (Hora y Minutos)
    private EstadoCita estado;           // Estado actual de la cita
    private Prioridad prioridad;         // Alta, Media, Baja
    private Categoria categoria;         // Categoría a la que pertenece
    private TipoCita tipoCita;           // Salud, Estudio, Estética, etc.

    /**
     * Lugar de la cita. Está limitado a los 4 espacios predefinidos:
     *   "Clínica Norte", "Clínica Sur", "Clínica Este", "Clínica Oeste"
     */
    private String lugar;

    /**
     * Momento exacto en que la cita cambió a CANCELADA o NO_ASISTIDA.
     * Se usa para aplicar la regla de visibilidad de 48 horas.
     * Es null mientras la cita esté en estado PENDIENTE o ASISTIDA.
     */
    private LocalDateTime fechaCambioEstado;

    // Constructor
    public Cita(String id, String titulo, String descripcion, LocalDate fecha, LocalTime hora,
                Prioridad prioridad, Categoria categoria, TipoCita tipoCita, String lugar) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.hora = hora;
        this.prioridad = prioridad;
        this.categoria = categoria;
        this.tipoCita = tipoCita;
        this.lugar = lugar;

        // Toda cita nueva empieza como PENDIENTE por defecto
        this.estado = EstadoCita.PENDIENTE;
        this.fechaCambioEstado = null;
    }

    // --- GETTERS y SETTERS ---

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }

    public EstadoCita getEstado() { return estado; }
    public void setEstado(EstadoCita estado) { this.estado = estado; }

    public Prioridad getPrioridad() { return prioridad; }
    public void setPrioridad(Prioridad prioridad) { this.prioridad = prioridad; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public TipoCita getTipoCita() { return tipoCita; }
    public void setTipoCita(TipoCita tipoCita) { this.tipoCita = tipoCita; }

    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }

    public LocalDateTime getFechaCambioEstado() { return fechaCambioEstado; }
    public void setFechaCambioEstado(LocalDateTime fechaCambioEstado) { this.fechaCambioEstado = fechaCambioEstado; }
}
