-- Base de datos Medicronos
CREATE DATABASE IF NOT EXISTS medicronos;
USE medicronos;

-- Tabla de usuarios
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de citas
CREATE TABLE citas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    tipo ENUM('salud', 'estudio', 'estetica', 'tramites') NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    lugar VARCHAR(150),
    descripcion TEXT,
    estado ENUM('pendiente', 'completada', 'cancelada', 'vencida') DEFAULT 'pendiente',
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- Datos de prueba
INSERT INTO usuarios (nombre, email, contrasena)
VALUES ('Santiago Correa', 'correac.santiago@javeriana.edu.co', '1234');

INSERT INTO citas (usuario_id, tipo, fecha, hora, lugar, descripcion, estado)
VALUES
(1, 'salud', '2026-04-25', '10:00:00', 'Clinica Norte', 'Cita medica general', 'pendiente'),
(1, 'estudio', '2026-04-28', '14:00:00', 'Biblioteca Javeriana', 'Asesoria tesis', 'pendiente'),
(1, 'tramites', '2026-04-20', '09:00:00', 'Notaria 5', 'Renovar documentos', 'completada');