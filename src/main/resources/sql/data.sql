-- Datos de prueba para Medicronos
-- Usamos MERGE INTO para que no falle si los datos ya existen

-- Insertar usuario de prueba (solo si no existe)
MERGE INTO usuarios (id, nombre, email, contrasena)
KEY(id)
VALUES (1, 'Santiago Correa', 'correac.santiago@javeriana.edu.co', '1234');

-- Insertar citas de prueba (solo si no existen)
MERGE INTO citas (id, usuario_id, tipo, fecha, hora, lugar, descripcion, estado)
KEY(id)
VALUES (1, 1, 'salud', '2026-04-25', '10:00:00', 'Clinica Norte', 'Cita medica general', 'pendiente');

MERGE INTO citas (id, usuario_id, tipo, fecha, hora, lugar, descripcion, estado)
KEY(id)
VALUES (2, 1, 'estudio', '2026-04-28', '14:00:00', 'Biblioteca Javeriana', 'Asesoria tesis', 'pendiente');

MERGE INTO citas (id, usuario_id, tipo, fecha, hora, lugar, descripcion, estado)
KEY(id)
VALUES (3, 1, 'tramites', '2026-04-20', '09:00:00', 'Notaria 5', 'Renovar documentos', 'completada');