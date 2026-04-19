package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada de gestionar la conexión con la base de datos MySQL.
 * Utiliza el patrón Singleton para mantener una sola instancia de conexión.
 */
public class ConexionDB {

    // Datos de conexión
    private static final String URL = "jdbc:mysql://localhost:3306/medicronos";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "7200787Sapo!"; // Cambia por tu contraseña de MySQL

    private static Connection conexion = null;

    /**
     * Obtiene la conexión a la base de datos.
     * Si no existe una conexión activa, la crea.
     * @return objeto Connection con la conexión activa
     */
    public static Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
                System.out.println("Conexión exitosa a Medicronos");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Error: Driver MySQL no encontrado");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos");
            e.printStackTrace();
        }
        return conexion;
    }

    /**
     * Cierra la conexión activa con la base de datos.
     */
    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexión cerrada correctamente");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión");
            e.printStackTrace();
        }
    }
}