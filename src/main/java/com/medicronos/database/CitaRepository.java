package com.medicronos.database;

import com.medicronos.modelo.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para gestionar las operaciones CRUD de las citas.
 */
@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {

    List<Cita> findByUsuarioId(int usuarioId);

    void deleteById(int id);

    /**
     * Verifica si ya existe una cita en una fecha y hora específica
     * sin importar el usuario. Para garantizar horarios únicos globales.
     */
    boolean existsByFechaAndHora(String fecha, String hora);

    /**
     * Verifica si existe una cita en esa fecha/hora excluyendo un ID
     * (útil al editar para no bloquearse a sí misma).
     */
    @Query("SELECT COUNT(c) > 0 FROM Cita c WHERE c.fecha = :fecha AND c.hora = :hora AND c.id <> :id")
    boolean existsByFechaAndHoraAndIdNot(@Param("fecha") String fecha, @Param("hora") String hora, @Param("id") int id);

    /**
     * Obtiene todas las horas ocupadas en una fecha específica.
     */
    @Query("SELECT c.hora FROM Cita c WHERE c.fecha = :fecha")
    List<String> findHorasOcupadasByFecha(@Param("fecha") String fecha);

    /**
     * Obtiene el máximo código numérico existente para generar el siguiente.
     */
    @Query("SELECT MAX(CAST(SUBSTRING(c.codigo, 5) AS int)) FROM Cita c WHERE c.codigo LIKE 'MED-%'")
    Integer findMaxCodigoNumero();
}