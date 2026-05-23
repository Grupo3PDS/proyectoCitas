package com.medicronos.database;

import com.medicronos.modelo.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Clase DAO para gestionar operaciones CRUD de usuarios, encapsulando el repositorio JPA.
 */
@Repository
public class UsuarioDao {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Busca un usuario por su ID primario.
     * @param id ID del usuario
     * @return Optional con el usuario si se encuentra
     */
    public Optional<Usuario> obtenerPorId(int id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Busca un usuario por su correo electrónico.
     * @param correo Correo electrónico
     * @return Optional con el usuario si se encuentra
     */
    public Optional<Usuario> obtenerPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    /**
     * Obtiene el ID máximo de usuario registrado en la base de datos.
     * @return El ID máximo, o 0 si no hay usuarios.
     */
    public int obtenerMaxId() {
        return usuarioRepository.findMaxId();
    }

    /**
     * Guarda o actualiza un usuario en la base de datos.
     * @param usuario El objeto usuario
     * @return true si se guardó correctamente, false en caso contrario
     */
    public boolean guardarUsuario(Usuario usuario) {
        try {
            usuarioRepository.save(usuario);
            return true;
        } catch (Exception e) {
            System.err.println("Error al guardar usuario en DAO: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
