package com.medicronos.servicio;

import com.medicronos.database.CitaDao;
import com.medicronos.modelo.Cita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Capa de Servicio: lógica de negocio de las citas.
 */
@Service
public class CitaServicio {

    @Autowired
    private CitaDao citaDao;

    public List<Cita> obtenerCitasUsuario(int usuarioId) {
        return citaDao.obtenerCitasPorUsuario(usuarioId);
    }

    /**
     * Guarda una nueva cita con validaciones de negocio.
     * El CHECK final de horario se hace en el DAO dentro de la transacción ACID.
     */
    public boolean guardarNuevaCita(Cita nuevaCita) {
        if (nuevaCita.getUsuarioId() <= 0) {
            System.out.println("No se puede guardar: usuario no válido.");
            return false;
        }
        if (nuevaCita.getFecha() == null || nuevaCita.getFecha().isBlank()) {
            System.out.println("No se puede guardar: fecha requerida.");
            return false;
        }
        if (nuevaCita.getHora() == null || nuevaCita.getHora().isBlank()) {
            System.out.println("No se puede guardar: hora requerida.");
            return false;
        }
        if (nuevaCita.getEstado() == null || nuevaCita.getEstado().isBlank()) {
            nuevaCita.setEstado("pendiente");
        }
        return citaDao.registrarCita(nuevaCita);
    }
    
    public java.util.List<String> obtenerHorasOcupadas(String fecha) {
        return citaDao.obtenerCitasPorUsuario(1).stream().filter(c -> c.getFecha().equals(fecha)).map(Cita::getHora).toList();
    }
    
    public boolean modificarCita(Cita cita) {
        return citaDao.modificarCita(cita);
    }

    public boolean eliminarCitaDefinitivo(int id) {
        return citaDao.eliminarCita(id);
    }

    /** Cancela una cita pendiente cambiando su estado a 'cancelada' */
    public boolean cancelarCita(int id) {
        return citaDao.cambiarEstado(id, "cancelada");
    }

    /** Marca una cita como completada/asistida */
    public boolean marcarAsistida(int id) {
        return citaDao.cambiarEstado(id, "completada");
    }

    /** Marca una cita como no asistida */
    public boolean marcarNoAsistida(int id) {
        return citaDao.cambiarEstado(id, "no asistida");
    }
}