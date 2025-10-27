# 🚀 Task Manager API

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.22-blue.svg)](https://kotlinlang.org)
[![Ktor](https://img.shields.io/badge/Ktor-2.3.7-orange.svg)](https://ktor.io)
[![Koin](https://img.shields.io/badge/Koin-3.5.3-green.svg)](https://insert-koin.io)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

API REST para gestión de tareas construida con **Ktor Server**, **Clean Architecture** y **Koin** para inyección de dependencias.

---

## ✨ Características

- ✅ **Clean Architecture** - Separación clara en capas (Domain, Data, Presentation)
- ✅ **Koin DI** - Inyección de dependencias moderna y ligera
- ✅ **REST API** - Endpoints completos para CRUD de tareas
- ✅ **Kotlin Coroutines** - Programación asíncrona eficiente
- ✅ **Serialización JSON** - Con Kotlinx Serialization
- ✅ **Documentación Completa** - Guías y ejemplos detallados

---

## 🏗️ Arquitectura

Este proyecto implementa **Clean Architecture** con tres capas bien definidas:

### 📦 Domain (Capa de Dominio)
- **Entidades**: `Task` - Modelo de negocio puro
- **Interfaces**: `TaskRepository` - Contratos para acceso a datos
- **Use Cases**: Lógica de negocio encapsulada
  - `GetAllTasksUseCase`
  - `GetTaskByIdUseCase`
  - `CreateTaskUseCase`
  - `UpdateTaskUseCase`
  - `DeleteTaskUseCase`

### 💾 Data (Capa de Datos)
- **DataSource**: Abstracción de la fuente de datos (`TaskDataSourceImpl`)
- **Repository**: Implementación del contrato (`TaskRepositoryImpl`)
- **Storage**: Almacenamiento en memoria con `ConcurrentHashMap`

### 🎯 Presentation (Capa de Presentación)
- **DTOs**: `TaskDto`, `TaskRequest`, `UpdateTaskRequest`
- **Mappers**: Conversión entre entidades de dominio y DTOs
- **Routes**: Endpoints REST (`TaskRoutes.kt`)

### 🔧 Dependency Injection
- **Koin Modules**: Organización modular por capas
  - `DataModule` - Singletons para DataSource y Repository
  - `DomainModule` - Factories para Use Cases

> 📚 Ver [ARCHITECTURE.md](ARCHITECTURE.md) para detalles completos

---

## 🚀 Tecnologías

| Categoría | Tecnología | Versión |
|-----------|------------|---------|
| **Lenguaje** | Kotlin | 1.9.22 |
| **Framework** | Ktor Server | 2.3.7 |
| **DI** | Koin | 3.5.3 |
| **Serialización** | Kotlinx Serialization | 1.6.2 |
| **Logging** | Logback | 1.4.14 |
| **Build Tool** | Gradle | 8.5 |

---

## 📋 API Endpoints

### 🏥 Health Check
```http
GET /
```

**Respuesta:**
```json
{
  "status": "running",
  "service": "Task Manager API",
  "version": "1.0.0",
  "di": "Koin"
}
```

### 📝 Gestión de Tareas

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/tasks` | Obtener todas las tareas |
| `GET` | `/api/tasks/{id}` | Obtener tarea por ID |
| `POST` | `/api/tasks` | Crear nueva tarea |
| `PUT` | `/api/tasks/{id}` | Actualizar tarea |
| `DELETE` | `/api/tasks/{id}` | Eliminar tarea |

> 📚 Ver [API_EXAMPLES.md](API_EXAMPLES.md) para ejemplos completos con curl

---

## 🛠️ Instalación y Ejecución

### Requisitos Previos
- **JDK 17** o superior
- **Git** (opcional)

### Clonar el Repositorio
```bash
git clone https://github.com/tu-usuario/TaskManager.git
cd TaskManager
```

### Compilar el Proyecto
```bash
# Opción 1: Comando estándar
./gradlew build

# Opción 2: Script auto-detección
./auto-compile.sh
```

### Ejecutar el Servidor
```bash
./gradlew run
```

El servidor se iniciará en: **http://localhost:8080**

> 📚 Ver [COMPILE_GUIDE.md](COMPILE_GUIDE.md) para comandos de compilación detallados

---

## 🧪 Ejemplos de Uso

### Crear una Tarea
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Aprender Ktor",
    "description": "Completar tutorial de Ktor Server",
    "isCompleted": false
  }'
```

### Listar Todas las Tareas
```bash
curl http://localhost:8080/api/tasks
```

### Obtener Tarea por ID
```bash
curl http://localhost:8080/api/tasks/{id}
```

### Actualizar una Tarea
```bash
curl -X PUT http://localhost:8080/api/tasks/{id} \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Ktor completado",
    "description": "Tutorial finalizado con éxito",
    "isCompleted": true
  }'
```

### Eliminar una Tarea
```bash
curl -X DELETE http://localhost:8080/api/tasks/{id}
```

---

## 📁 Estructura del Proyecto

```
TaskManager/
├── src/main/kotlin/com/taskmanager/
│   ├── Application.kt                      # Punto de entrada
│   ├── data/
│   │   ├── datasource/
│   │   │   ├── TaskDataSource.kt          # Interface
│   │   │   └── TaskDataSourceImpl.kt      # Implementación
│   │   └── repository/
│   │       ├── TaskRepository.kt          # Interface
│   │       └── TaskRepositoryImpl.kt      # Implementación
│   ├── domain/
│   │   ├── model/
│   │   │   └── Task.kt                    # Entidad
│   │   └── usecase/
│   │       ├── GetAllTasksUseCase.kt
│   │       ├── GetTaskByIdUseCase.kt
│   │       ├── CreateTaskUseCase.kt
│   │       ├── UpdateTaskUseCase.kt
│   │       └── DeleteTaskUseCase.kt
│   ├── presentation/
│   │   ├── dto/
│   │   │   ├── TaskDto.kt
│   │   │   ├── TaskRequest.kt
│   │   │   └── UpdateTaskRequest.kt
│   │   ├── mapper/
│   │   │   └── TaskMapper.kt
│   │   └── routes/
│   │       └── TaskRoutes.kt              # Endpoints REST
│   └── di/
│       ├── DataModule.kt                  # Módulo Koin Data
│       ├── DomainModule.kt                # Módulo Koin Domain
│       └── KoinModules.kt                 # Configuración Koin
├── src/main/resources/
│   └── logback.xml                        # Configuración logging
├── build.gradle.kts                       # Configuración Gradle
├── settings.gradle.kts
└── README.md
```

---

## 📚 Documentación

| Documento | Descripción |
|-----------|-------------|
| [API_EXAMPLES.md](API_EXAMPLES.md) | Ejemplos completos de uso de la API |
| [ARCHITECTURE.md](ARCHITECTURE.md) | Detalles de Clean Architecture |
| [KOIN_DI.md](KOIN_DI.md) | Guía de Koin y DI |
| [MIGRATION_SUMMARY.md](MIGRATION_SUMMARY.md) | Resumen de migración a Koin |
| [COMPILE_GUIDE.md](COMPILE_GUIDE.md) | Guía de compilación por tipo de proyecto |
| [COMPILE_COMMANDS.md](COMPILE_COMMANDS.md) | Referencia rápida de comandos |

---

## 🔧 Configuración de Koin

Este proyecto usa **Koin** para inyección de dependencias:

```kotlin
// Configuración en Application.kt
install(Koin) {
    slf4jLogger()
    modules(appModules)
}

// Inyección en rutas
fun Route.taskRoutes() {
    val getAllTasksUseCase by inject<GetAllTasksUseCase>()
    val createTaskUseCase by inject<CreateTaskUseCase>()
    // ... uso automático
}
```

> 📚 Ver [KOIN_DI.md](KOIN_DI.md) para documentación completa de Koin

---

## 🧪 Testing

### Ejecutar Tests
```bash
./gradlew test
```

### Estructura de Tests (Próximamente)
```kotlin
class TaskRepositoryTest : KoinTest {
    private val repository by inject<TaskRepository>()
    
    @Test
    fun `should create task successfully`() {
        // test implementation
    }
}
```

---

## 🚀 Despliegue

### Build para Producción
```bash
./gradlew build
```

### Ejecutar JAR
```bash
java -jar build/libs/TaskManager-1.0.0.jar
```

### Docker (Próximamente)
```dockerfile
FROM openjdk:17-jdk-slim
COPY build/libs/TaskManager-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

## 🤝 Contribuciones

Las contribuciones son bienvenidas! Por favor:

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/amazing-feature`)
3. Commit tus cambios (`git commit -m 'Add amazing feature'`)
4. Push a la rama (`git push origin feature/amazing-feature`)
5. Abre un Pull Request

---

## 📝 Roadmap

- [ ] Tests unitarios con Koin Test
- [ ] Tests de integración con Ktor Test
- [ ] Persistencia con base de datos (PostgreSQL/MongoDB)
- [ ] Autenticación JWT
- [ ] Documentación OpenAPI/Swagger
- [ ] Docker y Docker Compose
- [ ] CI/CD con GitHub Actions
- [ ] Métricas y monitoring

---

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo [LICENSE](LICENSE) para más detalles.

---

## 👨‍💻 Autor

**Luis O.**

---

## 🙏 Agradecimientos

- [Ktor Framework](https://ktor.io/) - Framework web asíncrono
- [Koin](https://insert-koin.io/) - Framework de DI ligero
- [Kotlin](https://kotlinlang.org/) - Lenguaje de programación moderno

---

## 📞 Soporte

Si tienes preguntas o problemas:
- 📧 Email: [tu-email@example.com]
- 🐛 Issues: [GitHub Issues](https://github.com/tu-usuario/TaskManager/issues)

---

<div align="center">

**⭐ Si te gusta este proyecto, dale una estrella en GitHub! ⭐**

Hecho con ❤️ usando Kotlin y Ktor

</div>
