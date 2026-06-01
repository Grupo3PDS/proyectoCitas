package com.medicronos.modelo;

import jakarta.persistence.*;

/**
 * Clase que representa una cita en el sistema Medicronos.
 */
@Entity
@Table(name = "citas")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false, length = 10)
    private String codigo;

    @Column(name = "usuario_id", nullable = false)
    private int usuarioId;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private String fecha;

    @Column(nullable = false)
    private String hora;

    private String lugar;

    @Column(nullable = false)
    private String estado;

    // Constructor vacío requerido por JPA
    public Cita() {}

    public Cita(int id, String codigo, int usuarioId, String tipo, String fecha,
                String hora, String lugar, String estado) {
        this.id = id;
        this.codigo = codigo;
        this.usuarioId = usuarioId;
        this.tipo = tipo;
        this.fecha = fecha;
        this.hora = hora;
        this.lugar = lugar;
        this.estado = estado;
    }

    public Cita(int usuarioId, String tipo, String fecha,
                String hora, String lugar) {
        this.usuarioId = usuarioId;
        this.tipo = tipo;
        this.fecha = fecha;
        this.hora = hora;
        this.lugar = lugar;
        this.estado = "pendiente";
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Cita [id=" + id + ", codigo=" + codigo + ", tipo=" + tipo +
               ", fecha=" + fecha + ", hora=" + hora + ", estado=" + estado + "]";
    }
}
