package com.medicronos.controlador;

import com.medicronos.modelo.Usuario;
import com.medicronos.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controlador REST para gestionar la autenticación y registro de usuarios.
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    /**
     * Endpoint para iniciar sesión.
     * POST /api/usuarios/login
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        String correo = credenciales.get("correo");
        String contrasena = credenciales.get("contrasena");

        try {
            Usuario usuario = usuarioServicio.login(correo, contrasena);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            String mensaje = e.getMessage();
            if ("el correo no esta registrado".equals(mensaje)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", mensaje));
            } else if ("Contraseña incorrecta".equals(mensaje)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", mensaje));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", mensaje));
            }
        }
    }

    /**
     * Endpoint para registrar un usuario nuevo.
     * POST /api/usuarios/registrar
     */
    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody Map<String, String> datos) {
        String nombre = datos.get("nombre");
        String correo = datos.get("correo");
        String contrasena = datos.get("contrasena");
        String confirmarContrasena = datos.get("confirmarContrasena");

        if (contrasena == null || !contrasena.equals(confirmarContrasena)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "no coinciden"));
        }

        try {
            Usuario usuario = usuarioServicio.registrar(nombre, correo, contrasena);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
        } catch (Exception e) {
            String mensaje = e.getMessage();
            if ("Correo ya asociado".equals(mensaje)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", mensaje));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", mensaje));
            }
        }
    }

    /**
     * Obtiene los datos de perfil de un usuario por su ID.
     * GET /api/usuarios/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUsuario(@PathVariable int id) {
        return usuarioServicio.obtenerPorId(id)
                .map(usuario -> ResponseEntity.ok((Object) usuario))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Usuario no encontrado")));
    }

    /**
     * Cambia la contraseña de un usuario.
     * PUT /api/usuarios/cambiar-contrasena
     */
    @PutMapping("/cambiar-contrasena")
    public ResponseEntity<?> cambiarContrasena(@RequestBody Map<String, String> payload) {
        try {
            int usuarioId = Integer.parseInt(payload.getOrDefault("usuarioId", "0"));
            String actual = payload.get("contrasenaActual");
            String nueva = payload.get("nuevaContrasena");
            usuarioServicio.cambiarContrasena(usuarioId, actual, nueva);
            return ResponseEntity.ok(Map.of("mensaje", "Contraseña actualizada correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }
}
