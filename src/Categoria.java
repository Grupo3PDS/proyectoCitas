/**
 * Sirve para clasificar las citas de un usuario.
 * Por ejemplo: Trabajo, Universidad, Salud, Familia, etc.
 */
public class Categoria {
    private String id;
    private String nombre;
    private String descripcion;
    // Opcionalmente se podría agregar un color en formato HEX para la interfaz web luego
    private String colorHex;

    // Constructor para inicializar una categoría
    public Categoria(String id, String nombre, String descripcion, String colorHex) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.colorHex = colorHex;
    }

    // Getters y Setters para acceder y modificar las variables

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }
}
