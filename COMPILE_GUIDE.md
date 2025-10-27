# 📦 Guía de Compilación - Comandos por Tipo de Proyecto

Esta guía documenta los comandos correctos de compilación según el tipo de proyecto.

---

## 🎯 Identificar el Tipo de Proyecto

| Tipo | Indicadores | Archivo Principal |
|------|-------------|-------------------|
| **Android (Frontend Móvil)** | `build.gradle` con Android plugin | `app/build.gradle` |
| **Ktor/Backend (JVM)** | `build.gradle.kts` con Ktor | `build.gradle.kts` |
| **React/Node.js (Frontend Web)** | `package.json` | `package.json` |
| **Spring Boot (Backend)** | `pom.xml` o `build.gradle` con Spring | Variable |

---

## 📱 ANDROID (Frontend Móvil)

### Compilación Debug
```bash
./gradlew compileDebugKotlin
```

### Compilación Release
```bash
./gradlew compileReleaseKotlin
```

### Build Completo Debug
```bash
./gradlew assembleDebug
```

### Build Completo Release
```bash
./gradlew assembleRelease
```

### Verificar Errores
```bash
./gradlew compileDebugKotlin --console=plain
```

---

## 🖥️ KTOR SERVER (Backend JVM) ⭐ **Proyecto Actual**

### Compilación Kotlin
```bash
./gradlew compileKotlin
```

### Build Completo
```bash
./gradlew build
```

### Build con Logs Detallados
```bash
./gradlew build --console=plain
```

### Limpiar y Compilar
```bash
./gradlew clean build
```

### Ejecutar el Servidor
```bash
./gradlew run
```

### Generar JAR Ejecutable
```bash
./gradlew jar
```

---

## 🌱 SPRING BOOT (Backend JVM)

### Maven
```bash
# Compilar
mvn compile

# Build completo
mvn clean package

# Sin tests
mvn clean package -DskipTests
```

### Gradle
```bash
# Compilar
./gradlew compileJava

# Build completo
./gradlew build

# Sin tests
./gradlew build -x test
```

---

## ⚛️ REACT / NODE.JS (Frontend Web)

### Verificar Sintaxis
```bash
npm run type-check  # Si usa TypeScript
```

### Build Desarrollo
```bash
npm run dev
# o
npm start
```

### Build Producción
```bash
npm run build
```

### Verificar Errores de Lint
```bash
npm run lint
```

---

## 🔍 DIAGNÓSTICO RÁPIDO

### Paso 1: Identificar el tipo de proyecto
```bash
# ¿Es Android?
ls -la app/build.gradle 2>/dev/null && echo "✅ Android Project"

# ¿Es Ktor/JVM?
grep -q "io.ktor" build.gradle.kts 2>/dev/null && echo "✅ Ktor Server Project"

# ¿Es Node.js?
ls -la package.json 2>/dev/null && echo "✅ Node.js Project"

# ¿Es Spring Boot?
grep -q "spring-boot" build.gradle* pom.xml 2>/dev/null && echo "✅ Spring Boot Project"
```

### Paso 2: Ver tareas disponibles

**Gradle:**
```bash
./gradlew tasks --all | grep compile
```

**Maven:**
```bash
mvn help:describe -Dcmd=compile
```

**NPM:**
```bash
npm run
```

---

## 🚨 Solución de Problemas Comunes

### Error: Task not found

**Síntoma:**
```
Task 'compileDebugKotlin' not found
```

**Causa:**
- Comando de Android en proyecto JVM/Backend

**Solución:**
```bash
# Para Backend (Ktor, Spring Boot)
./gradlew compileKotlin
# o
./gradlew build
```

---

### Error: Gradle Daemon

**Síntoma:**
```
Gradle Daemon could not be reused
```

**Solución:**
```bash
# Detener daemons
./gradlew --stop

# Reintentar
./gradlew build
```

---

### Error: Dependencies not found

**Solución:**
```bash
# Gradle
./gradlew --refresh-dependencies build

# Maven
mvn clean install -U

# NPM
npm install
```

---

## 📊 COMPARATIVA DE COMANDOS

| Acción | Android | Ktor/Backend | Spring Boot | React/Node |
|--------|---------|--------------|-------------|------------|
| **Compilar** | `compileDebugKotlin` | `compileKotlin` | `mvn compile` | `npm run build` |
| **Build Full** | `assembleDebug` | `build` | `mvn package` | `npm run build` |
| **Ejecutar** | Instalar APK | `run` | `mvn spring-boot:run` | `npm start` |
| **Limpiar** | `clean` | `clean` | `mvn clean` | `rm -rf dist/` |
| **Tests** | `testDebug` | `test` | `mvn test` | `npm test` |

---

## 🎯 PROYECTO ACTUAL: TaskManager (Ktor Backend)

### Comandos Recomendados:

```bash
# 1. Compilar código Kotlin
./gradlew compileKotlin

# 2. Build completo (compila + empaqueta)
./gradlew build

# 3. Ejecutar el servidor
./gradlew run

# 4. Build limpio
./gradlew clean build

# 5. Verificar sin ejecutar tests
./gradlew build -x test
```

---

## 📝 Notas Importantes

1. **Android vs Backend:**
   - Android usa `compileDebugKotlin` / `compileReleaseKotlin`
   - Backend (Ktor/Spring) usa `compileKotlin` o `compileJava`

2. **Wrapper de Gradle:**
   - Siempre usar `./gradlew` (no `gradle`)
   - Asegura versión consistente

3. **Modo Verbose:**
   - Agregar `--console=plain` para logs claros
   - Agregar `--stacktrace` para debug de errores

4. **Build Cache:**
   - `--no-build-cache` para build desde cero
   - `--refresh-dependencies` para actualizar deps

---

## 🔗 Referencias

- [Gradle Build Tool](https://docs.gradle.org/)
- [Ktor Documentation](https://ktor.io/docs/)
- [Android Developer Guide](https://developer.android.com/studio/build)
- [Spring Boot Maven Plugin](https://docs.spring.io/spring-boot/docs/current/maven-plugin/)

---

**Última actualización:** Proyecto TaskManager - Backend con Ktor Server

