package com.medicronos.controlador;

import com.medicronos.modelo.Cita;
import com.medicronos.servicio.CitaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controlador REST para las citas.
 */
@RestController
@RequestMapping("/api/citas")
public class CitaControlador {

    @Autowired
    private CitaServicio citaServicio;

    @GetMapping("/usuario/{usuarioId}")
    public List<Cita> listarCitas(@PathVariable int usuarioId) {
        return citaServicio.obtenerCitasUsuario(usuarioId);
    }

    @GetMapping
    public List<Cita> listarCitasPorDefecto() {
        return citaServicio.obtenerCitasUsuario(1);
    }

    /**
     * Endpoint para obtener las horas disponibles en una fecha específica.
     * Devuelve lista de horas de 08:00 a 20:00 en intervalos de 15 minutos
     * excluyendo las que ya están ocupadas.
     * Ruta: GET /api/citas/horarios-disponibles?fecha=2026-05-30
     */
    @GetMapping("/horarios-disponibles")
    public List<String> obtenerHorariosDisponibles(@RequestParam String fecha) {
        List<String> horasOcupadas = citaServicio.obtenerHorasOcupadas(fecha);

        List<String> todosLosHorarios = new java.util.ArrayList<>();
        for (int h = 8; h < 20; h++) {
            for (int m = 0; m < 60; m += 15) {
                String horario = String.format("%02d:%02d", h, m);
                todosLosHorarios.add(horario);
            }
        }

        // Filtrar los ocupados
        todosLosHorarios.removeIf(h -> horasOcupadas.stream()
            .anyMatch(ocupada -> ocupada.startsWith(h)));

        return todosLosHorarios;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> crearCita(@RequestBody Cita nuevaCita) {
        boolean exito = citaServicio.guardarNuevaCita(nuevaCita);
        if (exito) {
            return ResponseEntity.status(201).body(Map.of("mensaje", "Cita agendada correctamente"));
        } else {
            return ResponseEntity.status(409).body(Map.of("error", "El horario seleccionado ya no está disponible. Por favor elija otro."));
        }
    }

    @PutMapping
    public ResponseEntity<Map<String, String>> editarCita(@RequestBody Cita cita) {
        boolean exito = citaServicio.modificarCita(cita);
        if (exito) {
            return ResponseEntity.ok(Map.of("mensaje", "Cita actualizada correctamente"));
        } else {
            return ResponseEntity.status(400).body(Map.of("error", "No se pudo actualizar la cita"));
        }
    }

    /** Cancela una cita pendiente (en vez de eliminarla) */
    @PatchMapping("/cancelar/{id}")
    public ResponseEntity<String> cancelarCita(@PathVariable int id) {
        boolean exito = citaServicio.cancelarCita(id);
        return exito
            ? ResponseEntity.ok("Cita cancelada correctamente")
            : ResponseEntity.status(400).body("No se pudo cancelar la cita");
    }

    @PatchMapping("/asistir/{id}")
    public ResponseEntity<String> marcarAsistida(@PathVariable int id) {
        boolean exito = citaServicio.marcarAsistida(id);
        return exito
            ? ResponseEntity.ok("Cita marcada como completada")
            : ResponseEntity.status(400).body("No se pudo actualizar el estado");
    }

    @PatchMapping("/no-asistida/{id}")
    public ResponseEntity<String> marcarNoAsistida(@PathVariable int id) {
        boolean exito = citaServicio.marcarNoAsistida(id);
        return exito
            ? ResponseEntity.ok("Cita marcada como no asistida")
            : ResponseEntity.status(400).body("No se pudo actualizar el estado");
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<String> eliminarCita(@PathVariable int id) {
        boolean exito = citaServicio.eliminarCitaDefinitivo(id);
        return exito
            ? ResponseEntity.ok("Cita eliminada")
            : ResponseEntity.status(400).body("No se encontró la cita");
    }
}
