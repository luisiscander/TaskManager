# 🔧 Dependency Injection with Koin

This project uses **Koin** as a dependency injection framework to maintain clean, decoupled, and testable code.

---

## 📚 What is Koin?

**Koin** is a lightweight dependency injection framework for Kotlin that:
- ✅ Uses idiomatic Kotlin DSL
- ✅ Doesn't require code generation (runtime DI)
- ✅ Is easy to configure and use
- ✅ Perfect for Ktor/Backend projects
- ✅ Supports scopes and qualifiers
- ✅ Native integration with Ktor

---

## 🏗️ Module Structure

### 📁 Module Organization

```
di/
├── DataModule.kt       # Data layer module
├── DomainModule.kt     # Domain layer module
└── KoinModules.kt      # Centralized module list
```

### 1️⃣ DataModule.kt

```kotlin
val dataModule = module {
    // Singleton: Single shared instance
    single<TaskDataSource> {
        TaskDataSourceImpl()
    }
    
    single<TaskRepository> {
        TaskRepositoryImpl(get())  // get() injects TaskDataSource
    }
}
```

**Features:**
- `single`: Defines a singleton (single instance)
- `get()`: Gets a registered dependency
- Interfaces for better abstraction

### 2️⃣ DomainModule.kt

```kotlin
val domainModule = module {
    // Factory: New instance each time
    factory {
        GetAllTasksUseCase(get())
    }
    
    factory {
        CreateTaskUseCase(get())
    }
    
    // ... other use cases
}
```

**Features:**
- `factory`: Creates new instance on each injection
- Ideal for use cases (lightweight and stateless)

### 3️⃣ KoinModules.kt

```kotlin
val appModules = listOf(
    dataModule,
    domainModule
)
```

**Purpose:** Centralize all application modules

---

## ⚙️ Configuration in Application.kt

```kotlin
fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()           // Logger with SLF4J
        modules(appModules)     // Load modules
    }
}

fun main() {
    embeddedServer(Netty, port = 8080) {
        configureKoin()         // 1️⃣ Configure Koin first
        configurePlugins()
        configureRouting()
    }.start(wait = true)
}
```

---

## 💉 Injection in Routes

### Before (Manual)

```kotlin
fun Route.taskRoutes(
    getAllTasksUseCase: GetAllTasksUseCase,
    getTaskByIdUseCase: GetTaskByIdUseCase,
    // ... more parameters
) {
    // use the use cases
}

// In Application.kt
taskRoutes(
    DependencyInjection.getAllTasksUseCase,
    DependencyInjection.getTaskByIdUseCase,
    // ... pass manually
)
```

### After (With Koin) ⭐

```kotlin
fun Route.taskRoutes() {
    // Automatic injection with Koin
    val getAllTasksUseCase by inject<GetAllTasksUseCase>()
    val getTaskByIdUseCase by inject<GetTaskByIdUseCase>()
    val createTaskUseCase by inject<CreateTaskUseCase>()
    val updateTaskUseCase by inject<UpdateTaskUseCase>()
    val deleteTaskUseCase by inject<DeleteTaskUseCase>()
    
    route("/api/tasks") {
        get {
            val tasks = getAllTasksUseCase()
            // ...
        }
    }
}

// In Application.kt
taskRoutes()  // No parameters! 🎉
```

**Advantages:**
- ✅ Less boilerplate code
- ✅ No manual dependency passing
- ✅ Easier to add new dependencies

---

## 🔍 Definition Types in Koin

| Type | Syntax | Lifecycle | Use |
|------|--------|-----------|-----|
| **single** | `single { ... }` | Singleton | DataSources, Repositories |
| **factory** | `factory { ... }` | New instance | Use Cases, Presenters |
| **scoped** | `scoped { ... }` | Per scope | Sessions, Requests |

### Examples:

```kotlin
module {
    // Singleton: Shared globally
    single<Database> { DatabaseImpl() }
    
    // Factory: New instance each time
    factory<UseCase> { UseCaseImpl(get()) }
    
    // Named: For multiple implementations
    single<Repository>(named("local")) { LocalRepository() }
    single<Repository>(named("remote")) { RemoteRepository() }
    
    // Scoped: Per specific scope
    scope<UserSession> {
        scoped { UserPreferences() }
    }
}
```

---

## 🧪 Testing with Koin

### Test Example

```kotlin
class TaskRepositoryTest : KoinTest {
    
    @Before
    fun setup() {
        startKoin {
            modules(testModule)
        }
    }
    
    @After
    fun tearDown() {
        stopKoin()
    }
    
    @Test
    fun `test repository`() {
        val repository by inject<TaskRepository>()
        // ... test
    }
}

// Test module
val testModule = module {
    single<TaskDataSource> { MockTaskDataSource() }
    single<TaskRepository> { TaskRepositoryImpl(get()) }
}
```

---

## 📊 Advantages vs Disadvantages

### ✅ Koin Advantages

1. **Simplicity:** Clean and readable Kotlin DSL
2. **No code generation:** Runtime DI
3. **Lightweight:** Small footprint
4. **Easy debugging:** Clear runtime errors
5. **Ktor integration:** Native `koin-ktor` plugin
6. **Testing friendly:** Easy to create test modules

### ⚠️ Considerations

1. **Runtime DI:** Errors at runtime (not compile-time)
2. **Performance:** Slightly slower than Dagger (compile-time)
3. **Type-safety:** Less strict than Dagger

---

## 🆚 Comparison: Koin vs Other Frameworks

| Feature | Koin | Dagger/Hilt | Manual |
|---------|------|-------------|--------|
| Complexity | 🟢 Low | 🔴 High | 🟡 Medium |
| Learning Curve | 🟢 Gentle | 🔴 Steep | 🟢 None |
| Performance | 🟡 Good | 🟢 Excellent | 🟢 Excellent |
| Compile Safety | 🟡 Runtime | 🟢 Compile-time | 🔴 None |
| Boilerplate | 🟢 Minimum | 🔴 Much | 🟡 Medium |
| Backend (Ktor) | 🟢 Ideal | 🔴 Not compatible | 🟢 OK |
| Android | 🟢 Compatible | 🟢 Native | 🟡 Tedious |

**Verdict for Ktor Backend:** ✅ **Koin is the best choice**

---

## 🚀 Migrate from Manual to Koin

### Steps performed in this project:

1. ✅ **Add dependencies:**
   ```kotlin
   implementation("io.insert-koin:koin-ktor:3.5.3")
   implementation("io.insert-koin:koin-logger-slf4j:3.5.3")
   ```

2. ✅ **Create Koin modules:**
   - DataModule: DataSource and Repository
   - DomainModule: Use Cases

3. ✅ **Configure Koin in Application:**
   ```kotlin
   install(Koin) {
       slf4jLogger()
       modules(appModules)
   }
   ```

4. ✅ **Refactor routes:**
   - Use `inject<T>()` instead of parameters
   - Remove manual dependency passing

5. ✅ **Remove manual code:**
   - Delete `DependencyInjection.kt` object

---

## 📖 Resources and References

- [Koin Documentation](https://insert-koin.io/)
- [Koin + Ktor Guide](https://insert-koin.io/docs/reference/koin-ktor/ktor/)
- [Koin GitHub](https://github.com/InsertKoinIO/koin)
- [Koin vs Dagger](https://insert-koin.io/docs/reference/introduction/koin-dagger)

---

## 💡 Next Steps

### Suggested Improvements:

1. **Add Unit Tests:**
   ```kotlin
   testImplementation("io.insert-koin:koin-test:3.5.3")
   ```

2. **Use Scopes for different contexts:**
   ```kotlin
   scope<ApiRequest> {
       scoped { RequestContext() }
   }
   ```

3. **Qualifiers for multiple implementations:**
   ```kotlin
   single<Cache>(named("memory")) { MemoryCache() }
   single<Cache>(named("disk")) { DiskCache() }
   ```

4. **Profiles for different environments:**
   ```kotlin
   val devModule = module { /* ... */ }
   val prodModule = module { /* ... */ }
   
   modules(if (isDev) devModule else prodModule)
   ```

---

## 🎓 Best Practices

1. ✅ **Organize by layers:** One module per layer (Data, Domain, Presentation)
2. ✅ **Use interfaces:** For better abstraction and testing
3. ✅ **Single for singletons:** Repositories, DataSources, Clients
4. ✅ **Factory for logic:** Use Cases, Presenters
5. ✅ **Name dependencies:** When there are multiple implementations
6. ✅ **Logs in development:** `slf4jLogger()` for debugging
7. ✅ **Test modules:** Create separate modules for testing

---

**Implemented in:** TaskManager - Ktor Backend with Clean Architecture
**Koin Version:** 3.5.3
