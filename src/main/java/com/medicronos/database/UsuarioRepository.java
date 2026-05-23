package com.medicronos.database;

import com.medicronos.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    /**
     * Busca un usuario por su correo electrónico.
     * @param correo Correo electrónico a buscar
     * @return Un Optional con el usuario si se encuentra
     */
    Optional<Usuario> findByCorreo(String correo);

    /**
     * Consulta el ID máximo actual de la tabla usuarios.
     * @return ID máximo, o 0 si la tabla está vacía
     */
    @Query("SELECT COALESCE(MAX(u.id), 0) FROM Usuario u")
    int findMaxId();
}
