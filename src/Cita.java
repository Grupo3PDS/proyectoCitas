import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Representa un compromiso o tarea programada. Es el núcleo principal de la aplicación.
 */
public class Cita {
    private String id;
    private String titulo;
    private String descripcion;
    private LocalDate fecha; // Solo fecha (Año, Mes, Día)
    private LocalTime hora;  // Solo hora (Hora y Minutos)
    private EstadoCita estado; // Pendiente, Completada, etc.
    private Prioridad prioridad; // Alta, Media, Baja
    private Categoria categoria; // A qué categoría pertenece
    private TipoCita tipoCita; // Salud, Estudio, Estética, etc.

    // Constructor: Función que se llama para crear una nueva 'Cita'
    public Cita(String id, String titulo, String descripcion, LocalDate fecha, LocalTime hora, 
                Prioridad prioridad, Categoria categoria, TipoCita tipoCita) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.hora = hora;
        this.prioridad = prioridad;
        this.categoria = categoria;
        this.tipoCita = tipoCita;
        
        // Toda cita nueva empieza como PENDIENTE por defecto
        this.estado = EstadoCita.PENDIENTE; 
    }

    // --- GETTERS (Para leer las variables) y SETTERS (Para editalas) ---

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
}
