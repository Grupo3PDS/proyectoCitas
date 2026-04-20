package com.medicronos.database;

import com.medicronos.modelo.Cita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Clase DAO/Repositorio para gestionar las operaciones CRUD de las citas.
 * Utilizamos Spring @Repository para inyección de dependencias.
 */
@Repository
public class CitaDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Mapeador que convierte los resultados SQL a objetos Java.
     */
    private RowMapper<Cita> citaRowMapper = new RowMapper<Cita>() {
        @Override
        public Cita mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Cita(
                rs.getInt("id"),
                rs.getInt("usuario_id"),
                rs.getString("tipo"),
                rs.getString("fecha"),
                rs.getString("hora"),
                rs.getString("lugar"),
                rs.getString("descripcion"),
                rs.getString("estado")
            );
        }
    };

    /**
     * Obtiene todas las citas de un usuario específico.
     * @param usuarioId ID del usuario
     * @return lista de citas del usuario
     */
    public List<Cita> obtenerCitasPorUsuario(int usuarioId) {
        String sql = "SELECT * FROM citas WHERE usuario_id = ? ORDER BY fecha ASC";
        return jdbcTemplate.query(sql, citaRowMapper, usuarioId);
    }

    /**
     * Registra una nueva cita en la base de datos.
     * @param cita objeto Cita con los datos a registrar
     * @return true si se registró correctamente
     */
    public boolean registrarCita(Cita cita) {
        String sql = "INSERT INTO citas (usuario_id, tipo, fecha, hora, lugar, descripcion, estado) " +
                     "VALUES (?, ?, ?, ?, ?, ?, 'pendiente')";
                     
        int filas = jdbcTemplate.update(sql, 
            cita.getUsuarioId(), 
            cita.getTipo(), 
            cita.getFecha(), 
            cita.getHora(), 
            cita.getLugar(), 
            cita.getDescripcion()
        );
        return filas > 0;
    }

    /**
     * Edita los datos de una cita existente.
     * @param cita objeto Cita con los datos actualizados
     * @return true si se editó correctamente
     */
    public boolean editarCita(Cita cita) {
        String sql = "UPDATE citas SET tipo=?, fecha=?, hora=?, lugar=?, descripcion=? WHERE id=?";
        
        int filas = jdbcTemplate.update(sql,
            cita.getTipo(),
            cita.getFecha(),
            cita.getHora(),
            cita.getLugar(),
            cita.getDescripcion(),
            cita.getId()
        );
        return filas > 0;
    }

    /**
     * Cancela una cita cambiando su estado a cancelada.
     * @param citaId ID de la cita a cancelar
     * @return true si se canceló correctamente
     */
    public boolean cancelarCita(int citaId) {
        String sql = "UPDATE citas SET estado = 'cancelada' WHERE id = ?";
        int filas = jdbcTemplate.update(sql, citaId);
        return filas > 0;
    }

    /**
     * Elimina una cita de la base de datos.
     * @param citaId ID de la cita a eliminar
     * @return true si se eliminó correctamente
     */
    public boolean eliminarCita(int citaId) {
        String sql = "DELETE FROM citas WHERE id = ?";
        int filas = jdbcTemplate.update(sql, citaId);
        return filas > 0;
    }
}
