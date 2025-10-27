# Task Manager API

API REST para gestión de tareas construida con Ktor y Clean Architecture.

## 🏗️ Arquitectura

El proyecto sigue los principios de Clean Architecture con tres capas principales:

### 📦 Domain (Capa de Dominio)
- **Entidades**: Modelos de negocio puros (`Task`)
- **Repository Interface**: Contratos para acceso a datos
- **Use Cases**: Lógica de negocio encapsulada
  - GetAllTasksUseCase
  - GetTaskByIdUseCase
  - CreateTaskUseCase
  - UpdateTaskUseCase
  - DeleteTaskUseCase

### 💾 Data (Capa de Datos)
- **DataSource**: Abstracción de la fuente de datos
- **Repository Implementation**: Implementación del contrato del repositorio
- **InMemoryDataSource**: Almacenamiento en memoria (para simplificar)

### 🎯 Presentation (Capa de Presentación)
- **DTOs**: Objetos de transferencia de datos
- **Mappers**: Conversión entre entidades de dominio y DTOs
- **Routes**: Endpoints REST de la API

## 🚀 Tecnologías

- **Kotlin** - Lenguaje de programación
- **Ktor 2.3.7** - Framework web asíncrono
- **Kotlinx Serialization** - Serialización JSON
- **Logback** - Sistema de logging

## 📋 Endpoints de la API

### GET /
Health check del servidor
```json
{
  "status": "running",
  "service": "Task Manager API",
  "version": "1.0.0"
}
```

### GET /api/tasks
Obtiene todas las tareas
```json
[
  {
    "id": "uuid",
    "title": "Tarea de ejemplo",
    "description": "Descripción de la tarea",
    "isCompleted": false,
    "createdAt": 1234567890
  }
]
```

### GET /api/tasks/{id}
Obtiene una tarea por su ID

### POST /api/tasks
Crea una nueva tarea
```json
{
  "title": "Nueva tarea",
  "description": "Descripción de la tarea",
  "isCompleted": false
}
```

### PUT /api/tasks/{id}
Actualiza una tarea existente
```json
{
  "title": "Tarea actualizada",
  "description": "Descripción actualizada",
  "isCompleted": true
}
```

### DELETE /api/tasks/{id}
Elimina una tarea por su ID

## 🛠️ Configuración y Ejecución

### Requisitos
- JDK 17 o superior
- Gradle (incluido con wrapper)

### Ejecutar el servidor
```bash
./gradlew run
```

El servidor se iniciará en `http://localhost:8080`

### Compilar el proyecto
```bash
./gradlew build
```

## 📝 Estructura del Proyecto

```
TaskManager/
├── src/
│   └── main/
│       ├── kotlin/
│       │   └── com/
│       │       └── taskmanager/
│       │           ├── data/
│       │           │   ├── datasource/
│       │           │   └── repository/
│       │           ├── domain/
│       │           │   ├── model/
│       │           │   ├── repository/
│       │           │   └── usecase/
│       │           ├── presentation/
│       │           │   ├── dto/
│       │           │   ├── mapper/
│       │           │   └── routes/
│       │           ├── di/
│       │           └── Application.kt
│       └── resources/
│           └── logback.xml
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## 🧪 Ejemplos de Uso

### Crear una tarea
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Completar proyecto",
    "description": "Finalizar el CRUD de tareas",
    "isCompleted": false
  }'
```

### Obtener todas las tareas
```bash
curl http://localhost:8080/api/tasks
```

### Actualizar una tarea
```bash
curl -X PUT http://localhost:8080/api/tasks/{id} \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Proyecto completado",
    "description": "CRUD de tareas finalizado",
    "isCompleted": true
  }'
```

### Eliminar una tarea
```bash
curl -X DELETE http://localhost:8080/api/tasks/{id}
```

## 📄 Licencia

Este proyecto es de código abierto y está disponible bajo la licencia MIT.

