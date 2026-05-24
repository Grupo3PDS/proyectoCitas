package com.medicronos.controlador;

import com.medicronos.modelo.Cita;
import com.medicronos.servicio.CitaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST: Es el encargado de recibir las peticiones de internet 
 * (por ejemplo, cuando tu página web HTML le pide datos por JavaScript)
 * y darle una respuesta. Todo esto sin conectarse a la Base de Datos,
 * pues para eso utiliza el Servicio.
 */
@RestController // Indicamos que envía y recibe información (usualmente JSON)
@RequestMapping("/api/citas") // Define la URL base, esto cuadra con lo que tienes en maquetaProyecto.html
public class CitaControlador {

    // Se inyecta (conecta) la capa de servicio
    @Autowired
    private CitaServicio citaServicio;

    /**
     * Endpoint para obtener todas las citas de un usuario simulado.
     * En el futuro, el ID vendrá de la sesión iniciada.
     * Ruta final: GET /api/citas/usuario/1
     */
    @GetMapping("/usuario/{usuarioId}")
    public List<Cita> listarCitas(@PathVariable int usuarioId) {
        // Le dice al servicio que busque las citas, y las devuelve al navegador en formato JSON.
        return citaServicio.obtenerCitasUsuario(usuarioId);
    }

    // Ruta comodín para que el JavaScript de tu HTML (que llama a /api/citas) pueda mostrar resultados de prueba
    @GetMapping
    public List<Cita> listarCitasPorDefecto() {
        // En un caso real sacaríamos esto del token de sesión. Aquí simularemos buscar el usuario 1.
        return citaServicio.obtenerCitasUsuario(1);
    }

    /**
     * Endpoint para crear una cita nueva.
     * Ruta final: POST /api/citas
     */
    @PostMapping
    public ResponseEntity<String> crearCita(@RequestBody Cita nuevaCita) {
        boolean exito = citaServicio.guardarNuevaCita(nuevaCita);
        
        if (exito) {
            return ResponseEntity.status(201).body("Cita agendada correctamente");
        } else {
            return ResponseEntity.badRequest().body("Error al intentar crear la cita. Verifique sus datos.");
        }
    }

    /**
     * Endpoint para actualizar una cita existente.
     * Ruta final: PUT /api/citas
     */
    @PutMapping
    public ResponseEntity<String> actualizarCita(@RequestBody Cita citaModificada) {
        // Si no trae estado (frontend no lo envía), lo preservamos buscándolo primero
        if (citaModificada.getEstado() == null || citaModificada.getEstado().isBlank()) {
            citaModificada.setEstado("pendiente");
        }
        boolean exito = citaServicio.modificarCita(citaModificada);
        
        if (exito) {
            return ResponseEntity.ok("Cita modificada correctamente");
        } else {
            return ResponseEntity.badRequest().body("Error al modificar la cita.");
        }
    }

    /**
     * Endpoint para cancelar la cita cambiando su estado.
     * Ruta final: PATCH /api/citas/cancelar/5
     */
    @PatchMapping("/cancelar/{idCita}")
    public String cancelar(@PathVariable int idCita) {
        boolean exito = citaServicio.cancelarCita(idCita);
        
        if(exito) {
            return "Cita cancelada correctamente";
        } else {
            return "No se pudo cancelar la cita";
        }
    }

    /**
     * Endpoint para marcar una cita como asistida.
     * Ruta final: PATCH /api/citas/asistir/5
     */
    @PatchMapping("/asistir/{idCita}")
    public ResponseEntity<String> asistir(@PathVariable int idCita) {
        boolean exito = citaServicio.cambiarEstadoAsistencia(idCita, "asistida");
        
        if(exito) {
            return ResponseEntity.ok("Cita marcada como asistida");
        } else {
            return ResponseEntity.badRequest().body("No se pudo actualizar la asistencia");
        }
    }

    /**
     * Endpoint para marcar una cita como no asistida automáticamente.
     * Ruta final: PATCH /api/citas/no-asistida/5
     */
    @PatchMapping("/no-asistida/{idCita}")
    public ResponseEntity<String> noAsistida(@PathVariable int idCita) {
        boolean exito = citaServicio.cambiarEstadoAsistencia(idCita, "no asistida");
        
        if(exito) {
            return ResponseEntity.ok("Cita expirada y marcada como no asistida");
        } else {
            return ResponseEntity.badRequest().body("No se pudo actualizar el estado");
        }
    }

    /**
     * Endpoint para borrar permanentemente una cita.
     * Ruta final: DELETE /api/citas/borrar/5
     */
    @DeleteMapping("/borrar/{idCita}")
    public String borrarDefinitivo(@PathVariable int idCita) {
        boolean exito = citaServicio.eliminarCitaDefinitivo(idCita);
        
        if(exito) {
            return "Cita borrada permanentemente";
        } else {
            return "No se pudo borrar la cita";
        }
    }
}
