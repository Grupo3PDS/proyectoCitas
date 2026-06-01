package com.medicronos.servicio;

import com.medicronos.modelo.Cita;
import com.medicronos.modelo.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Este módulo se encarga de analizar los datos de las citas y devolver estadísticas.
 * De esta forma separamos la lógica matemática/analítica.
 */
@Service
public class ModuloEstadisticas {

    /**
     * Calcula qué porcentaje de las citas se han completado con éxito.
     * @param usuario Usuario al que se evaluará
     * @return El porcentaje en número decimal (ejemplo: 75.5 porciento)
     */
    public double calcularPorcentajeCompletadas(Usuario usuario) {
        List<Cita> citas = usuario.getListaCitas();
        
        // Para evitar errores matemáticos si el usuario no tiene citas o la lista es nula
        if (citas == null || citas.isEmpty()) {
            return 0.0;
        }

        int completadas = 0;
        
        // Recorremos la lista de citas del usuario
        for (Cita cita : citas) {
            if ("completada".equalsIgnoreCase(cita.getEstado())) {
                completadas++;
            }
        }

        // Operación matemática para sacar porcentaje y retornarlo
        return ((double) completadas / citas.size()) * 100.0;
    }

    /**
     * Cuenta cuántas citas hay en un estado específico (ej. cuántas están 'vencida' o 'pendiente').
     */
    public int contarCitasPorEstado(Usuario usuario, String estadoBuscado) {
        List<Cita> citas = usuario.getListaCitas();
        if (citas == null || citas.isEmpty()) {
            return 0;
        }

        int contador = 0;
        for (Cita cita : citas) {
            if (estadoBuscado.equalsIgnoreCase(cita.getEstado())) {
                contador++;
            }
        }
        return contador;
    }
}
