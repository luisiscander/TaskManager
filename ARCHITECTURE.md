# 🏗️ Project Architecture

## Clean Architecture - Layers

This project follows **Clean Architecture** principles with three clearly defined layers:

```
┌─────────────────────────────────────────────────────────────┐
│                      PRESENTATION                           │
│  (Presentation Layer - REST API)                           │
│                                                             │
│  • TaskRoutes.kt        - REST Endpoints                   │
│  • TaskDto.kt           - Response DTOs                    │
│  • CreateTaskRequest    - Creation DTO                     │
│  • UpdateTaskRequest    - Update DTO                       │
│  • TaskMapper.kt        - Domain ↔ DTO Conversion          │
│                                                             │
└──────────────────┬──────────────────────────────────────────┘
                   │ Dependency
                   ↓
┌─────────────────────────────────────────────────────────────┐
│                        DOMAIN                               │
│  (Domain Layer - Business Logic)                           │
│                                                             │
│  • Task.kt              - Domain Entity                    │
│  • TaskRepository.kt    - Repository Interface             │
│  • GetAllTasksUseCase   - Use Case: List all              │
│  • GetTaskByIdUseCase   - Use Case: Get by ID             │
│  • CreateTaskUseCase    - Use Case: Create                │
│  • UpdateTaskUseCase    - Use Case: Update                │
│  • DeleteTaskUseCase    - Use Case: Delete                │
│                                                             │
└──────────────────┬──────────────────────────────────────────┘
                   │ Dependency
                   ↓
┌─────────────────────────────────────────────────────────────┐
│                         DATA                                │
│  (Data Layer - Data Access)                                │
│                                                             │
│  • TaskRepositoryImpl.kt     - Repository Implementation   │
│  • TaskDataSource.kt         - Data Interface              │
│  • InMemoryTaskDataSource    - In-memory Storage           │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

## 📊 Data Flow

### Example: Create a Task

```
1. CLIENT (cURL/Postman)
   │
   │ POST /api/tasks
   │ { "title": "New task", "description": "...", ... }
   │
   ↓
2. PRESENTATION Layer
   │
   ├─ TaskRoutes.kt
   │  └─ Receives CreateTaskRequest
   │     └─ Validates data
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
   │           └─ Saves to ConcurrentHashMap
   │
   ↓
5. Response (reverse flow)
   │
   │ Task (Domain) → TaskDto (Presentation)
   │
   ↓
6. CLIENT
   │
   │ 201 Created
   │ { "id": "...", "title": "New task", ... }
```

---

## 🎯 Applied Principles

### 1. **Separation of Concerns**
- **Presentation**: Only handles HTTP and DTO conversion
- **Domain**: Only pure business logic
- **Data**: Only data access

### 2. **Dependency Inversion**
- Upper layers depend on abstractions (interfaces)
- Lower layers implement those abstractions
- **Domain** defines `TaskRepository` interface
- **Data** implements `TaskRepositoryImpl`

### 3. **Single Responsibility**
- Each use case has a single responsibility
- Each class has a clear purpose

### 4. **Open/Closed Principle**
- Easy to extend with new use cases
- No need to modify existing code

---

## 🔧 Dependency Injection

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
    // ... other use cases
}
```

**Advantages:**
- ✅ Singleton pattern for global components
- ✅ Lazy initialization (only created when used)
- ✅ Easy to change implementations (e.g., from InMemory to Database)

---

## 🔄 How to Extend the Project

### Add Database Persistence

1. **Create new DataSource implementation:**
```kotlin
class DatabaseTaskDataSource : TaskDataSource {
    // Implement with Room, Exposed, etc.
}
```

2. **Update DependencyInjection:**
```kotlin
private val taskDataSource: TaskDataSource by lazy {
    DatabaseTaskDataSource() // Simple change!
}
```

3. **No other changes needed** ✨
   - Domain remains the same
   - Presentation remains the same
   - Only change Data implementation

---

### Add New Use Case

1. **Create in Domain:**
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

2. **Register in DI:**
```kotlin
val toggleTaskUseCase by lazy {
    ToggleTaskCompletionUseCase(taskRepository)
}
```

3. **Add endpoint in Presentation:**
```kotlin
patch("/{id}/toggle") {
    val id = call.parameters["id"] ?: return@patch ...
    val task = toggleTaskUseCase(id)
    call.respond(task?.toDto() ?: ...)
}
```

---

## 📦 File Structure

```
src/main/kotlin/com/taskmanager/
│
├── Application.kt              # Entry point, Ktor configuration
│
├── di/
│   └── DependencyInjection.kt  # Dependency container
│
├── presentation/               # 🎨 Presentation Layer
│   ├── dto/                    # Data Transfer Objects
│   │   ├── TaskDto.kt
│   │   ├── CreateTaskRequest.kt
│   │   └── UpdateTaskRequest.kt
│   ├── mapper/                 # Domain ↔ DTO Conversion
│   │   └── TaskMapper.kt
│   └── routes/                 # REST Endpoints
│       └── TaskRoutes.kt
│
├── domain/                     # 🎯 Domain Layer
│   ├── model/                  # Entities
│   │   └── Task.kt
│   ├── repository/             # Contracts
│   │   └── TaskRepository.kt
│   └── usecase/                # Business Logic
│       ├── GetAllTasksUseCase.kt
│       ├── GetTaskByIdUseCase.kt
│       ├── CreateTaskUseCase.kt
│       ├── UpdateTaskUseCase.kt
│       └── DeleteTaskUseCase.kt
│
└── data/                       # 💾 Data Layer
    ├── datasource/             # Data Source
    │   ├── TaskDataSource.kt
    │   └── InMemoryTaskDataSource.kt
    └── repository/             # Repository Implementation
        └── TaskRepositoryImpl.kt
```

---

## 🎨 Advantages of This Architecture

### ✅ Testability
- Each layer can be tested independently
- Easy to create mocks of interfaces

### ✅ Maintainability
- Organized and predictable code
- Easy to find where to make changes

### ✅ Scalability
- Easy to add new features
- Can grow without becoming chaotic

### ✅ Flexibility
- Change data source without touching business logic
- Change web framework without touching domain

### ✅ Reusability
- Use cases can be used by different presentations
- Could add GraphQL, WebSockets, CLI, etc.

---

## 🚀 Recommended Next Steps

1. **Add Unit Tests**
   ```kotlin
   class CreateTaskUseCaseTest {
       @Test
       fun `should create task successfully`() {
           // Test with mock repository
       }
   }
   ```

2. **Add Real Persistence**
   - Exposed (SQL)
   - MongoDB
   - Room (Android)

3. **Add Validation**
   - Validation library
   - DTOs with validations

4. **Add Authentication**
   - JWT tokens
   - OAuth2

5. **Add API Documentation**
   - OpenAPI/Swagger
   - Ktor OpenAPI plugin

6. **Add Logging and Monitoring**
   - Structured logging
   - Metrics with Micrometer

---

## 📚 Resources and References

- [Clean Architecture - Uncle Bob](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Ktor Documentation](https://ktor.io/docs/)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [Dependency Inversion Principle](https://en.wikipedia.org/wiki/Dependency_inversion_principle)
