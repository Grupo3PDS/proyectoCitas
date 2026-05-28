package com.medicronos.database;

import com.medicronos.modelo.Cita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Clase DAO para gestionar las operaciones CRUD de las citas.
 * Todas las operaciones críticas usan @Transactional para garantizar ACID.
 */
@Repository
public class CitaDao {

    @Autowired
    private CitaRepository citaRepository;

    public List<Cita> obtenerCitasPorUsuario(int usuarioId) {
        return citaRepository.findByUsuarioId(usuarioId);
    }

    /**
     * Genera un código alfanumérico único tipo MED-0001.
     */
    private String generarCodigo() {
        Integer max = citaRepository.findMaxCodigoNumero();
        int siguiente = (max == null ? 0 : max) + 1;
        return String.format("MED-%04d", siguiente);
    }

    /**
     * Registra una nueva cita con verificación ACID de disponibilidad de horario.
     * Usa @Transactional para garantizar atomicidad y rollback en caso de conflicto.
     */
    @Transactional
    public boolean registrarCita(Cita cita) {
        try {
            // CHECK final: verificar que el horario siga disponible justo antes de guardar
            if (citaRepository.existsByFechaAndHora(cita.getFecha(), cita.getHora())) {
                System.out.println(
