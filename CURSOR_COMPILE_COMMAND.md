# 📝 Configuración del Comando /compile en Cursor

Este archivo contiene el contenido sugerido para el comando personalizado `/compile` en Cursor IDE.

---

## 🔧 Cómo Actualizar el Comando en Cursor

1. Abrir **Cursor Settings** (Cmd+,)
2. Ir a **Cursor Settings > Features > Composer**
3. Buscar el comando `compile`
4. Reemplazar el contenido con el siguiente:

---

## 📄 Contenido Sugerido para compile.md

```markdown
# Compilación del Proyecto

## Detección Automática del Tipo de Proyecto

Antes de compilar, identifica el tipo de proyecto:

### 🔍 SI ES ANDROID (Frontend Móvil):
- Buscar: `app/build.gradle` o Android plugin
- **Comando:** `./gradlew compileDebugKotlin`
- **Verificar:** Revisar que no haya errores de compilación

### 🔍 SI ES BACKEND (Ktor, Spring Boot):
- Buscar: `io.ktor` en build.gradle.kts o Spring dependencies
- **Comando:** `./gradlew compileKotlin` o `./gradlew build`
- **Verificar:** Revisar que no haya errores de compilación

### 🔍 SI ES FRONTEND WEB (React, Node.js):
- Buscar: `package.json`
- **Comando:** `npm run build`
- **Verificar:** Revisar que no haya errores de TypeScript/ESLint

---

## 📋 Pasos a Ejecutar:

1. **Identificar tipo de proyecto** usando los indicadores arriba
2. **Ejecutar el comando apropiado:**
   - Android: `./gradlew compileDebugKotlin --console=plain`
   - Backend JVM: `./gradlew compileKotlin --console=plain`
   - Frontend Web: `npm run build` o `npm run type-check`
3. **Verificar la salida:**
   - ✅ Si dice "BUILD SUCCESSFUL": Todo correcto
   - ❌ Si hay errores: Reportarlos y sugerir soluciones
4. **Ejecutar linter** si está disponible para verificar calidad del código

---

## 🎯 Para el Proyecto Actual:

Verificar el archivo `build.gradle.kts` o `package.json` para determinar el tipo.

**Proyecto Ktor/Backend:**
```bash
./gradlew compileKotlin --console=plain
./gradlew build --console=plain
```

**Proyecto Android:**
```bash
./gradlew compileDebugKotlin --console=plain
./gradlew assembleDebug --console=plain
```

**Proyecto Node.js:**
```bash
npm run build
npm run lint
```

---

## ⚠️ Importante:
- Siempre usar `./gradlew` (con wrapper) en vez de `gradle`
- Agregar `--console=plain` para output limpio
- Si el comando falla con "task not found", revisar el tipo de proyecto
```

---

## 🔄 Alternativa: Script de Auto-detección

Si prefieres un enfoque automático, puedes usar este script:

```bash
#!/bin/bash
# auto-compile.sh

if [ -f "app/build.gradle" ] || grep -q "com.android.application" build.gradle* 2>/dev/null; then
    echo "📱 Detectado: Proyecto Android"
    ./gradlew compileDebugKotlin --console=plain
elif grep -q "io.ktor" build.gradle.kts 2>/dev/null; then
    echo "🖥️ Detectado: Proyecto Ktor Backend"
    ./gradlew compileKotlin --console=plain
elif grep -q "spring-boot" build.gradle* pom.xml 2>/dev/null; then
    echo "🌱 Detectado: Proyecto Spring Boot"
    ./gradlew compileKotlin --console=plain
elif [ -f "package.json" ]; then
    echo "⚛️ Detectado: Proyecto Node.js"
    npm run build
else
    echo "❓ Tipo de proyecto no detectado"
    echo "Intentando build genérico..."
    ./gradlew build --console=plain 2>/dev/null || npm run build 2>/dev/null
fi
```

Guardar como `auto-compile.sh` y hacer ejecutable:
```bash
chmod +x auto-compile.sh
```

---

## 📚 Referencias

Ver [COMPILE_GUIDE.md](./COMPILE_GUIDE.md) para la guía completa con todos los comandos.
Ver [COMPILE_COMMANDS.md](./COMPILE_COMMANDS.md) para referencia rápida.

