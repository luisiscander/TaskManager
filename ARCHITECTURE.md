# 🏗️ Arquitectura del Proyecto

## Clean Architecture - Capas

Este proyecto sigue los principios de **Clean Architecture** con tres capas claramente definidas:

```
┌─────────────────────────────────────────────────────────────┐
│                      PRESENTATION                           │
│  (Capa de Presentación - API REST)                         │
│                                                             │
│  • TaskRoutes.kt        - Endpoints REST                   │
│  • TaskDto.kt           - DTOs de respuesta                │
│  • CreateTaskRequest    - DTO de creación                  │
│  • UpdateTaskRequest    - DTO de actualización             │
│  • TaskMapper.kt        - Conversión Domain ↔ DTO          │
│                                                             │
└──────────────────┬──────────────────────────────────────────┘
                   │ Dependencia
                   ↓
┌─────────────────────────────────────────────────────────────┐
│                        DOMAIN                               │
│  (Capa de Dominio - Lógica de Negocio)                    │
│                                                             │
│  • Task.kt              - Entidad del dominio              │
│  • TaskRepository.kt    - Interface del repositorio        │
│  • GetAllTasksUseCase   - Caso de uso: Listar             │
│  • GetTaskByIdUseCase   - Caso de uso: Obtener por ID     │
│  • CreateTaskUseCase    - Caso de uso: Crear              │
│  • UpdateTaskUseCase    - Caso de uso: Actualizar         │
│  • DeleteTaskUseCase    - Caso de uso: Eliminar           │
│                                                             │
└──────────────────┬──────────────────────────────────────────┘
                   │ Dependencia
                   ↓
┌─────────────────────────────────────────────────────────────┐
│                         DATA                                │
│  (Capa de Datos - Acceso a Datos)                         │
│                                                             │
│  • TaskRepositoryImpl.kt     - Implementación repo         │
│  • TaskDataSource.kt         - Interface de datos          │
│  • InMemoryTaskDataSource    - Almacenamiento en memoria   │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

## 📊 Flujo de Datos

### Ejemplo: Crear una Tarea

```
1. CLIENT (cURL/Postman)
   │
   │ POST /api/tasks
   │ { "title": "Nueva tarea", "description": "...", ... }
   │
   ↓
2. PRESENTATION Layer
   │
   ├─ TaskRoutes.kt
   │  └─ Recibe CreateTaskRequest
   │     └─ Valida datos
   │        └─ TaskMapper.toDomain()
   │
   ↓
3. DOMAIN Layer
   │
   ├─ CreateTaskUseCase
   │  └─ invoke(task: Task)
   │     └─ TaskRepository.createTask()
   │
   ↓
4. DATA Layer
   │
   ├─ TaskRepositoryImpl
   │  └─ createTask(task: Task)
   │     └─ TaskDataSource.insertTask()
   │        └─ InMemoryTaskDataSource
   │           └─ Guarda en ConcurrentHashMap
   │
   ↓
5. Response (flujo inverso)
   │
   │ Task (Domain) → TaskDto (Presentation)
   │
   ↓
6. CLIENT
   │
   │ 201 Created
   │ { "id": "...", "title": "Nueva tarea", ... }
```

---

## 🎯 Principios Aplicados

### 1. **Separación de Responsabilidades**
- **Presentation**: Solo maneja HTTP y conversión de DTOs
- **Domain**: Solo lógica de negocio pura
- **Data**: Solo acceso a datos

### 2. **Dependency Inversion**
- Las capas superiores dependen de abstracciones (interfaces)
- Las capas inferiores implementan esas abstracciones
- **Domain** define `TaskRepository` interface
- **Data** implementa `TaskRepositoryImpl`

### 3. **Single Responsibility**
- Cada caso de uso tiene una responsabilidad única
- Cada clase tiene un propósito claro

### 4. **Open/Closed Principle**
- Fácil extender con nuevos casos de uso
- No necesitas modificar código existente

---

## 🔧 Inyección de Dependencias

```kotlin
// di/DependencyInjection.kt

object DependencyInjection {
    
    // Data Layer
    private val taskDataSource: TaskDataSource by lazy {
        InMemoryTaskDataSource()
    }
    
    // Domain Layer
    private val taskRepository: TaskRepository by lazy {
        TaskRepositoryImpl(taskDataSource)
    }
    
    // Use Cases
    val getAllTasksUseCase by lazy {
        GetAllTasksUseCase(taskRepository)
    }
    // ... otros use cases
}
```

**Ventajas:**
- ✅ Singleton pattern para componentes globales
- ✅ Lazy initialization (solo se crea cuando se usa)
- ✅ Fácil de cambiar implementaciones (ej: de InMemory a Database)

---

## 🔄 Cómo Extender el Proyecto

### Agregar persistencia con Base de Datos

1. **Crear nueva implementación de DataSource:**
```kotlin
class DatabaseTaskDataSource : TaskDataSource {
    // Implementar con Room, Exposed, etc.
}
```

2. **Actualizar DependencyInjection:**
```kotlin
private val taskDataSource: TaskDataSource by lazy {
    DatabaseTaskDataSource() // Cambio simple!
}
```

3. **No es necesario cambiar nada más** ✨
   - Domain permanece igual
   - Presentation permanece igual
   - Solo cambias la implementación de Data

---

### Agregar nuevo caso de uso

1. **Crear en Domain:**
```kotlin
class ToggleTaskCompletionUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(id: String): Task? {
        val task = repository.getTaskById(id) ?: return null
        return repository.updateTask(
            task.copy(isCompleted = !task.isCompleted)
        )
    }
}
```

2. **Registrar en DI:**
```kotlin
val toggleTaskUseCase by lazy {
    ToggleTaskCompletionUseCase(taskRepository)
}
```

3. **Agregar endpoint en Presentation:**
```kotlin
patch("/{id}/toggle") {
    val id = call.parameters["id"] ?: return@patch ...
    val task = toggleTaskUseCase(id)
    call.respond(task?.toDto() ?: ...)
}
```

---

## 📦 Estructura de Archivos

```
src/main/kotlin/com/taskmanager/
│
├── Application.kt              # Punto de entrada, configuración Ktor
│
├── di/
│   └── DependencyInjection.kt  # Container de dependencias
│
├── presentation/               # 🎨 Capa de Presentación
│   ├── dto/                    # Data Transfer Objects
│   │   ├── TaskDto.kt
│   │   ├── CreateTaskRequest.kt
│   │   └── UpdateTaskRequest.kt
│   ├── mapper/                 # Conversión Domain ↔ DTO
│   │   └── TaskMapper.kt
│   └── routes/                 # Endpoints REST
│       └── TaskRoutes.kt
│
├── domain/                     # 🎯 Capa de Dominio
│   ├── model/                  # Entidades
│   │   └── Task.kt
│   ├── repository/             # Contratos
│   │   └── TaskRepository.kt
│   └── usecase/                # Lógica de negocio
│       ├── GetAllTasksUseCase.kt
│       ├── GetTaskByIdUseCase.kt
│       ├── CreateTaskUseCase.kt
│       ├── UpdateTaskUseCase.kt
│       └── DeleteTaskUseCase.kt
│
└── data/                       # 💾 Capa de Datos
    ├── datasource/             # Fuente de datos
    │   ├── TaskDataSource.kt
    │   └── InMemoryTaskDataSource.kt
    └── repository/             # Implementación del repositorio
        └── TaskRepositoryImpl.kt
```

---

## 🎨 Ventajas de esta Arquitectura

### ✅ Testabilidad
- Cada capa se puede testear independientemente
- Fácil crear mocks de interfaces

### ✅ Mantenibilidad
- Código organizado y predecible
- Fácil encontrar dónde hacer cambios

### ✅ Escalabilidad
- Fácil agregar nuevas features
- Puede crecer sin volverse caótico

### ✅ Flexibilidad
- Cambiar la fuente de datos sin tocar lógica de negocio
- Cambiar el framework web sin tocar el dominio

### ✅ Reutilización
- Los use cases pueden ser usados por diferentes presentaciones
- Podrías agregar GraphQL, WebSockets, CLI, etc.

---

## 🚀 Próximos Pasos Recomendados

1. **Agregar Tests Unitarios**
   ```kotlin
   class CreateTaskUseCaseTest {
       @Test
       fun `should create task successfully`() {
           // Test con mock repository
       }
   }
   ```

2. **Agregar Persistencia Real**
   - Exposed (SQL)
   - MongoDB
   - Room (Android)

3. **Agregar Validación**
   - Librería de validación
   - DTOs con validaciones

4. **Agregar Autenticación**
   - JWT tokens
   - OAuth2

5. **Agregar Documentación API**
   - OpenAPI/Swagger
   - Ktor OpenAPI plugin

6. **Agregar Logging y Monitoring**
   - Structured logging
   - Metrics con Micrometer

---

## 📚 Recursos y Referencias

- [Clean Architecture - Uncle Bob](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Ktor Documentation](https://ktor.io/docs/)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [Dependency Inversion Principle](https://en.wikipedia.org/wiki/Dependency_inversion_principle)

