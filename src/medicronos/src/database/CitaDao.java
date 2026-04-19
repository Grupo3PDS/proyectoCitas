package database;

import modelo.Cita;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO para gestionar las operaciones CRUD
 * de las citas en la base de datos Medicronos.
 */
public class CitaDao {

    /**
     * Obtiene todas las citas de un usuario específico.
     * @param usuarioId ID del usuario
     * @return lista de citas del usuario
     */
    public List<Cita> obtenerCitasPorUsuario(int usuarioId) {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT * FROM citas WHERE usuario_id = ? ORDER BY fecha ASC";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, usuarioId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Cita cita = new Cita(
                    rs.getInt("id"),
                    rs.getInt("usuario_id"),
                    rs.getString("tipo"),
                    rs.getString("fecha"),
                    rs.getString("hora"),
                    rs.getString("lugar"),
                    rs.getString("descripcion"),
                    rs.getString("estado")
                );
                citas.add(cita);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener citas");
            e.printStackTrace();
        }
        return citas;
    }

    /**
     * Registra una nueva cita en la base de datos.
     * @param cita objeto Cita con los datos a registrar
     * @return true si se registró correctamente
     */
    public boolean registrarCita(Cita cita) {
        String sql = "INSERT INTO citas (usuario_id, tipo, fecha, hora, lugar, descripcion, estado) " +
                     "VALUES (?, ?, ?, ?, ?, ?, 'pendiente')";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cita.getUsuarioId());
            ps.setString(2, cita.getTipo());
            ps.setString(3, cita.getFecha());
            ps.setString(4, cita.getHora());
            ps.setString(5, cita.getLugar());
            ps.setString(6, cita.getDescripcion());

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error al registrar cita");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Edita los datos de una cita existente.
     * @param cita objeto Cita con los datos actualizados
     * @return true si se editó correctamente
     */
    public boolean editarCita(Cita cita) {
        String sql = "UPDATE citas SET tipo=?, fecha=?, hora=?, lugar=?, descripcion=? WHERE id=?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cita.getTipo());
            ps.setString(2, cita.getFecha());
            ps.setString(3, cita.getHora());
            ps.setString(4, cita.getLugar());
            ps.setString(5, cita.getDescripcion());
            ps.setInt(6, cita.getId());

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error al editar cita");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Cancela una cita cambiando su estado a cancelada.
     * @param citaId ID de la cita a cancelar
     * @return true si se canceló correctamente
     */
    public boolean cancelarCita(int citaId) {
        String sql = "UPDATE citas SET estado = 'cancelada' WHERE id = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, citaId);
            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error al cancelar cita");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina una cita de la base de datos.
     * @param citaId ID de la cita a eliminar
     * @return true si se eliminó correctamente
     */
    public boolean eliminarCita(int citaId) {
        String sql = "DELETE FROM citas WHERE id = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, citaId);
            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar cita");
            e.printStackTrace();
            return false;
        }
    }
}