package com.medicronos.servicio;

import com.medicronos.database.UsuarioDao;
import com.medicronos.modelo.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Servicio de negocio para la gestión de usuarios, implementando las políticas
 * de inicio de sesión y de registro secuencial transaccional (ACID).
 */
@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioDao usuarioDao;

    /**
     * Valida las credenciales de inicio de sesión de un usuario.
     * Cumple con ACID (lectura de consistencia transaccional).
     * 
     * @param correo Correo electrónico del usuario
     * @param contrasena Contraseña del usuario
     * @return El objeto Usuario si la autenticación es exitosa
     * @throws Exception Excepción detallada de negocio ("el correo no esta registrado" o "Contraseña incorrecta")
     */
    @Transactional(readOnly = true)
    public Usuario login(String correo, String contrasena) throws Exception {
        if (correo == null || correo.isBlank()) {
            throw new Exception("El correo es requerido");
        }
        
        Optional<Usuario> optUsuario = usuarioDao.obtenerPorCorreo(correo.trim());
        if (optUsuario.isEmpty()) {
            throw new Exception("el correo no esta registrado");
        }

        Usuario usuario = optUsuario.get();
        if (!usuario.getContrasena().equals(contrasena)) {
            throw new Exception("Contraseña incorrecta");
        }

        return usuario;
    }

    /**
     * Registra un nuevo usuario en la base de datos de manera transaccional.
     * Usa Isolation.SERIALIZABLE para garantizar atomicidad y aislamiento consistentes (ACID)
     * e impedir colisiones concurrentes al generar el ID (+1 del anterior).
     * 
     * @param nombre Nombre del usuario
     * @param correo Correo electrónico del usuario
     * @param contrasena Contraseña del usuario
     * @return El usuario registrado
     * @throws Exception Excepción si el correo ya está asociado o hay fallos
     */
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public Usuario registrar(String nombre, String correo, String contrasena) throws Exception {
        if (nombre == null || nombre.isBlank() || correo == null || correo.isBlank() || contrasena == null || contrasena.isBlank()) {
            throw new Exception("Todos los campos son obligatorios");
        }

        String correoLimpio = correo.trim();

        // 1. Verificar si el correo ya existe en la base de datos
        Optional<Usuario> optExistente = usuarioDao.obtenerPorCorreo(correoLimpio);
        if (optExistente.isPresent()) {
            throw new Exception("Correo ya asociado");
        }

        // 2. Obtener el ID máximo y sumar +1 (Transaccionalidad serializada evita carreras de condiciones)
        int nuevoId = usuarioDao.obtenerMaxId() + 1;

        // 3. Crear el nuevo usuario
        Usuario nuevoUsuario = new Usuario(nuevoId, nombre.trim(), correoLimpio, contrasena);

        // 4. Guardar en base de datos
        boolean guardado = usuarioDao.guardarUsuario(nuevoUsuario);
        if (!guardado) {
            throw new Exception("No se pudo persistir el usuario en la base de datos");
        }

        return nuevoUsuario;
    }
}
