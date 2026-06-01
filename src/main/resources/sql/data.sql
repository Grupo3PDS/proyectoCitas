-- Datos de prueba para Medicronos (H2)

MERGE INTO usuarios (id, nombre, email, contrasena)
KEY(id)
VALUES (1, 'Santiago Correa', 'correac.santiago@javeriana.edu.co', '1234');

ALTER TABLE usuarios ALTER COLUMN id RESTART WITH 2;

-- ================== ENERO 2026 ==================
INSERT INTO citas (codigo, usuario_id, tipo, fecha, hora, lugar, estado)
SELECT 'MED-0001', 1, 'Medicina General', '2026-01-08', '09:00', 'Clínica Norte', 'completada'
WHERE NOT EXISTS (SELECT 1 FROM citas WHERE codigo = 'MED-0001');

INSERT INTO citas (codigo, usuario_id, tipo, fecha, hora, lugar, estado)
SELECT 'MED-0002', 1, 'Odontología', '2026-01-15', '11:00', 'Clínica Sur', 'completada'
WHERE NOT EXISTS (SELECT 1 FROM citas WHERE codigo = 'MED-0002');

INSERT INTO citas (codigo, usuario_id, tipo, fecha, hora, lugar, estado)
SELECT 'MED-0003', 1, 'Control', '2026-01-22', '14:00', 'Clínica Este', 'cancelada'
WHERE NOT EXISTS (SELECT 1 FROM citas WHERE codigo = 'MED-0003');

-- ================== FEBRERO 2026 ==================
INSERT INTO citas (codigo, usuario_id, tipo, fecha, hora, lugar, estado)
SELECT 'MED-0004', 1, 'Cardiología', '2026-02-05', '08:00', 'Clínica Oeste', 'completada'
WHERE NOT EXISTS (SELECT 1 FROM citas WHERE codigo = 'MED-0004');

INSERT INTO citas (codigo, usuario_id, tipo, fecha, hora, lugar, estado)
SELECT 'MED-0005', 1, 'Pediatría', '2026-02-14', '10:30', 'Clínica Norte', 'completada'
WHERE NOT EXISTS (SELECT 1 FROM citas WHERE codigo = 'MED-0005');

INSERT INTO citas (codigo, usuario_id, tipo, fecha, hora, lugar, estado)
SELECT 'MED-0006', 1, 'Dermatología', '2026-02-28', '09:30', 'Clínica Sur', 'no asistida'
WHERE NOT EXISTS (SELECT 1 FROM citas WHERE codigo = 'MED-0006');

-- ================== MARZO 2026 ==================
INSERT INTO citas (codigo, usuario_id, tipo, fecha, hora, lugar, estado)
SELECT 'MED-0007', 1, 'Ginecología', '2026-03-06', '11:30', 'Clínica Norte', 'completada'
WHERE NOT EXISTS (SELECT 1 FROM citas WHERE codigo = 'MED-0007');

INSERT INTO citas (codigo, usuario_id, tipo, fecha, hora, lugar, estado)
SELECT 'MED-0008', 1, 'Psicología', '2026-03-13', '15:00', 'Clínica Sur', 'cancelada'
WHERE NOT EXISTS (SELECT 1 FROM citas WHERE codigo = 'MED-0008');

INSERT INTO citas (codigo, usuario_id, tipo, fecha, hora, lugar, estado)
SELECT 'MED-0009', 1, 'Optometría', '2026-03-20', '13:00', 'Clínica Este', 'completada'
WHERE NOT EXISTS (SELECT 1 FROM citas WHERE codigo = 'MED-0009');

INSERT INTO citas (codigo, usuario_id, tipo, fecha, hora, lugar, estado)
SELECT 'MED-0010', 1, 'Medicina General', '2026-03-27', '09:00', 'Clínica Oeste', 'completada'
WHERE NOT EXISTS (SELECT 1 FROM citas WHERE codigo = 'MED-0010');

-- ================== ABRIL 2026 ==================
INSERT INTO citas (codigo, usuario_id, tipo, fecha, hora, lugar, estado)
SELECT 'MED-0011', 1, 'Exámenes de laboratorio', '2026-04-03', '08:00', 'Clínica Norte', 'completada'
WHERE NOT EXISTS (SELECT 1 FROM citas WHERE codigo = 'MED-0011');

INSERT INTO citas (codigo, usuario_id, tipo, fecha, hora, lugar, estado)
SELECT 'MED-0012', 1, 'Cirugía', '2026-04-10', '07:00', 'Clínica Sur', 'completada'
WHERE NOT EXISTS (SELECT 1 FROM citas WHERE codigo = 'MED-0012');

INSERT INTO citas (codigo, usuario_id, tipo, fecha, hora, lugar, estado)
SELECT 'MED-0013', 1, 'Medicina General', '2026-04-17', '11:00', 'Clínica Este', 'no asistida'
WHERE NOT EXISTS (SELECT 1 FROM citas WHERE codigo = 'MED-0013');

INSERT INTO citas (codigo, usuario_id, tipo, fecha, hora, lugar, estado)
SELECT 'MED-0014', 1, 'Control', '2026-04-25', '14:30', 'Clínica Oeste', 'completada'
WHERE NOT EXISTS (SELECT 1 FROM citas WHERE codigo = 'MED-0014');

-- ================== MAYO 2026 ==================
INSERT INTO citas (codigo, usuario_id, tipo, fecha, hora, lugar, estado)
SELECT 'MED-0015', 1, 'Control', '2026-05-02', '10:00', 'Clínica Norte', 'completada'
WHERE NOT EXISTS (SELECT 1 FROM citas WHERE codigo = 'MED-0015');

INSERT INTO citas (codigo, usuario_id, tipo, fecha, hora, lugar, estado)
SELECT 'MED-0016', 1, 'Odontología', '2026-05-09', '09:30', 'Clínica Este', 'completada'
WHERE NOT EXISTS (SELECT 1 FROM citas WHERE codigo = 'MED-0016');

INSERT INTO citas (codigo, usuario_id, tipo, fecha, hora, lugar, estado)
SELECT 'MED-0017', 1, 'Psicología', '2026-05-16', '16:00', 'Clínica Oeste', 'completada'
WHERE NOT EXISTS (SELECT 1 FROM citas WHERE codigo = 'MED-0017');

INSERT INTO citas (codigo, usuario_id, tipo, fecha, hora, lugar, estado)
SELECT 'MED-0018', 1, 'Urgencias', '2026-05-20', '19:00', 'Clínica Sur', 'completada'
WHERE NOT EXISTS (SELECT 1 FROM citas WHERE codigo = 'MED-0018');

INSERT INTO citas (codigo, usuario_id, tipo, fecha, hora, lugar, estado)
SELECT 'MED-0019', 1, 'Dermatología', '2026-05-28', '11:30', 'Clínica Norte', 'cancelada'
WHERE NOT EXISTS (SELECT 1 FROM citas WHERE codigo = 'MED-0019');

-- ================== JUNIO 2026 ==================
-- Cita de madrugada de hoy → pendiente, ya pasó hace pocas horas → boton "Asistida" visible
INSERT INTO citas (codigo, usuario_id, tipo, fecha, hora, lugar, estado)
SELECT 'MED-0020', 1, 'Medicina General', '2026-06-01', '02:00', 'Clínica Norte', 'pendiente'
WHERE NOT EXISTS (SELECT 1 FROM citas WHERE codigo = 'MED-0020');

-- Cita mañana → activa la alerta de 24h
INSERT INTO citas (codigo, usuario_id, tipo, fecha, hora, lugar, estado)
SELECT 'MED-0021', 1, 'Cardiología', '2026-06-02', '10:00', 'Clínica Sur', 'pendiente'
WHERE NOT EXISTS (SELECT 1 FROM citas WHERE codigo = 'MED-0021');

-- Citas futuras pendientes
INSERT INTO citas (codigo, usuario_id, tipo, fecha, hora, lugar, estado)
SELECT 'MED-0022', 1, 'Pediatría', '2026-06-10', '09:00', 'Clínica Norte', 'pendiente'
WHERE NOT EXISTS (SELECT 1 FROM citas WHERE codigo = 'MED-0022');

INSERT INTO citas (codigo, usuario_id, tipo, fecha, hora, lugar, estado)
SELECT 'MED-0023', 1, 'Optometría', '2026-06-15', '11:00', 'Clínica Este', 'pendiente'
WHERE NOT EXISTS (SELECT 1 FROM citas WHERE codigo = 'MED-0023');

INSERT INTO citas (codigo, usuario_id, tipo, fecha, hora, lugar, estado)
SELECT 'MED-0024', 1, 'Control', '2026-06-20', '14:00', 'Clínica Oeste', 'pendiente'
WHERE NOT EXISTS (SELECT 1 FROM citas WHERE codigo = 'MED-0024');

INSERT INTO citas (codigo, usuario_id, tipo, fecha, hora, lugar, estado)
SELECT 'MED-0025', 1, 'Exámenes de laboratorio', '2026-06-27', '08:30', 'Clínica Sur', 'pendiente'
WHERE NOT EXISTS (SELECT 1 FROM citas WHERE codigo = 'MED-0025');
