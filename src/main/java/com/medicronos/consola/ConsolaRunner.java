package com.medicronos.consola;

import com.medicronos.modelo.Cita;
import com.medicronos.servicio.CitaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

/**
 * Runner de consola interactivo para gestionar citas.
 * Se activa automáticamente cuando Spring Boot termina de arrancar.
 * Permite agregar, editar y eliminar citas desde la terminal.
 */
@Component
public class ConsolaRunner implements ApplicationListener<ApplicationReadyEvent> {

    // ID de usuario simulado (en producción vendría de la sesión)
    private static final int USUARIO_ID = 1;

    @Autowired
    private CitaServicio citaServicio;

    /**
     * Se ejecuta cuando la aplicación está completamente lista.
     * Inicia el menú en un hilo separado para no bloquear el servidor web.
     */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Thread hiloConsola = new Thread(this::iniciarMenu);
        hiloConsola.setDaemon(true); // Se cierra junto con la app
        hiloConsola.setName("consola-menu");
        hiloConsola.start();
    }

    /**
     * Bucle principal del menú de consola.
     */
    private void iniciarMenu() {
        Scanner scanner = new Scanner(System.in);

        // Pequeña pausa para que los logs de Spring no sobreescriban el menú
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}

        System.out.println("\n");
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║      MEDICRONOS - Consola de Citas   ║");
        System.out.println("╚══════════════════════════════════════╝");

        boolean continuar = true;
        while (continuar) {
            imprimirMenu();
            System.out.print("▶ Elige una opción: ");

            String entrada = scanner.nextLine().trim();
            System.out.println();

            switch (entrada) {
                case "1" -> listarCitas();
                case "2" -> agregarCita(scanner);
                case "3" -> editarCita(scanner);
                case "4" -> eliminarCita(scanner);
                case "5" -> {
                    System.out.println("👋 Saliendo del menú de consola. El servidor sigue activo en http://localhost:8080");
                    continuar = false;
                }
                default -> System.out.println("⚠  Opción no válida. Ingresa un número del 1 al 5.\n");
            }
        }
    }

    private void imprimirMenu() {
        System.out.println("─────────────────────────────────────");
        System.out.println("  1. Ver mis citas");
        System.out.println("  2. Agregar cita");
        System.out.println("  3. Editar cita");
        System.out.println("  4. Eliminar cita");
        System.out.println("  5. Salir del menú");
        System.out.println("─────────────────────────────────────");
    }

    // ──────────────────────────────────────────────────────────────
    //  1. LISTAR
    // ──────────────────────────────────────────────────────────────
    private void listarCitas() {
        List<Cita> citas = citaServicio.obtenerCitasUsuario(USUARIO_ID);

        if (citas.isEmpty()) {
            System.out.println("📭 No tienes citas registradas.\n");
            return;
        }

        System.out.println("📋 Tus citas:");
        System.out.printf("%-4s %-10s %-12s %-6s %-20s %-12s%n",
                "ID", "TIPO", "FECHA", "HORA", "LUGAR", "ESTADO");
        System.out.println("─".repeat(68));

        for (Cita c : citas) {
            System.out.printf("%-4d %-10s %-12s %-6s %-20s %-12s%n",
                    c.getId(),
                    c.getTipo(),
                    c.getFecha(),
                    c.getHora(),
                    abreviar(c.getLugar(), 20),
                    c.getEstado());
        }
        System.out.println();
    }

    // ──────────────────────────────────────────────────────────────
    //  2. AGREGAR
    // ──────────────────────────────────────────────────────────────
    private void agregarCita(Scanner scanner) {
        System.out.println("📝 Nueva Cita — ingresa los datos:");

        String tipo = leerOpcionTipo(scanner);
        if (tipo == null) return;

        String fecha = leerCampo(scanner, "Fecha (ej: 2026-05-10)");
        String hora  = leerCampo(scanner, "Hora  (ej: 14:30)");
        String lugar = leerCampo(scanner, "Lugar (ej: Clínica Norte)");
        String desc  = leerCampo(scanner, "Descripción");

        Cita nueva = new Cita(USUARIO_ID, tipo, fecha, hora, lugar, desc);

        boolean exito = citaServicio.guardarNuevaCita(nueva);
        if (exito) {
            System.out.println("✅ Cita creada correctamente.\n");
            listarCitas();
        } else {
            System.out.println("❌ No se pudo crear la cita. Revisa los datos.\n");
        }
    }

    // ──────────────────────────────────────────────────────────────
    //  3. EDITAR
    // ──────────────────────────────────────────────────────────────
    private void editarCita(Scanner scanner) {
        listarCitas();

        System.out.print("✏️  ID de la cita a editar (o 0 para cancelar): ");
        int id = leerEntero(scanner);
        if (id <= 0) { System.out.println("Operación cancelada.\n"); return; }

        System.out.println("Ingresa los nuevos datos (deja en blanco para no cambiar):");

        String tipo  = leerOpcionTipoOpcional(scanner);
        String fecha = leerCampoOpcional(scanner, "Nueva Fecha (ej: 2026-05-10)");
        String hora  = leerCampoOpcional(scanner, "Nueva Hora  (ej: 14:30)");
        String lugar = leerCampoOpcional(scanner, "Nuevo Lugar");
        String desc  = leerCampoOpcional(scanner, "Nueva Descripción");

        // Buscamos la cita actual para mantener los campos que no cambiaron
        List<Cita> todas = citaServicio.obtenerCitasUsuario(USUARIO_ID);
        Cita actual = todas.stream().filter(c -> c.getId() == id).findFirst().orElse(null);

        if (actual == null) {
            System.out.println("❌ No se encontró una cita con ese ID.\n");
            return;
        }

        // Solo sobreescribimos si el usuario ingresó algo
        if (!tipo.isEmpty())  actual.setTipo(tipo);
        if (!fecha.isEmpty()) actual.setFecha(fecha);
        if (!hora.isEmpty())  actual.setHora(hora);
        if (!lugar.isEmpty()) actual.setLugar(lugar);
        if (!desc.isEmpty())  actual.setDescripcion(desc);

        boolean exito = citaServicio.modificarCita(actual);
        if (exito) {
            System.out.println("✅ Cita editada correctamente.\n");
            listarCitas();
        } else {
            System.out.println("❌ No se pudo editar la cita.\n");
        }
    }

    // ──────────────────────────────────────────────────────────────
    //  4. ELIMINAR
    // ──────────────────────────────────────────────────────────────
    private void eliminarCita(Scanner scanner) {
        listarCitas();

        System.out.print("🗑  ID de la cita a eliminar (o 0 para cancelar): ");
        int id = leerEntero(scanner);
        if (id <= 0) { System.out.println("Operación cancelada.\n"); return; }

        System.out.print("⚠️  ¿Confirmas eliminar la cita #" + id + "? (s/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        if (!confirm.equals("s")) {
            System.out.println("Operación cancelada.\n");
            return;
        }

        boolean exito = citaServicio.eliminarCitaDefinitivo(id);
        if (exito) {
            System.out.println("✅ Cita eliminada correctamente.\n");
            listarCitas();
        } else {
            System.out.println("❌ No se pudo eliminar la cita (¿ID incorrecto?).\n");
        }
    }

    // ──────────────────────────────────────────────────────────────
    //  UTILIDADES
    // ──────────────────────────────────────────────────────────────

    private String leerCampo(Scanner scanner, String etiqueta) {
        System.out.print("  " + etiqueta + ": ");
        return scanner.nextLine().trim();
    }

    private String leerCampoOpcional(Scanner scanner, String etiqueta) {
        System.out.print("  " + etiqueta + " [Enter para mantener]: ");
        return scanner.nextLine().trim();
    }

    private int leerEntero(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /** Muestra el menú de tipos y retorna la selección válida, o null si cancela. */
    private String leerOpcionTipo(Scanner scanner) {
        System.out.println("  Tipo de cita:");
        System.out.println("    1. salud    2. estudio    3. estetica    4. tramites");
        System.out.print("  Elige (1-4): ");
        String[] tipos = {"salud", "estudio", "estetica", "tramites"};
        int op = leerEntero(scanner);
        if (op < 1 || op > 4) {
            System.out.println("⚠️  Tipo inválido. Operación cancelada.\n");
            return null;
        }
        return tipos[op - 1];
    }

    /** Igual que leerOpcionTipo pero permite Enter vacío para no cambiar. */
    private String leerOpcionTipoOpcional(Scanner scanner) {
        System.out.println("  Tipo de cita (Enter para mantener):");
        System.out.println("    1. salud    2. estudio    3. estetica    4. tramites");
        System.out.print("  Elige (1-4) o Enter: ");
        String[] tipos = {"salud", "estudio", "estetica", "tramites"};
        String linea = scanner.nextLine().trim();
        if (linea.isEmpty()) return "";
        try {
            int op = Integer.parseInt(linea);
            if (op >= 1 && op <= 4) return tipos[op - 1];
        } catch (NumberFormatException ignored) {}
        return "";
    }

    private String abreviar(String texto, int max) {
        if (texto == null) return "";
        return texto.length() > max ? texto.substring(0, max - 1) + "…" : texto;
    }
}
