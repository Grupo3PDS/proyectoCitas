package com.medicronos.database;

import com.medicronos.modelo.Cita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Clase DAO/Repositorio para gestionar las operaciones CRUD de las citas.
 * Utilizamos Spring Data JPA.
 */
@Repository
public class CitaDao {

    @Autowired
    private CitaRepository citaRepository;

    /**
     * Obtiene todas las citas de un usuario específico.
     * @param usuarioId ID del usuario
     * @return lista de citas del usuario
     */
    public List<Cita> obtenerCitasPorUsuario(int usuarioId) {
        return citaRepository.findByUsuarioId(usuarioId);
    }

    /**
     * Registra una nueva cita en la base de datos.
     * @param cita objeto Cita con los datos a registrar
     * @return true si se registró correctamente
     */
    public boolean registrarCita(Cita cita) {
        try {
            citaRepository.save(cita);
            return true;
        } catch (Exception e) {
            System.err.println("Error al registrar cita: " + e.getMessage());
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
        try {
            citaRepository.save(cita);
            return true;
        } catch (Exception e) {
            System.err.println("Error al editar cita: " + e.getMessage());
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
        try {
            Cita cita = citaRepository.findById(citaId).orElse(null);
            if (cita != null) {
                cita.setEstado("cancelada");
                citaRepository.save(cita);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error al cancelar cita: " + e.getMessage());
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
        try {
            citaRepository.deleteById(citaId);
            return true;
        } catch (Exception e) {
            System.err.println("Error al eliminar cita: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
