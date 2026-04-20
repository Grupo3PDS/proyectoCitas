package com.medicronos.servicio;

import com.medicronos.database.CitaDao;
import com.medicronos.modelo.Cita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Capa de Servicio: Aquí reside la lógica principal (las reglas del negocio).
 * Su función principal es servir como un puente seguro entre el Controlador
 * (que recibe las peticiones HTTP) y el DAO (que habla con la base de datos).
 */
@Service // Le indicamos a Spring que este es un servicio
public class CitaServicio {

    // Spring inyecta (conecta) automáticamente el DAO aquí para poder usarlo.
    @Autowired
    private CitaDao citaDao;

    /**
     * Trae la lista de citas de un usuario.
     * @param usuarioId El id del usuario del que se quieren las citas.
     * @return Las citas recuperadas de la base de datos.
     */
    public List<Cita> obtenerCitasUsuario(int usuarioId) {
        // En escenarios reales aquí se puede revisar si el usuario existe antes de consultar
        return citaDao.obtenerCitasPorUsuario(usuarioId);
    }

    /**
     * Lógica para guardar una nueva cita.
     * @param nuevaCita El objeto cita que el usuario llenó en la página web.
     * @return verdadero si fue exitoso, falso si hubo fallas.
     */
    public boolean guardarNuevaCita(Cita nuevaCita) {
        // Validamos que por lo menos la cita tenga un paciente (usuario) asignado
        if (nuevaCita.getUsuarioId() <= 0) {
            System.out.println("No se puede guardar: El usuario no es válido.");
            return false;
        }
        
        // Llamamos a la base de datos para registrarla
        return citaDao.registrarCita(nuevaCita);
    }

    /**
     * Lógica para actualizar información de una cita que ya existe.
     * @param citaActualizar cita con la información renovada.
     * @return verdadero si se actualizó, falso en caso contrario.
     */
    public boolean modificarCita(Cita citaActualizar) {
        // Validamos que tenga un ID real que podamos buscar en la base de datos
        if (citaActualizar.getId() <= 0) {
            System.out.println("Error: La cita a modificar no tiene un ID válido.");
            return false;
        }
        
        return citaDao.editarCita(citaActualizar);
    }

    /**
     * Cancela la cita sin borrarla de la base de datos. Útil para mantener histórico.
     * @param citaId ID de la cita.
     * @return estado de la operación.
     */
    public boolean cancelarCita(int citaId) {
        return citaDao.cancelarCita(citaId);
    }

    /**
     * Elimina definitivamente una cita del sistema.
     * @param citaId ID de cita.
     * @return estado de la operación.
     */
    public boolean eliminarCitaDefinitivo(int citaId) {
        return citaDao.eliminarCita(citaId);
    }
}
