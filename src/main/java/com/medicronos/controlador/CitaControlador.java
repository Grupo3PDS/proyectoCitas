package com.medicronos.controlador;

import com.medicronos.modelo.Cita;
import com.medicronos.servicio.CitaServicio;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String crearCita(@RequestBody Cita nuevaCita) {
        boolean exito = citaServicio.guardarNuevaCita(nuevaCita);
        
        // Retornamos un mensaje de confirmación súper sencillo
        if(exito){
            return "Cita agendada correctamente";
        } else {
            return "Error al intentar crear la cita. Verifique sus datos.";
        }
    }

    /**
     * Endpoint para actualizar una cita existente.
     * Ruta final: PUT /api/citas
     */
    @PutMapping
    public String actualizarCita(@RequestBody Cita citaModificada) {
        boolean exito = citaServicio.modificarCita(citaModificada);
        
        if(exito){
            return "Cita modificada correctamente";
        } else {
            return "Error al modificar la cita.";
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
