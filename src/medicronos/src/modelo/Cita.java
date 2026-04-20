package modelo;

/**
 * Clase que representa una cita en el sistema Medicronos.
 * Contiene todos los atributos de una cita personal.
 */
public class Cita {

    private int id;
    private int usuarioId;
    private String tipo;
    private String fecha;
    private String hora;
    private String lugar;
    private String descripcion;
    private String estado;

    /**
     * Constructor con todos los atributos
     */
    public Cita(int id, int usuarioId, String tipo, String fecha,
                String hora, String lugar, String descripcion, String estado) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.tipo = tipo;
        this.fecha = fecha;
        this.hora = hora;
        this.lugar = lugar;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    /**
     * Constructor sin id (para crear nuevas citas)
     */
    public Cita(int usuarioId, String tipo, String fecha,
                String hora, String lugar, String descripcion) {
        this.usuarioId = usuarioId;
        this.tipo = tipo;
        this.fecha = fecha;
        this.hora = hora;
        this.lugar = lugar;
        this.descripcion = descripcion;
        this.estado = "pendiente";
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    /**
     * Representación en texto de la cita
     */
    @Override
    public String toString() {
        return "Cita [id=" + id + ", tipo=" + tipo + ", fecha=" + fecha +
               ", hora=" + hora + ", lugar=" + lugar + ", estado=" + estado + "]";
    }
}