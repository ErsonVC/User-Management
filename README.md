# User Management API with JWT Authentication

API REST desarrollada con **Spring Boot** que implementa un sistema completo de gestión de usuarios con seguridad avanzada.

## 🔹 Descripción
Este proyecto proporciona una base sólida para la administración de usuarios, incluyendo:
* **CRUD de usuarios**: Operaciones completas de creación, lectura, actualización y eliminación.
* **Soft Delete**: Eliminación lógica de registros para mantener la integridad de los datos.
* **Seguridad con BCrypt**: Encriptación de contraseñas de alta seguridad.
* **Autenticación con JWT**: Sistema de acceso mediante tokens para peticiones seguras.
* **Protección de Endpoints**: Restricción de acceso a rutas basado en el estado de autenticación.

## 🔹 Stack Tecnológico
* **Lenguaje**: Java 21
* **Framework**: Spring Boot 3.3.4
* **Seguridad**: Spring Security & JWT (JJWT)
* **Persistencia**: Spring Data JPA
* **Base de Datos**: MySQL

## 🔹 Arquitectura
El proyecto está organizado siguiendo el patrón de capas para un código limpio y mantenible:
* `controller`: Definición de los puntos de entrada (endpoints).
* `service`: Lógica de negocio y procesamiento.
* `repository`: Interfaz de comunicación con la base de datos.
* `dto`: Objetos de transferencia de datos.
* `entity`: Modelado de tablas de base de datos.
* `exception`: Manejo global de errores y respuestas.
* `config`: Configuraciones de seguridad y beans.

## 🔹 Flujo de Autenticación
1. **Login**: El usuario envía sus credenciales.
2. **Token**: El servidor genera y devuelve un JWT.
3. **Header Authorization**: El cliente envía el token en cada petición.
4. **Filtro JWT**: El sistema valida el token antes de procesar la solicitud.
5. **Endpoint Protegido**: Se concede el acceso si el token es válido.

## 🔹 Endpoints Principales

| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `POST` | `/api/auth/login` | Inicio de sesión y obtención de Token |
| `POST` | `/api/users` | Registro de un nuevo usuario |
| `GET` | `/api/users` | Listado de todos los usuarios |

## 🔹 Cómo ejecutar
1. **Configurar base de datos**: Crea una base de datos MySQL en tu entorno local.
2. **Ajustar application.yml**: Configura tus credenciales de base de datos y la clave secreta de JWT.
3. **Ejecutar aplicación**: Lanza el proyecto desde tu IDE o mediante `mvn spring-boot:run`.
4. **Probar con Postman**: Importa los endpoints y asegúrate de enviar el Token en el Header para rutas protegidas.