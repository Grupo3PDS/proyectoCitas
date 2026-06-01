package com.medicronos.controlador;

import com.medicronos.modelo.Usuario;
import com.medicronos.modelo.Cita;
import com.medicronos.servicio.UsuarioServicio;
import com.medicronos.servicio.CitaServicio;
import com.medicronos.servicio.ModuloEstadisticas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticasControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private CitaServicio citaServicio;

    @Autowired
    private ModuloEstadisticas moduloEstadisticas;

    @GetMapping("/usuario/{usuarioId}")
    public Map<String, Object> obtenerEstadisticas(
            @PathVariable int usuarioId,
            @RequestParam(required = false) Integer mes) {

        Optional<Usuario> optUsuario = usuarioServicio.obtenerPorId(usuarioId);
        if (optUsuario.isEmpty()) {
            return Map.of("error", "Usuario no encontrado");
        }

        Usuario usuario = optUsuario.get();
        List<Cita> citas = citaServicio.obtenerCitasUsuario(usuarioId);

        // Filtrar por mes si se proporcionó (mes 1=enero ... 12=diciembre)
        if (mes != null) {
            citas = citas.stream()
                .filter(c -> {
                    try {
                        int mesCita = java.time.LocalDate.parse(c.getFecha()).getMonthValue();
                        return mesCita == mes;
                    } catch (Exception e) { return false; }
                })
                .toList();
        }

        for (Cita c : citas) {
            usuario.agregarCita(c);
        }

        double porcentaje = moduloEstadisticas.calcularPorcentajeCompletadas(usuario);
        int pendientes = moduloEstadisticas.contarCitasPorEstado(usuario, "pendiente");
        int completadas = moduloEstadisticas.contarCitasPorEstado(usuario, "completada");
        int canceladas = moduloEstadisticas.contarCitasPorEstado(usuario, "cancelada") + 
                         moduloEstadisticas.contarCitasPorEstado(usuario, "no asistida");
        int total = citas.size();

        Map<String, Long> distribucionPorTipo = citas.stream()
                .collect(java.util.stream.Collectors.groupingBy(Cita::getTipo, java.util.stream.Collectors.counting()));

        return Map.of(
            "porcentajeCompletadas", porcentaje,
            "pendientes", pendientes,
            "completadas", completadas,
            "canceladas", canceladas,
            "total", total,
            "distribucionTipo", distribucionPorTipo
        );
    }
}
