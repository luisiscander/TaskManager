#!/bin/bash
# auto-compile.sh
# Script de compilación automática que detecta el tipo de proyecto

set -e  # Salir si hay error

echo "🔍 Detectando tipo de proyecto..."
echo ""

# Función para compilar Android
compile_android() {
    echo "📱 Proyecto Android detectado"
    echo "Ejecutando: ./gradlew compileDebugKotlin"
    echo ""
    ./gradlew compileDebugKotlin --console=plain
    echo ""
    echo "✅ Compilación Android completada"
}

# Función para compilar Backend JVM
compile_backend_jvm() {
    echo "🖥️ Proyecto Backend JVM detectado"
    echo "Ejecutando: ./gradlew compileKotlin"
    echo ""
    ./gradlew compileKotlin --console=plain
    echo ""
    echo "✅ Compilación Backend completada"
}

# Función para compilar Node.js
compile_nodejs() {
    echo "⚛️ Proyecto Node.js detectado"
    echo "Ejecutando: npm run build"
    echo ""
    npm run build
    echo ""
    echo "✅ Build Node.js completado"
}

# Función para build completo
build_full() {
    if [ -f "gradlew" ]; then
        echo "📦 Ejecutando build completo..."
        ./gradlew build --console=plain
    elif [ -f "package.json" ]; then
        npm run build
    fi
}

# Detección del tipo de proyecto
if [ -f "app/build.gradle" ] || grep -q "com.android.application" build.gradle* 2>/dev/null; then
    compile_android
elif grep -q "io.ktor" build.gradle.kts 2>/dev/null; then
    compile_backend_jvm
    echo ""
    echo "💡 Para ejecutar el servidor: ./gradlew run"
elif grep -q "spring-boot" build.gradle* pom.xml 2>/dev/null; then
    compile_backend_jvm
    echo ""
    echo "💡 Para ejecutar el servidor: ./gradlew bootRun"
elif [ -f "package.json" ]; then
    compile_nodejs
elif [ -f "gradlew" ]; then
    echo "🔧 Proyecto Gradle genérico detectado"
    compile_backend_jvm
else
    echo "❌ No se pudo detectar el tipo de proyecto"
    echo ""
    echo "Tipos soportados:"
    echo "  📱 Android (app/build.gradle)"
    echo "  🖥️ Ktor Backend (build.gradle.kts con io.ktor)"
    echo "  🌱 Spring Boot (build.gradle con spring-boot)"
    echo "  ⚛️ Node.js (package.json)"
    exit 1
fi

echo ""
echo "🎉 Proceso de compilación finalizado exitosamente"

