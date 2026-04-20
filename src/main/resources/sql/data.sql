-- Datos de prueba para Medicronos

-- Insertar usuario de prueba
INSERT INTO usuarios (nombre, email, contrasena)
VALUES ('Santiago Correa', 'correac.santiago@javeriana.edu.co', '1234');

-- Insertar citas de prueba
INSERT INTO citas (usuario_id, tipo, fecha, hora, lugar, descripcion, estado)
VALUES
(1, 'salud', '2026-04-25', '10:00:00', 'Clinica Norte', 'Cita medica general', 'pendiente'),
(1, 'estudio', '2026-04-28', '14:00:00', 'Biblioteca Javeriana', 'Asesoria tesis', 'pendiente'),
(1, 'tramites', '2026-04-20', '09:00:00', 'Notaria 5', 'Renovar documentos', 'completada');