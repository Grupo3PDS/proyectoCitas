-- Datos de prueba para Medicronos

MERGE INTO usuarios (id, nombre, email, contrasena)
KEY(id)
VALUES (1, 'Santiago Correa', 'correac.santiago@javeriana.edu.co', '1234');

MERGE INTO citas (id, codigo, usuario_id, tipo, fecha, hora, lugar, descripcion, estado)
KEY(id)
VALUES (1, 'MED-0001', 1, 'Medicina General', '2026-04-25', '10:00:00', 'Clinica Norte', 'Cita medica general', 'pendiente');

MERGE INTO citas (id, codigo, usuario_id, tipo, fecha, hora, lugar, descripcion, estado)
KEY(id)
VALUES (2, 'MED-0002', 1, 'Control', '2026-04-28', '14:00:00', 'Biblioteca Javeriana', 'Asesoria tesis', 'pendiente');

MERGE INTO citas (id, codigo, usuario_id, tipo, fecha, hora, lugar, descripcion, estado)
KEY(id)
VALUES (3, 'MED-0003', 1, 'Exámenes de laboratorio', '2026-04-20', '09:00:00', 'Notaria 5', 'Renovar documentos', 'completada');

ALTER TABLE citas ALTER COLUMN id RESTART WITH 4;