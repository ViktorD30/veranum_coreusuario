# Veranum Core Usuario

Microservicio encargado de la gestión de usuarios del sistema hotelero **Veranum Core**.

El proyecto está desarrollado con Spring Boot y utiliza una arquitectura por capas para separar las responsabilidades del sistema.

---

## Tecnologías utilizadas

- Java 17
- Spring Boot 4.1.1
- Maven
- Spring Web
- Spring Data JPA
- Hibernate
- Bean Validation
- MySQL
- Git y GitHub
- Postman

---

## Arquitectura del proyecto

El microservicio utiliza una arquitectura por capas:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
JPA / Hibernate
    ↓
MySQL
```

Estructura principal:

```text
src/main/java/com/veranum/coreusuario
│
├── config
├── controller
├── dto
├── exception
├── model
├── repository
├── service
└── VeranumCoreusuarioApplication.java
```

---

## Base de datos

El microservicio utiliza una base de datos MySQL independiente.

Nombre de la base de datos:

`veranum_usuario`

Antes de ejecutar el microservicio se debe crear la base de datos:

```sql
CREATE DATABASE veranum_usuario
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;
```

Las tablas son generadas automáticamente por JPA/Hibernate mediante las entidades del proyecto.

Las tablas principales son:

- `roles`
- `usuarios`

La relación entre las entidades es:

`Rol 1 ───────── N Usuario`

La tabla `usuarios` contiene la clave foránea:

`usuarios.rol_id → roles.id`

---

## Roles iniciales

Al iniciar el proyecto se ejecuta el archivo `data.sql`, que carga los siguientes roles:

- `1 - CLIENTE`
- `2 - ADMIN`
- `3 - RECEPCIONISTA`

---

## Configuración de MySQL

La configuración de conexión se encuentra en:

`src/main/resources/application.properties`

El proyecto utiliza las siguientes propiedades:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/veranum_usuario?useSSL=false&serverTimezone=America/Santiago
spring.datasource.username=${DB_USER:root}
spring.datasource.password=${DB_PASSWORD:}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

Si MySQL utiliza el usuario `root` sin contraseña, no es necesario definir variables adicionales.

Si MySQL utiliza otro usuario o contraseña, se recomienda definir `DB_USER` y `DB_PASSWORD` como variables de entorno y no almacenar credenciales directamente en GitHub.

---

## Configuración JPA / Hibernate

El proyecto utiliza JPA e Hibernate para administrar la persistencia.

La configuración principal es:

```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.defer-datasource-initialization=true
spring.sql.init.mode=always
```

`ddl-auto=update` permite que Hibernate cree o actualice las tablas a partir de las entidades sin eliminar los datos existentes.

---

## Puerto del microservicio

El microservicio se ejecuta en el puerto:

`8081`

URL base:

`http://localhost:8081`

---

## Requisitos

Antes de ejecutar el proyecto se debe contar con:

- JDK 17
- MySQL 8 o compatible
- Git
- Maven Wrapper incluido en el proyecto
- Conexión a Internet para descargar las dependencias la primera vez

Para comprobar la versión de Java:

```powershell
java -version
```

El proyecto utiliza Java 17.

También se debe tener configurada correctamente la variable `JAVA_HOME` apuntando a una instalación de JDK 17.

---

## Clonar el repositorio

Clonar el proyecto desde GitHub:

```powershell
git clone https://github.com/ViktorD30/veranum_coreusuario.git
```

Ingresar a la carpeta del proyecto:

```powershell
cd veranum_coreusuario
```

---

## Compilar el proyecto

En Windows se puede utilizar el Maven Wrapper incluido en el repositorio:

```powershell
.\mvnw.cmd clean compile
```

Si la compilación es correcta, Maven mostrará:

```text
BUILD SUCCESS
```

---

## Generar el artefacto JAR

Ejecutar:

```powershell
.\mvnw.cmd package
```

El archivo generado queda ubicado en:

```text
target/veranum_coreusuario-0.0.1-SNAPSHOT.jar
```

---

## Instalar el proyecto con Maven

Ejecutar:

```powershell
.\mvnw.cmd install
```

Este comando compila, ejecuta las pruebas, genera el artefacto e instala el proyecto en el repositorio Maven local.

---

## Ejecutar con Spring Boot

Para iniciar el microservicio directamente con Maven:

```powershell
.\mvnw.cmd spring-boot:run
```

Cuando la aplicación inicia correctamente se encuentra disponible en:

`http://localhost:8081`

---

## Ejecutar el archivo JAR

Después de ejecutar `package`, también se puede iniciar el microservicio mediante:

```powershell
java -jar .\target\veranum_coreusuario-0.0.1-SNAPSHOT.jar
```

Al iniciar correctamente debe mostrarse un mensaje similar a:

```text
Tomcat started on port 8081
Started VeranumCoreusuarioApplication
```

---

## Endpoints REST

La URL base de los endpoints de usuario es:

`http://localhost:8081/api/v1/usuarios`

Los endpoints pueden ser probados mediante Postman.

### Crear usuario

**Método:** `POST`

```text
/api/v1/usuarios
```

Ejemplo de solicitud:

```json
{
  "rut": "12345678-9",
  "nombre": "Usuario Prueba",
  "email": "usuario.prueba@veranum.cl",
  "password": "123456",
  "rolId": 1
}
```

Respuesta esperada:

```text
201 Created
```

Ejemplo de respuesta:

```json
{
  "codigo": 201,
  "mensaje": "Usuario creado con éxito",
  "error": false,
  "data": {
    "id": 1,
    "rut": "12345678-9",
    "nombre": "Usuario Prueba",
    "email": "usuario.prueba@veranum.cl",
    "rol": "CLIENTE",
    "bloqueado": false,
    "intentosFallidos": 0
  }
}
```

---

### Listar usuarios

**Método:** `GET`

```text
/api/v1/usuarios
```

Respuesta esperada:

```text
200 OK
```

---

### Buscar usuario por ID

**Método:** `GET`

```text
/api/v1/usuarios/{id}
```

Ejemplo:

```text
/api/v1/usuarios/1
```

Si el usuario existe:

```text
200 OK
```

Si el usuario no existe:

```text
404 Not Found
```

Ejemplo de error:

```json
{
  "codigo": 404,
  "mensaje": "Usuario no encontrado con ID: 999",
  "error": true,
  "data": null
}
```

---

### Actualizar usuario

**Método:** `PUT`

```text
/api/v1/usuarios/{id}
```

Ejemplo:

```text
/api/v1/usuarios/1
```

Ejemplo de solicitud:

```json
{
  "rut": "12345678-9",
  "nombre": "Usuario Prueba Actualizado",
  "email": "usuario.actualizado@veranum.cl",
  "password": "123456",
  "rolId": 1
}
```

Respuesta esperada:

```text
200 OK
```

---

### Eliminar usuario

**Método:** `DELETE`

```text
/api/v1/usuarios/{id}
```

Ejemplo:

```text
/api/v1/usuarios/1
```

Respuesta esperada:

```text
200 OK
```

Ejemplo de respuesta:

```json
{
  "codigo": 200,
  "mensaje": "Usuario eliminado correctamente",
  "error": false,
  "data": null
}
```

---

## Endpoints adicionales

### Bloquear usuario

**Método:** `PUT`

```text
/api/v1/usuarios/{id}/bloquear
```

Ejemplo:

```text
/api/v1/usuarios/1/bloquear
```

Cuando la operación es correcta, el usuario queda con:

```json
{
  "bloqueado": true
}
```

---

### Desbloquear usuario

**Método:** `PUT`

```text
/api/v1/usuarios/{id}/desbloquear
```

Ejemplo:

```text
/api/v1/usuarios/1/desbloquear
```

Cuando la operación es correcta, el usuario queda con:

```json
{
  "bloqueado": false
}
```

---

### Verificar existencia por RUT

**Método:** `GET`

```text
/api/v1/usuarios/existe/{rut}
```

Ejemplo:

```text
/api/v1/usuarios/existe/12345678-9
```

Si el usuario existe:

```json
{
  "codigo": 200,
  "mensaje": "Validación de existencia realizada",
  "error": false,
  "data": true
}
```

---

### Validar usuario para una reserva

**Método:** `GET`

```text
/api/v1/usuarios/validar-reserva/{rut}
```

Ejemplo:

```text
/api/v1/usuarios/validar-reserva/12345678-9
```

Para realizar una reserva el usuario debe:

- existir;
- no estar bloqueado;
- tener rol `CLIENTE`.

Si cumple las condiciones:

```json
{
  "codigo": 200,
  "mensaje": "Usuario habilitado para realizar reservas",
  "error": false,
  "data": true
}
```

Si el usuario está bloqueado:

```json
{
  "codigo": 400,
  "mensaje": "El usuario está bloqueado y no puede realizar reservas",
  "error": true,
  "data": null
}
```

---

### Listar roles

**Método:** `GET`

```text
/api/v1/usuarios/roles
```

Ejemplo de respuesta:

```json
{
  "codigo": 200,
  "mensaje": "Lista de roles obtenida correctamente",
  "error": false,
  "data": [
    {
      "id": 1,
      "nombre": "CLIENTE"
    },
    {
      "id": 2,
      "nombre": "ADMIN"
    },
    {
      "id": 3,
      "nombre": "RECEPCIONISTA"
    }
  ]
}
```

---

## Validaciones

Los datos recibidos al crear o actualizar usuarios son validados antes de ser procesados.

Algunas de las validaciones implementadas son:

- RUT obligatorio.
- Nombre obligatorio.
- Nombre entre 3 y 100 caracteres.
- Email obligatorio.
- Formato correcto de email.
- Contraseña de mínimo 6 caracteres.
- Rol obligatorio.
- RUT no duplicado.
- Email no duplicado.
- Rol existente.

Ejemplo de respuesta ante datos inválidos:

```json
{
  "codigo": 400,
  "mensaje": "Errores de validación en los datos enviados",
  "error": true,
  "data": {
    "rolId": "Debe asignar el ID de un rol",
    "password": "La contraseña debe tener mínimo 6 caracteres",
    "nombre": "El nombre debe tener entre 3 y 100 caracteres",
    "email": "Formato de correo inválido"
  }
}
```

---

## Manejo de errores

El proyecto utiliza un manejador global de excepciones mediante `GlobalExceptionHandler`.

Los principales códigos HTTP utilizados son:

| Código | Descripción |
|---|---|
| `200 OK` | Operación realizada correctamente |
| `201 Created` | Recurso creado correctamente |
| `400 Bad Request` | Datos inválidos o regla de negocio no cumplida |
| `404 Not Found` | Recurso solicitado no encontrado |
| `409 Conflict` | Conflicto con datos únicos o integridad |
| `500 Internal Server Error` | Error interno no controlado |

Ejemplos de errores controlados:

- usuario inexistente;
- rol inexistente;
- RUT duplicado;
- email duplicado;
- datos inválidos;
- usuario bloqueado para realizar una reserva.

---

## Relación entre Usuario y Rol

La entidad `Usuario` utiliza una relación `ManyToOne` con `Rol`.

```text
Muchos usuarios
      ↓
     un rol
```

La entidad `Rol` utiliza la relación inversa `OneToMany`.

```text
Rol
 │
 │ 1
 │
 │ N
 ▼
Usuario
```

En MySQL la relación se implementa mediante:

```text
usuarios.rol_id → roles.id
```

---

## Persistencia

El proyecto utiliza:

- Spring Data JPA
- Hibernate
- MySQL

Los repositorios principales son:

- `UsuarioRepository`
- `RolRepository`

Spring Data JPA proporciona operaciones como:

```text
save()
findAll()
findById()
delete()
```

También se utilizan consultas derivadas como:

```text
findByRut()
findByEmail()
existsByRut()
existsByEmail()
findByNombreIgnoreCase()
```

---

## Pruebas realizadas con Postman

Se comprobaron casos exitosos y casos de error.

Entre las pruebas realizadas se encuentran:

- Crear usuario.
- Listar usuarios.
- Buscar usuario por ID.
- Actualizar usuario.
- Eliminar usuario.
- Buscar usuario inexistente.
- Crear usuario con rol inexistente.
- Validar datos incorrectos.
- Validar RUT duplicado.
- Validar email duplicado.
- Bloquear usuario.
- Desbloquear usuario.
- Validar usuario bloqueado para reserva.
- Validar usuario habilitado para reserva.
- Consultar existencia por RUT.
- Listar roles.

---

## Comandos Maven utilizados

Durante el desarrollo se comprobaron los siguientes comandos:

```powershell
.\mvnw.cmd clean
```

```powershell
.\mvnw.cmd compile
```

```powershell
.\mvnw.cmd clean compile
```

```powershell
.\mvnw.cmd package
```

```powershell
.\mvnw.cmd install
```

También se comprobó la ejecución directa del artefacto:

```powershell
java -jar .\target\veranum_coreusuario-0.0.1-SNAPSHOT.jar
```

Todos estos procesos finalizaron correctamente con:

```text
BUILD SUCCESS
```

---

## Estrategia Git

El proyecto utiliza las ramas:

```text
main
develop
feature/*
```

El flujo utilizado es:

```text
feature
   ↓
Pull Request
   ↓
develop
   ↓
main
```

Las nuevas funcionalidades se desarrollan primero en ramas `feature`.

Una vez comprobadas, se integran a `develop` mediante Pull Request.

La rama `main` se mantiene para versiones estables del proyecto.

---

## Estructura de respuestas

El microservicio utiliza una respuesta estándar mediante `ApiResponse`.

Ejemplo exitoso:

```json
{
  "codigo": 200,
  "mensaje": "Operación realizada correctamente",
  "error": false,
  "data": {}
}
```

Ejemplo de error:

```json
{
  "codigo": 404,
  "mensaje": "Recurso no encontrado",
  "error": true,
  "data": null
}
```

---

## Consideraciones importantes

- La base de datos debe existir antes de iniciar el microservicio.
- MySQL debe estar ejecutándose.
- El puerto `8081` debe estar disponible.
- Se debe utilizar Java 17.
- No se deben almacenar credenciales reales de MySQL directamente en GitHub.
- La carpeta `target` no debe versionarse.
- La carpeta `.vscode` no debe versionarse.

---

## Proyecto académico

Proyecto desarrollado para la **Evaluación Parcial 2**.

Microservicio correspondiente a la gestión de usuarios del sistema hotelero **Veranum Core**.