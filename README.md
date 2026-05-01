# 📚 Biblioteca API

API REST desarrollada con Spring Boot para la gestión de autores y libros.  
Incluye operaciones CRUD completas, validaciones en capa de servicio y manejo global de excepciones con respuestas estructuradas.

Este proyecto fue diseñado siguiendo buenas prácticas de arquitectura en capas (Controller, Service, Repository) y orientado a ser consumido por múltiples clientes frontend.

---

## 🚀 Tecnologías utilizadas

- Java 17  
- Spring Boot  
- Spring Web  
- Spring Data JPA  
- Hibernate  
- MySQL  
- Swagger (OpenAPI)  
- Logging (SLF4J + Logback)

---

## ⚙️ Cómo ejecutar el proyecto

1. Clonar el repositorio  
2. Abrir el proyecto en tu IDE (NetBeans, IntelliJ o Eclipse)  
3. Configurar la base de datos en el archivo `application.properties`  

### 🗄️ Base de datos

Antes de ejecutar el script, debes crear la base de datos manualmente en MySQL:

```sql
CREATE DATABASE biblioteca_db;
```

Luego ejecutar el script ubicado en:

```
database/biblioteca-db.sql
```

Asegúrate de configurar correctamente las credenciales en `application.properties`.

---

## 🌐 Base URL

http://localhost:8080/api

---

## 📄 Documentación

Swagger UI disponible en:

http://localhost:8080/swagger-ui.html

---

## 📌 Endpoints principales

### 🔹 Autor

- **POST** `/api/autores` → Crear autor  
- **GET** `/api/autores/{id}` → Obtener autor por ID  
- **PUT** `/api/autores/{id}` → Actualizar autor  
- **DELETE** `/api/autores/{id}` → Eliminar autor  

---

## ⚠️ Manejo de errores

La API implementa un manejo global de excepciones utilizando `@ControllerAdvice`, retornando respuestas estructuradas en formato JSON.

Ejemplo:

```json
{
  "timestamp": "2026-03-15T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "El autor no existe",
  "path": "/api/autores/10"
}
```

---

## 📸 Evidencia de funcionamiento

### 🔹 Vista general Swagger
![Swagger](./images/swagger-overview.png)

### 🔹 Crear autor (POST)
![POST](./images/post-autores.png)

### 🔹 Obtener autores
![GET](./images/get-autores-success.png)

### 🔹 Error 404
![404](./images/get-autor-id-not-found.png)

### 🔹 Eliminación exitosa
![DELETE](./images/delete-autores-success.png)

### 🔹 Reporte PDF
![PDF](./images/pdf-reporte-libros.png)

---

## 🔄 Mejoras en versión 2.0

Esta versión representa una evolución respecto a la versión inicial del proyecto:

- Implementación de **DTOs** para desacoplar entidades de la capa de presentación  
- Incorporación de **validaciones en los request** para asegurar integridad de datos  
- Uso de **logs (SLF4J + Logback)** para seguimiento de operaciones  
- Implementación de **manejo global de excepciones** con respuestas estructuradas  
- Generación de **reportes PDF dinámicos**  
- Mejora en la **arquitectura por capas** (Controller → Service → Repository)  

---