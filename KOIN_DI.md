# 🔧 Inyección de Dependencias con Koin

Este proyecto utiliza **Koin** como framework de inyección de dependencias para mantener un código limpio, desacoplado y fácil de testear.

---

## 📚 ¿Qué es Koin?

**Koin** es un framework de inyección de dependencias ligero para Kotlin que:
- ✅ Usa DSL idiomático de Kotlin
- ✅ No requiere generación de código (runtime DI)
- ✅ Es fácil de configurar y usar
- ✅ Perfecto para proyectos Ktor/Backend
- ✅ Soporta scopes y cualificadores
- ✅ Integración nativa con Ktor

---

## 🏗️ Estructura de Módulos

### 📁 Organización de Módulos

```
di/
├── DataModule.kt       # Módulo de capa de datos
├── DomainModule.kt     # Módulo de capa de dominio
└── KoinModules.kt      # Lista centralizada de módulos
```

### 1️⃣ DataModule.kt

```kotlin
val dataModule = module {
    // Singleton: Una única instancia compartida
    single<TaskDataSource> {
        TaskDataSourceImpl()
    }
    
    single<TaskRepository> {
        TaskRepositoryImpl(get())  // get() inyecta TaskDataSource
    }
}
```

**Características:**
- `single`: Define un singleton (una sola instancia)
- `get()`: Obtiene una dependencia registrada
- Interfaces para mejor abstracción

### 2️⃣ DomainModule.kt

```kotlin
val domainModule = module {
    // Factory: Nueva instancia cada vez
    factory {
        GetAllTasksUseCase(get())
    }
    
    factory {
        CreateTaskUseCase(get())
    }
    
    // ... otros use cases
}
```

**Características:**
- `factory`: Crea nueva instancia en cada inyección
- Ideal para use cases (ligeros y sin estado)

### 3️⃣ KoinModules.kt

```kotlin
val appModules = listOf(
    dataModule,
    domainModule
)
```

**Propósito:** Centralizar todos los módulos de la aplicación

---

## ⚙️ Configuración en Application.kt

```kotlin
fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()           // Logger con SLF4J
        modules(appModules)     // Cargar módulos
    }
}

fun main() {
    embeddedServer(Netty, port = 8080) {
        configureKoin()         // 1️⃣ Configurar Koin primero
        configurePlugins()
        configureRouting()
    }.start(wait = true)
}
```

---

## 💉 Inyección en Rutas

### Antes (Manual)

```kotlin
fun Route.taskRoutes(
    getAllTasksUseCase: GetAllTasksUseCase,
    getTaskByIdUseCase: GetTaskByIdUseCase,
    // ... más parámetros
) {
    // usar los use cases
}

// En Application.kt
taskRoutes(
    DependencyInjection.getAllTasksUseCase,
    DependencyInjection.getTaskByIdUseCase,
    // ... pasar manualmente
)
```

### Después (Con Koin) ⭐

```kotlin
fun Route.taskRoutes() {
    // Inyección automática con Koin
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

// En Application.kt
taskRoutes()  // ¡Sin parámetros! 🎉
```

**Ventajas:**
- ✅ Menos código boilerplate
- ✅ No pasar dependencias manualmente
- ✅ Más fácil agregar nuevas dependencias

---

## 🔍 Tipos de Definiciones en Koin

| Tipo | Sintaxis | Ciclo de Vida | Uso |
|------|----------|---------------|-----|
| **single** | `single { ... }` | Singleton | DataSources, Repositories |
| **factory** | `factory { ... }` | Nueva instancia | Use Cases, Presenters |
| **scoped** | `scoped { ... }` | Por scope | Sesiones, Requests |

### Ejemplos:

```kotlin
module {
    // Singleton: Compartido globalmente
    single<Database> { DatabaseImpl() }
    
    // Factory: Nueva instancia cada vez
    factory<UseCase> { UseCaseImpl(get()) }
    
    // Named: Para múltiples implementaciones
    single<Repository>(named("local")) { LocalRepository() }
    single<Repository>(named("remote")) { RemoteRepository() }
    
    // Scoped: Por ámbito específico
    scope<UserSession> {
        scoped { UserPreferences() }
    }
}
```

---

## 🧪 Testing con Koin

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

// Módulo de test
val testModule = module {
    single<TaskDataSource> { MockTaskDataSource() }
    single<TaskRepository> { TaskRepositoryImpl(get()) }
}
```

---

## 📊 Ventajas vs Desventajas

### ✅ Ventajas de Koin

1. **Simplicidad:** DSL Kotlin limpio y legible
2. **Sin generación de código:** Runtime DI
3. **Ligero:** Pequeña footprint
4. **Fácil debugging:** Errores claros en runtime
5. **Integración Ktor:** Plugin nativo `koin-ktor`
6. **Testing friendly:** Fácil crear módulos de test

### ⚠️ Consideraciones

1. **Runtime DI:** Errores en tiempo de ejecución (no compile-time)
2. **Performance:** Mínimamente más lento que Dagger (compile-time)
3. **Type-safety:** Menos estricto que Dagger

---

## 🆚 Comparación: Koin vs Otros Frameworks

| Característica | Koin | Dagger/Hilt | Manual |
|----------------|------|-------------|--------|
| Complejidad | 🟢 Baja | 🔴 Alta | 🟡 Media |
| Curva Aprendizaje | 🟢 Suave | 🔴 Empinada | 🟢 Ninguna |
| Performance | 🟡 Buena | 🟢 Excelente | 🟢 Excelente |
| Compile Safety | 🟡 Runtime | 🟢 Compile-time | 🔴 Ninguna |
| Boilerplate | 🟢 Mínimo | 🔴 Mucho | 🟡 Medio |
| Backend (Ktor) | 🟢 Ideal | 🔴 No compatible | 🟢 OK |
| Android | 🟢 Compatible | 🟢 Nativo | 🟡 Tedioso |

**Veredicto para Ktor Backend:** ✅ **Koin es la mejor opción**

---

## 🚀 Migrar de Manual a Koin

### Pasos realizados en este proyecto:

1. ✅ **Agregar dependencias:**
   ```kotlin
   implementation("io.insert-koin:koin-ktor:3.5.3")
   implementation("io.insert-koin:koin-logger-slf4j:3.5.3")
   ```

2. ✅ **Crear módulos de Koin:**
   - DataModule: DataSource y Repository
   - DomainModule: Use Cases

3. ✅ **Configurar Koin en Application:**
   ```kotlin
   install(Koin) {
       slf4jLogger()
       modules(appModules)
   }
   ```

4. ✅ **Refactorizar rutas:**
   - Usar `inject<T>()` en lugar de parámetros
   - Eliminar paso manual de dependencias

5. ✅ **Eliminar código manual:**
   - Borrar `DependencyInjection.kt` object

---

## 📖 Recursos y Referencias

- [Koin Documentation](https://insert-koin.io/)
- [Koin + Ktor Guide](https://insert-koin.io/docs/reference/koin-ktor/ktor/)
- [Koin GitHub](https://github.com/InsertKoinIO/koin)
- [Koin vs Dagger](https://insert-koin.io/docs/reference/introduction/koin-dagger)

---

## 💡 Próximos Pasos

### Mejoras Sugeridas:

1. **Agregar Tests Unitarios:**
   ```kotlin
   testImplementation("io.insert-koin:koin-test:3.5.3")
   ```

2. **Usar Scopes para diferentes contextos:**
   ```kotlin
   scope<ApiRequest> {
       scoped { RequestContext() }
   }
   ```

3. **Cualificadores para múltiples implementaciones:**
   ```kotlin
   single<Cache>(named("memory")) { MemoryCache() }
   single<Cache>(named("disk")) { DiskCache() }
   ```

4. **Profiles para diferentes ambientes:**
   ```kotlin
   val devModule = module { /* ... */ }
   val prodModule = module { /* ... */ }
   
   modules(if (isDev) devModule else prodModule)
   ```

---

## 🎓 Best Practices

1. ✅ **Organizar por capas:** Un módulo por capa (Data, Domain, Presentation)
2. ✅ **Usar interfaces:** Para mejor abstracción y testing
3. ✅ **Single para singletons:** Repositories, DataSources, Clients
4. ✅ **Factory para lógica:** Use Cases, Presenters
5. ✅ **Nombrar dependencias:** Cuando hay múltiples implementaciones
6. ✅ **Logs en desarrollo:** `slf4jLogger()` para debug
7. ✅ **Módulos de test:** Crear módulos separados para testing

---

**Implementado en:** TaskManager - Ktor Backend con Clean Architecture
**Versión de Koin:** 3.5.3

