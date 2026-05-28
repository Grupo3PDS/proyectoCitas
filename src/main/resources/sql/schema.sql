-- Esquema de base de datos Medicronos para H2

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
    codigo VARCHAR(10) UNIQUE NOT NULL,
    usuario_id INT NOT NULL,
    tipo VARCHAR(50) NOT NULL CHECK (tipo IN (
        'Odontología', 'Optometría', 'Medicina General', 'Pediatría',
        'Ginecología', 'Cardiología', 'Dermatología', 'Psicología',
        'Exámenes de laboratorio', 'Control', 'Cirugía', 'Urgencias', 'Otro'
    )),
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    lugar VARCHAR(150),
    descripcion TEXT,
    estado VARCHAR(20) DEFAULT 'pendiente' CHECK (estado IN ('pendiente', 'completada', 'cancelada', 'vencida', 'asistida', 'no asistida')),
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT uq_fecha_hora UNIQUE (fecha, hora)
);