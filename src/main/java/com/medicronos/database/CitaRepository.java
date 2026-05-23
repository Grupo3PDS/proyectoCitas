package com.medicronos.database;

import com.medicronos.modelo.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para gestionar las operaciones CRUD de las citas.
 */
@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {

    /**
     * Obtiene todas las citas de un usuario específico.
     * @param usuarioId ID del usuario
     * @return lista de citas del usuario
     */
    List<Cita> findByUsuarioId(int usuarioId);

    /**
     * Elimina una cita por ID.
     * @param id ID de la cita
     */
    void deleteById(int id);
}