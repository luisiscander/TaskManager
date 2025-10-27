# 🚀 Task Manager API

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.22-blue.svg)](https://kotlinlang.org)
[![Ktor](https://img.shields.io/badge/Ktor-2.3.7-orange.svg)](https://ktor.io)
[![Koin](https://img.shields.io/badge/Koin-3.5.3-green.svg)](https://insert-koin.io)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

A RESTful API for task management built with **Ktor Server**, **Clean Architecture**, and **Koin** for dependency injection.

---

## ✨ Features

- ✅ **Clean Architecture** - Clear separation of concerns (Domain, Data, Presentation)
- ✅ **Koin DI** - Modern and lightweight dependency injection
- ✅ **REST API** - Complete CRUD endpoints for tasks
- ✅ **Kotlin Coroutines** - Efficient asynchronous programming
- ✅ **JSON Serialization** - With Kotlinx Serialization
- ✅ **Comprehensive Documentation** - Detailed guides and examples

---

## 🏗️ Architecture

This project implements **Clean Architecture** with three well-defined layers:

### 📦 Domain Layer
- **Entities**: `Task` - Pure business model
- **Interfaces**: `TaskRepository` - Data access contracts
- **Use Cases**: Encapsulated business logic
  - `GetAllTasksUseCase`
  - `GetTaskByIdUseCase`
  - `CreateTaskUseCase`
  - `UpdateTaskUseCase`
  - `DeleteTaskUseCase`

### 💾 Data Layer
- **DataSource**: Data source abstraction (`TaskDataSourceImpl`)
- **Repository**: Contract implementation (`TaskRepositoryImpl`)
- **Storage**: In-memory storage with `ConcurrentHashMap`

### 🎯 Presentation Layer
- **DTOs**: `TaskDto`, `TaskRequest`, `UpdateTaskRequest`
- **Mappers**: Conversion between domain entities and DTOs
- **Routes**: REST endpoints (`TaskRoutes.kt`)

### 🔧 Dependency Injection
- **Koin Modules**: Modular organization by layers
  - `DataModule` - Singletons for DataSource and Repository
  - `DomainModule` - Factories for Use Cases

> 📚 See [ARCHITECTURE.md](ARCHITECTURE.md) for complete details

---

## 🚀 Technologies

| Category | Technology | Version |
|-----------|------------|---------|
| **Language** | Kotlin | 1.9.22 |
| **Framework** | Ktor Server | 2.3.7 |
| **DI** | Koin | 3.5.3 |
| **Serialization** | Kotlinx Serialization | 1.6.2 |
| **Logging** | Logback | 1.4.14 |
| **Build Tool** | Gradle | 8.5 |

---

## 📋 API Endpoints

### 🏥 Health Check
```http
GET /
```

**Response:**
```json
{
  "status": "running",
  "service": "Task Manager API",
  "version": "1.0.0",
  "di": "Koin"
}
```

### 📝 Task Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/tasks` | Get all tasks |
| `GET` | `/api/tasks/{id}` | Get task by ID |
| `POST` | `/api/tasks` | Create new task |
| `PUT` | `/api/tasks/{id}` | Update task |
| `DELETE` | `/api/tasks/{id}` | Delete task |

> 📚 See [API_EXAMPLES.md](API_EXAMPLES.md) for complete examples with curl

---

## 🛠️ Installation and Setup

### Prerequisites
- **JDK 17** or higher
- **Git** (optional)

### Clone the Repository
```bash
git clone https://github.com/luisiscander/TaskManager.git
cd TaskManager
```

### Build the Project
```bash
# Option 1: Standard command
./gradlew build

# Option 2: Auto-detection script
./auto-compile.sh
```

### Run the Server
```bash
./gradlew run
```

The server will start on: **http://localhost:8080**

> 📚 See [COMPILE_GUIDE.md](COMPILE_GUIDE.md) for detailed compilation commands

---

## 🧪 Usage Examples

### Create a Task
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Learn Ktor",
    "description": "Complete Ktor Server tutorial",
    "isCompleted": false
  }'
```

### List All Tasks
```bash
curl http://localhost:8080/api/tasks
```

### Get Task by ID
```bash
curl http://localhost:8080/api/tasks/{id}
```

### Update a Task
```bash
curl -X PUT http://localhost:8080/api/tasks/{id} \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Ktor completed",
    "description": "Tutorial successfully finished",
    "isCompleted": true
  }'
```

### Delete a Task
```bash
curl -X DELETE http://localhost:8080/api/tasks/{id}
```

---

## 📁 Project Structure

```
TaskManager/
├── src/main/kotlin/com/taskmanager/
│   ├── Application.kt                      # Entry point
│   ├── data/
│   │   ├── datasource/
│   │   │   ├── TaskDataSource.kt          # Interface
│   │   │   └── TaskDataSourceImpl.kt      # Implementation
│   │   └── repository/
│   │       ├── TaskRepository.kt          # Interface
│   │       └── TaskRepositoryImpl.kt      # Implementation
│   ├── domain/
│   │   ├── model/
│   │   │   └── Task.kt                    # Entity
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
│   │       └── TaskRoutes.kt              # REST Endpoints
│   └── di/
│       ├── DataModule.kt                  # Koin Data Module
│       ├── DomainModule.kt                # Koin Domain Module
│       └── KoinModules.kt                 # Koin Configuration
├── src/main/resources/
│   └── logback.xml                        # Logging configuration
├── build.gradle.kts                       # Gradle configuration
├── settings.gradle.kts
└── README.md
```

---

## 📚 Documentation

| Document | Description |
|-----------|-------------|
| [API_EXAMPLES.md](API_EXAMPLES.md) | Complete API usage examples |
| [ARCHITECTURE.md](ARCHITECTURE.md) | Clean Architecture details |
| [KOIN_DI.md](KOIN_DI.md) | Koin and DI guide |
| [MIGRATION_SUMMARY.md](MIGRATION_SUMMARY.md) | Migration summary to Koin |
| [COMPILE_GUIDE.md](COMPILE_GUIDE.md) | Compilation guide by project type |
| [COMPILE_COMMANDS.md](COMPILE_COMMANDS.md) | Quick command reference |

---

## 🔧 Koin Configuration

This project uses **Koin** for dependency injection:

```kotlin
// Configuration in Application.kt
install(Koin) {
    slf4jLogger()
    modules(appModules)
}

// Injection in routes
fun Route.taskRoutes() {
    val getAllTasksUseCase by inject<GetAllTasksUseCase>()
    val createTaskUseCase by inject<CreateTaskUseCase>()
    // ... automatic usage
}
```

> 📚 See [KOIN_DI.md](KOIN_DI.md) for complete Koin documentation

---

## 🧪 Testing

### Run Tests
```bash
./gradlew test
```

### Test Structure (Coming Soon)
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

## 🚀 Deployment

### Build for Production
```bash
./gradlew build
```

### Run JAR
```bash
java -jar build/libs/TaskManager-1.0.0.jar
```

### Docker (Coming Soon)
```dockerfile
FROM openjdk:17-jdk-slim
COPY build/libs/TaskManager-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

## 🤝 Contributing

Contributions are welcome! Please:

1. Fork the project
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## 📝 Roadmap

- [ ] Unit tests with Koin Test
- [ ] Integration tests with Ktor Test
- [ ] Database persistence (PostgreSQL/MongoDB)
- [ ] JWT Authentication
- [ ] OpenAPI/Swagger documentation
- [ ] Docker and Docker Compose
- [ ] CI/CD with GitHub Actions
- [ ] Metrics and monitoring

---

## 📄 License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

## 👨‍💻 Author

**Luis O.**

---

## 📞 Support

If you have questions or issues:
- 📧 Email: [your-email@example.com]
- 🐛 Issues: [GitHub Issues](https://github.com/luisiscander/TaskManager/issues)

---

<div align="center">

**⭐ If you like this project, give it a star on GitHub! ⭐**

Made with ❤️ using Kotlin and Ktor

</div>
