# Ejemplos de API REST - Task Manager

## 🚀 Iniciar el servidor

```bash
./gradlew run
```

El servidor se iniciará en: `http://localhost:8080`

---

## 📋 Endpoints y Ejemplos

### 1️⃣ Health Check

```bash
curl http://localhost:8080/
```

**Respuesta:**
```json
{
  "status": "running",
  "service": "Task Manager API",
  "version": "1.0.0"
}
```

---

### 2️⃣ Crear una Tarea (POST)

```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Aprender Ktor",
    "description": "Completar tutorial de Ktor Server",
    "isCompleted": false
  }'
```

**Respuesta (201 Created):**
```json
{
  "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "title": "Aprender Ktor",
  "description": "Completar tutorial de Ktor Server",
  "isCompleted": false,
  "createdAt": 1698765432000
}
```

---

### 3️⃣ Obtener Todas las Tareas (GET)

```bash
curl http://localhost:8080/api/tasks
```

**Respuesta (200 OK):**
```json
[
  {
    "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
    "title": "Aprender Ktor",
    "description": "Completar tutorial de Ktor Server",
    "isCompleted": false,
    "createdAt": 1698765432000
  },
  {
    "id": "b2c3d4e5-f6a7-8901-bcde-f12345678901",
    "title": "Implementar Clean Architecture",
    "description": "Separar capas: Data, Domain, Presentation",
    "isCompleted": true,
    "createdAt": 1698765433000
  }
]
```

---

### 4️⃣ Obtener una Tarea por ID (GET)

```bash
curl http://localhost:8080/api/tasks/a1b2c3d4-e5f6-7890-abcd-ef1234567890
```

**Respuesta (200 OK):**
```json
{
  "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "title": "Aprender Ktor",
  "description": "Completar tutorial de Ktor Server",
  "isCompleted": false,
  "createdAt": 1698765432000
}
```

**Respuesta Error (404 Not Found):**
```json
{
  "error": "Tarea no encontrada"
}
```

---

### 5️⃣ Actualizar una Tarea (PUT)

```bash
curl -X PUT http://localhost:8080/api/tasks/a1b2c3d4-e5f6-7890-abcd-ef1234567890 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Aprender Ktor - COMPLETADO",
    "description": "Tutorial de Ktor Server completado con éxito",
    "isCompleted": true
  }'
```

**Respuesta (200 OK):**
```json
{
  "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "title": "Aprender Ktor - COMPLETADO",
  "description": "Tutorial de Ktor Server completado con éxito",
  "isCompleted": true,
  "createdAt": 1698765432000
}
```

**Respuesta Error (404 Not Found):**
```json
{
  "error": "Tarea no encontrada"
}
```

---

### 6️⃣ Eliminar una Tarea (DELETE)

```bash
curl -X DELETE http://localhost:8080/api/tasks/a1b2c3d4-e5f6-7890-abcd-ef1234567890
```

**Respuesta (200 OK):**
```json
{
  "message": "Tarea eliminada exitosamente"
}
```

**Respuesta Error (404 Not Found):**
```json
{
  "error": "Tarea no encontrada"
}
```

---

## 🧪 Prueba Completa del CRUD

### Script de prueba completo:

```bash
# 1. Verificar que el servidor está corriendo
curl http://localhost:8080/

# 2. Crear primera tarea
TASK1=$(curl -s -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Tarea 1",
    "description": "Primera tarea de prueba",
    "isCompleted": false
  }')

echo "Tarea creada: $TASK1"

# 3. Extraer el ID de la tarea (requiere jq)
# TASK1_ID=$(echo $TASK1 | jq -r '.id')

# 4. Crear segunda tarea
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Tarea 2",
    "description": "Segunda tarea de prueba",
    "isCompleted": false
  }'

# 5. Listar todas las tareas
echo -e "\n\n=== Todas las tareas ==="
curl http://localhost:8080/api/tasks

# 6. Actualizar tarea (reemplaza el ID con el real)
# curl -X PUT http://localhost:8080/api/tasks/$TASK1_ID \
#   -H "Content-Type: application/json" \
#   -d '{
#     "title": "Tarea 1 - Actualizada",
#     "description": "Tarea actualizada",
#     "isCompleted": true
#   }'

# 7. Eliminar tarea (reemplaza el ID con el real)
# curl -X DELETE http://localhost:8080/api/tasks/$TASK1_ID
```

---

## 🐛 Códigos de Respuesta HTTP

| Código | Descripción |
|--------|-------------|
| 200 OK | Operación exitosa |
| 201 Created | Recurso creado exitosamente |
| 400 Bad Request | Solicitud mal formada o datos inválidos |
| 404 Not Found | Recurso no encontrado |
| 500 Internal Server Error | Error interno del servidor |

---

## 🔧 Herramientas Recomendadas

- **cURL**: Cliente HTTP de línea de comandos (incluido en este documento)
- **Postman**: Cliente HTTP con interfaz gráfica
- **HTTPie**: Cliente HTTP amigable para terminal (`http` comando)
- **Thunder Client**: Extensión de VS Code
- **REST Client**: Extensión de VS Code

---

## 📱 Ejemplo con HTTPie

Si prefieres usar HTTPie (más legible):

```bash
# GET
http GET :8080/api/tasks

# POST
http POST :8080/api/tasks title="Nueva tarea" description="Descripción" isCompleted:=false

# PUT
http PUT :8080/api/tasks/ID title="Actualizada" description="Nueva descripción" isCompleted:=true

# DELETE
http DELETE :8080/api/tasks/ID
```

---

## 🎯 Validaciones Implementadas

- ✅ El campo `title` no puede estar vacío
- ✅ Validación de existencia de tarea antes de actualizar/eliminar
- ✅ Manejo de errores con mensajes descriptivos
- ✅ Validación de formato JSON en requests

