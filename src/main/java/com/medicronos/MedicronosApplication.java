package com.medicronos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal que arranca el servidor web y la aplicación Spring Boot.
 */
@SpringBootApplication
public class MedicronosApplication {

    public static void main(String[] args) {
        // Al ejecutar esta función, Spring levanta el servidor y lee todos tus controladores y base de datos
        SpringApplication.run(MedicronosApplication.class, args);
        System.out.println("¡Servidor de Medicronos Iniciado Exitosamente!");
    }

}
