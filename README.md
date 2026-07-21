# Medicronos — Sistema de Gestion de Citas Medicas / Medical Appointment Management System

---

## Parte 1: Version en Español

### Informacion del Proyecto
* **Integrantes del proyecto:** Samuel Rodriguez, Catalina Gomez, Jhonatan Robayo, Santiago Correa
* **Materia:** Principios de Desarrollo de Software
* **Profesor:** Santiago Peña Arismendi
* **Institucion:** Pontificia Universidad Javeriana

---

### Tabla de Contenidos
1. [Descripcion General](#descripcion-general)
2. [Caracteristicas Principales](#caracteristicas-principales)
3. [Pila Tecnologica](#pila-tecnologica)
4. [Arquitectura del Sistema](#arquitectura-del-sistema)
5. [Esquema de Base de Datos](#esquema-de-base-de-datos)
6. [Referencia de la API REST](#referencia-de-la-api-rest)
7. [Configuracion de Seguridad y Credenciales](#configuracion-de-seguridad-y-credenciales)
8. [Ejecucion Local](#ejecucion-local)
9. [Despliegue con Docker](#despliegue-con-docker)
10. [Credenciales de Prueba por Defecto](#credenciales-de-prueba-por-defecto)
11. [Estructura del Proyecto](#estructura-del-proyecto)

---

### Descripcion General
Medicronos es una aplicacion web full-stack diseñada para la gestion personal de citas medicas. Permite a los pacientes registrarse, iniciar sesion y administrar de manera eficiente su agenda de salud. Los usuarios pueden agendar nuevas citas medicas, editarlas, cancelarlas, registrar su asistencia y analizar sus estadisticas de cumplimiento de citas mediante graficos intuitivos.

---

### Caracteristicas Principales
* **Autenticacion de Usuarios:** Registro e inicio de sesion seguro. Las credenciales se validan en el servidor y la sesion se mantiene localmente.
* **Agendamiento de Citas:** Permite reservar citas medicas seleccionando especialidad, fecha, hora disponible y consultorio. El sistema previene duplicados en el mismo rango de fecha y hora.
* **Filtros Avanzados:** Tabla de citas ordenable y filtrable por estado (Pendiente, Completada, Cancelada, No asistida) y especialidad medica.
* **Modificacion y Cancelacion:** Opcion de actualizar los datos de la cita o cancelarla. Las citas canceladas permanecen visibles unicamente durante las primeras 48 horas tras su cancelacion antes de ocultarse de la vista principal.
* **Registro de Asistencia:** Los usuarios pueden marcar citas pasadas como asistidas manualmente.
* **Deteccion de Citas Vencidas:** Al cargar el dashboard, el sistema detecta citas pendientes que ocurrieron hace mas de 24 horas y las actualiza automaticamente al estado "No asistida".
* **Dashboard de Estadisticas:** Incluye graficas de dona para analizar el porcentaje de cumplimiento (tasa de asistencia), distribucion de estados y especialidades medicas solicitadas.
* **Recordatorio de Citas proximas:** Banner de alerta dinamico si el usuario tiene citas programadas dentro de las proximas 24 horas.
* **Gestion de Perfil:** Visualizacion de datos basicos de la cuenta y opcion para cambiar la contraseña.
* **Diseno Adaptable (Responsive):** Interfaz optimizada para dispositivos moviles y pantallas de escritorio mediante barra lateral colapsable.

---

### Pila Tecnologica

#### Backend
* **Lenguaje:** Java 17
* **Framework:** Spring Boot 3.2.4
* **Capa Web:** Spring MVC (Controladores REST)
* **Persistencia de Datos:** Spring Data JPA (Hibernate)
* **Base de Datos (Local):** H2 (Embebida en archivo: `./medicronos.mv.db`)
* **Base de Datos (Docker):** MySQL 8

#### Frontend
* **Estructura y Logica:** HTML5 y JavaScript (Vanilla JS con llamadas asincronas fetch)
* **Estilos:** Vanilla CSS (Diseño moderno dark mode con efecto de vidrio esmerilado / glassmorphism)
* **Graficas:** Chart.js (cargado via CDN)
* **Tipografia:** Outfit (Google Fonts)

---

### Arquitectura del Sistema
El proyecto implementa una arquitectura por capas limpia:
* **Controladores (Controllers):** Gestionan las peticiones HTTP externas, validan entradas y retornan respuestas JSON.
* **Servicios (Services):** Contienen la logica de negocio y validaciones del dominio.
* **DAO / Repositorios:** Capa de acceso a datos utilizando Spring Data JPA para realizar consultas en la base de datos de manera segura.
* **Modelos (Entities):** Representacion de las tablas de la base de datos (Usuario, Cita, Categoria).

El frontend se comporta como una SPA (Single Page Application) servida de forma estatica por Spring Boot.

---

### Esquema de Base de Datos

#### Tabla: `usuarios`
* `id` (INT, Llave Primaria, Auto-incrementable)
* `nombre` (VARCHAR(100), Requerido)
* `email` (VARCHAR(100), Unico, Requerido)
* `contrasena` (VARCHAR(255), Requerido)
* `fecha_registro` (TIMESTAMP, Por defecto actual)

#### Tabla: `citas`
* `id` (INT, Llave Primaria, Auto-incrementable)
* `codigo` (VARCHAR(10), Unico, Requerido)
* `usuario_id` (INT, Llave Foranea → usuarios.id, Eliminacion en cascada)
* `tipo` (VARCHAR(50), Restringido a especialidades validas)
* `fecha` (DATE, Requerido)
* `hora` (TIME, Requerido)
* `lugar` (VARCHAR(150))
* `descripcion` (TEXT)
* `estado` (VARCHAR(20), Estados: pendiente, completada, cancelada, no asistida)
* `fecha_creacion` (TIMESTAMP, Por defecto actual)

**Restriccion unica:** `uq_fecha_hora` previene el registro de multiples citas en la misma fecha y hora a nivel global.

---

### Referencia de la API REST

#### Usuarios (`/api/usuarios`)
* `POST /login` -> Autentica credenciales y retorna el objeto del usuario.
* `POST /registrar` -> Registra un nuevo usuario.
* `GET /{id}` -> Obtiene los datos del perfil de un usuario.
* `PUT /cambiar-contrasena` -> Cambia de forma segura la contraseña del usuario.

#### Citas (`/api/citas`)
* `GET /usuario/{userId}` -> Retorna el listado de citas de un usuario especifico.
* `GET /horarios-disponibles?fecha=YYYY-MM-DD` -> Devuelve los bloques de hora disponibles (08:00 a 20:00 cada 15 minutos) excluyendo los ocupados.
* `POST /` -> Reserva una nueva cita.
* `PUT /` -> Actualiza los datos de una cita existente.
* `PATCH /cancelar/{id}` -> Cambia el estado de una cita a "cancelada".
* `PATCH /asistir/{id}` -> Marca una cita como "completada".
* `PATCH /no-asistida/{id}` -> Marca una cita como "no asistida".
* `DELETE /borrar/{id}` -> Elimina definitivamente una cita de la base de datos.

#### Estadisticas (`/api/estadisticas`)
* `GET /usuario/{userId}?mes=X` -> Retorna resumenes numericos y distribucion de citas.

---

### Configuracion de Seguridad y Credenciales
Para evitar subir informacion confidencial a repositorios publicos como GitHub, las credenciales de la base de datos SQL se han extraido del codigo fuente y se manejan de la siguiente manera:
1. **Variables de Entorno:** El archivo `application.properties` y el archivo `docker-compose.yml` leen las credenciales usando variables de entorno (`SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`, etc.).
2. **H2 por Defecto:** Si no se definen variables de entorno, la aplicacion arranca localmente de manera segura con la base de datos H2 embebida (sin secretos reales).
3. **Archivo de Configuracion Local (.env):** Para el entorno Docker, se incluye un archivo local llamado `.env` donde se definen las contraseñas de MySQL. **Este archivo esta en `.gitignore` para asegurar que nunca se suba al repositorio publico.**
4. **Plantilla de configuracion (.env.example):** Se incluye un archivo `.env.example` en el repositorio para que otros desarrolladores sepan que variables deben definir localmente.

---

### Ejecucion Local

#### Prerrequisitos
* Java 17 o superior instalado.
* Maven no requiere instalacion previa, ya que el proyecto incluye su propia distribucion embebida.

#### Pasos para la ejecucion
1. Clonar el repositorio.
2. Copiar el archivo de configuracion de ejemplo para crear tu configuracion local (opcional para desarrollo local con H2):
   ```bash
   cp .env.example .env
   ```
3. Iniciar el servidor Spring Boot usando el comando de Maven embebido:
   * En macOS/Linux:
     ```bash
     ./apache-maven-3.9.6/bin/mvn spring-boot:run
     ```
   * En Windows:
     ```cmd
     apache-maven-3.9.6\bin\mvn.cmd spring-boot:run
     ```
4. Abrir en el navegador:
   ```
   http://localhost:8085
   ```

---

### Despliegue con Docker
Docker Compose levantara de manera automatica dos contenedores: la aplicacion Spring Boot y la base de datos MySQL 8.
1. Asegurarse de tener Docker ejecutandose.
2. Crear y configurar tu archivo local `.env` a partir de `.env.example`.
3. Levantar los contenedores:
   ```bash
   docker compose up --build
   ```
4. La aplicacion estara disponible en `http://localhost:8085`. Las credenciales de base de datos MySQL se leeran de forma segura desde tu archivo `.env` local sin exponerse en el codigo.

---

### Credenciales de Prueba por Defecto
Al iniciar por primera vez, el sistema precarga datos de prueba:
* **Correo de pruebas:** `correac.santiago@javeriana.edu.co`
* **Contrasena:** `1234`

---

### Estructura del Proyecto
* `src/main/java/com/medicronos/`
  * `MedicronosApplication.java` -> Punto de entrada del backend.
  * `controlador/` -> Controladores REST del API.
  * `servicio/` -> Capa de servicios y logica de negocio.
  * `modelo/` -> Entidades JPA de mapeo relacional.
  * `database/` -> Capa DAO para consultas sql y logica transaccional.
* `src/main/resources/`
  * `application.properties` -> Propiedades de Spring Boot.
  * `static/index.html` -> Interfaz de usuario Single Page Application (HTML, CSS y JS).
  * `sql/` -> Scripts SQL de estructura y carga de datos iniciales.
* `.env` -> Archivo local de credenciales (ignorado por Git).
* `.env.example` -> Plantilla para configuracion de credenciales locales.
* `docker-compose.yml` -> Definicion de servicios Docker.
* `Dockerfile` -> Imagen Docker de la aplicacion Spring Boot.

---
---

## Part 2: English Version

### Project Information
* **Project Members:** Samuel Rodriguez, Catalina Gomez, Jhonatan Robayo, Santiago Correa
* **Course:** Principios de Desarrollo de Software
* **Professor:** Santiago Peña Arismendi
* **Institution:** Pontificia Universidad Javeriana

---

### Table of Contents
1. [Project Overview](#project-overview)
2. [Key Features](#key-features)
3. [Technology Stack](#technology-stack)
4. [Architecture Overview](#architecture-overview)
5. [Database Schema](#database-schema)
6. [REST API Reference](#rest-api-reference)
7. [Security Configuration and Credentials](#security-configuration-and-credentials)
8. [Running Locally](#running-locally)
9. [Docker Deployment](#docker-deployment)
10. [Default Test Credentials](#default-test-credentials)
11. [Project Structure](#project-structure)

---

### Project Overview
Medicronos is a full-stack web application designed for personal medical appointment management. It enables patients to sign up, log in, and efficiently manage their healthcare agenda. Users can schedule new medical appointments, edit them, cancel them, record attendance, and analyze their appointment compliance history via intuitive charts.

---

### Key Features
* **User Authentication:** Secure sign-up and log-in. Credentials are validated server-side, and the session persists locally in the browser.
* **Appointment Scheduling:** Book appointments by selecting a specialty, date, available time slot, and clinic location. The system prevents double bookings for the same date and time.
* **Advanced Filters:** An appointment table that can be filtered and sorted by status (Pending, Completed, Cancelled, No-show) and specialty type.
* **Modification & Cancellation:** Easily update appointment details or cancel them. Cancelled appointments remain visible for 48 hours after cancellation before being hidden.
* **Attendance Tracking:** Users can manually mark past appointments as attended.
* **Auto-Expiration:** On dashboard load, the system checks for pending appointments older than 24 hours and updates them to "No-show".
* **Stats Dashboard:** Doughnut charts displaying compliance rate (attendance percentage), status distribution, and requested medical specialties.
* **Upcoming Appointment Alert:** A dynamic reminder banner displays if the user has an appointment scheduled within the next 24 hours.
* **Profile Management:** View basic profile information and update account passwords.
* **Responsive Design:** Mobile-friendly layouts featuring a collapsible sidebar navigation for screen widths under 768px.

---

### Technology Stack

#### Backend
* **Language:** Java 17
* **Framework:** Spring Boot 3.2.4
* **Web Layer:** Spring MVC (REST Controllers)
* **Data Access:** Spring Data JPA (Hibernate)
* **Database (Local):** H2 (Embedded file: `./medicronos.mv.db`)
* **Database (Docker):** MySQL 8

#### Frontend
* **Structure & Logic:** HTML5 and Vanilla JavaScript (Fetch API for asynchronous calls)
* **Styling:** Vanilla CSS (Modern dark mode with glassmorphism visual effects)
* **Charts:** Chart.js via CDN
* **Typography:** Outfit (Google Fonts)

---

### Architecture Overview
This project adheres to a clean layered architecture:
* **Controllers:** Handle HTTP requests, parse query/body inputs, and return JSON.
* **Services:** Contain business rules and domain validations.
* **DAO / Repositories:** Interact with the database using Spring Data JPA.
* **Models:** Mapping representing database tables (Usuario, Cita, Categoria).

The frontend is served as a Single Page Application (SPA) statically hosted by Spring Boot.

---

### Database Schema

#### Table: `usuarios`
* `id` (INT, PK, Auto-increment)
* `nombre` (VARCHAR(100), Required)
* `email` (VARCHAR(100), Unique, Required)
* `contrasena` (VARCHAR(255), Required)
* `fecha_registro` (TIMESTAMP, Default CURRENT_TIMESTAMP)

#### Table: `citas`
* `id` (INT, PK, Auto-increment)
* `codigo` (VARCHAR(10), Unique, Required)
* `usuario_id` (INT, FK → usuarios.id, Cascade Delete)
* `tipo` (VARCHAR(50), Constrained to valid medical specialties)
* `fecha` (DATE, Required)
* `hora` (TIME, Required)
* `lugar` (VARCHAR(150))
* `descripcion` (TEXT)
* `estado` (VARCHAR(20), States: pending, completed, cancelled, no-show)
* `fecha_creacion` (TIMESTAMP, Default CURRENT_TIMESTAMP)

**Constraint:** The unique constraint `uq_fecha_hora` prevents multiple bookings for the same date and time slot.

---

### REST API Reference

#### Users (`/api/usuarios`)
* `POST /login` -> Authenticate credentials and retrieve user object.
* `POST /registrar` -> Create a new user account.
* `GET /{id}` -> Retrieve user profile data by ID.
* `PUT /cambiar-contrasena` -> Safely change user password.

#### Appointments (`/api/citas`)
* `GET /usuario/{userId}` -> Return appointments list of a specific user.
* `GET /horarios-disponibles?fecha=YYYY-MM-DD` -> Return list of 15-minute time slots (08:00 to 20:00) excluding booked ones.
* `POST /` -> Book a new appointment.
* `PUT /` -> Update an existing appointment.
* `PATCH /cancelar/{id}` -> Update status of an appointment to "cancelled".
* `PATCH /asistir/{id}` -> Update status of an appointment to "completed".
* `PATCH /no-asistida/{id}` -> Update status of an appointment to "no asistida".
* `DELETE /borrar/{id}` -> Delete an appointment permanently.

#### Statistics (`/api/estadisticas`)
* `GET /usuario/{userId}?mes=X` -> Return numerical statistical summary of user appointments.

---

### Security Configuration and Credentials
To avoid committing sensitive credentials to public repositories (such as GitHub), the SQL database credentials have been decoupled from the source code:
1. **Environment Variables:** Both `application.properties` and `docker-compose.yml` load database configurations from environment variables (e.g., `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`).
2. **H2 Safe Default:** If no environment variables are set, the app defaults to using the local H2 embedded database, which uses safe mock credentials (`sa`/`password`).
3. **Local Environment File (.env):** When launching via Docker, a local `.env` file must be used to define actual MySQL passwords. **This file is explicitly added to `.gitignore` to prevent leaks on GitHub.**
4. **Environment Template (.env.example):** A template file `.env.example` is supplied so developers can easily configure their local environment.

---

### Running Locally

#### Prerequisites
* Java 17 or higher installed.
* Maven is embedded inside the directory, no installation needed.

#### Execution Steps
1. Clone the repository.
2. Copy the sample environment file to create your local variables (optional for default H2 run):
   ```bash
   cp .env.example .env
   ```
3. Run the application using the embedded Maven wrapper:
   * On macOS/Linux:
     ```bash
     ./apache-maven-3.9.6/bin/mvn spring-boot:run
     ```
   * On Windows:
     ```cmd
     apache-maven-3.9.6\bin\mvn.cmd spring-boot:run
     ```
4. Access the web interface at:
   ```
   http://localhost:8085
   ```

---

### Docker Deployment
Docker Compose will launch both the Spring Boot app and a MySQL 8 database container.
1. Ensure Docker is running.
2. Verify you have created a local `.env` file based on `.env.example`.
3. Launch the services:
   ```bash
   docker compose up --build
   ```
4. Access the application at `http://localhost:8085`. Database credentials will be loaded securely from your `.env` file.

---

### Default Test Credentials
The database seeds a mock user on first run:
* **Test Email:** `correac.santiago@javeriana.edu.co`
* **Test Password:** `1234`

---

### Project Structure
* `src/main/java/com/medicronos/`
  * `MedicronosApplication.java` -> Backend boot class.
  * `controlador/` -> REST Controller endpoints.
  * `servicio/` -> Business logic layer.
  * `modelo/` -> JPA relational mappings.
  * `database/` -> DAO layer for custom sql queries.
* `src/main/resources/`
  * `application.properties` -> Spring configuration settings.
  * `static/index.html` -> Single Page Application frontend (HTML, CSS, JS).
  * `sql/` -> Database structure and seed scripts.
* `.env` -> Local environment file containing credentials (Git ignored).
* `.env.example` -> Local configuration credentials template.
* `docker-compose.yml` -> Docker services orchestrations.
* `Dockerfile` -> Spring Boot Docker build instructions.
