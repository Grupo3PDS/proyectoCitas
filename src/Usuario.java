import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa a la persona que usa la aplicación y es dueña de las citas y categorías.
 *
 * Regla de negocio importante:
 *   En vez de eliminar una cita, se CANCELA.
 *   Solo se pueden cancelar citas en estado PENDIENTE.
 */
public class Usuario {
    private String id;
    private String nombre;
    private String correo;
    private String contrasena;

    // Lista de todas las citas del usuario
    private List<Cita> listaCitas;

    // Lista de todas las categorías personalizadas del usuario
    private List<Categoria> listaCategorias;

    // Constructor
    public Usuario(String id, String nombre, String correo, String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;

        // Inicializamos las listas en blanco
        this.listaCitas = new ArrayList<>();
        this.listaCategorias = new ArrayList<>();
    }

    // --- Funciones para gestionar las citas y categorías directamente ---

    /**
     * Agrega una nueva cita a la lista del usuario.
     * La cita siempre inicia en estado PENDIENTE (definido en el constructor de Cita).
     *
     * @param nuevaCita Cita a agregar.
     */
    public void agregarCita(Cita nuevaCita) {
        listaCitas.add(nuevaCita);
    }

    /**
     * Cancela una cita pendiente.
     * En vez de eliminarla, se cambia su estado a CANCELADA.
     * Solo se puede cancelar si la cita está en estado PENDIENTE.
     *
     * @param citaACancelar Cita que se desea cancelar.
     * @return {@code true} si se canceló exitosamente, {@code false} si no era posible.
     */
    public boolean cancelarCita(Cita citaACancelar) {
        if (!listaCitas.contains(citaACancelar)) return false;
        if (citaACancelar.getEstado() != EstadoCita.PENDIENTE) return false;

        citaACancelar.setEstado(EstadoCita.CANCELADA);
        citaACancelar.setFechaCambioEstado(LocalDateTime.now());
        return true;
    }

    /**
     * @deprecated Usar {@link #cancelarCita(Cita)} en su lugar.
     *             Las citas no deben eliminarse; se cancelan.
     */
    @Deprecated
    public void eliminarCita(Cita citaAEliminar) {
        listaCitas.remove(citaAEliminar);
    }

    public void agregarCategoria(Categoria nuevaCategoria) {
        listaCategorias.add(nuevaCategoria);
    }

    // --- GETTERS y SETTERS ---

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public List<Cita> getListaCitas() { return listaCitas; }

    public List<Categoria> getListaCategorias() { return listaCategorias; }
}
