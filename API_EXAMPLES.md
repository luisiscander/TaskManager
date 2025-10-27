# REST API Examples - Task Manager

## 🚀 Start the Server

```bash
./gradlew run
```

The server will start at: `http://localhost:8080`

---

## 📋 Endpoints and Examples

### 1️⃣ Health Check

```bash
curl http://localhost:8080/
```

**Response:**
```json
{
  "status": "running",
  "service": "Task Manager API",
  "version": "1.0.0"
}
```

---

### 2️⃣ Create a Task (POST)

```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Learn Ktor",
    "description": "Complete Ktor Server tutorial",
    "isCompleted": false
  }'
```

**Response (201 Created):**
```json
{
  "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "title": "Learn Ktor",
  "description": "Complete Ktor Server tutorial",
  "isCompleted": false,
  "createdAt": 1698765432000
}
```

---

### 3️⃣ Get All Tasks (GET)

```bash
curl http://localhost:8080/api/tasks
```

**Response (200 OK):**
```json
[
  {
    "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
    "title": "Learn Ktor",
    "description": "Complete Ktor Server tutorial",
    "isCompleted": false,
    "createdAt": 1698765432000
  },
  {
    "id": "b2c3d4e5-f6a7-8901-bcde-f12345678901",
    "title": "Implement Clean Architecture",
    "description": "Separate layers: Data, Domain, Presentation",
    "isCompleted": true,
    "createdAt": 1698765433000
  }
]
```

---

### 4️⃣ Get Task by ID (GET)

```bash
curl http://localhost:8080/api/tasks/a1b2c3d4-e5f6-7890-abcd-ef1234567890
```

**Response (200 OK):**
```json
{
  "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "title": "Learn Ktor",
  "description": "Complete Ktor Server tutorial",
  "isCompleted": false,
  "createdAt": 1698765432000
}
```

**Error Response (404 Not Found):**
```json
{
  "error": "Task not found"
}
```

---

### 5️⃣ Update a Task (PUT)

```bash
curl -X PUT http://localhost:8080/api/tasks/a1b2c3d4-e5f6-7890-abcd-ef1234567890 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Learn Ktor - COMPLETED",
    "description": "Ktor Server tutorial successfully completed",
    "isCompleted": true
  }'
```

**Response (200 OK):**
```json
{
  "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "title": "Learn Ktor - COMPLETED",
  "description": "Ktor Server tutorial successfully completed",
  "isCompleted": true,
  "createdAt": 1698765432000
}
```

**Error Response (404 Not Found):**
```json
{
  "error": "Task not found"
}
```

---

### 6️⃣ Delete a Task (DELETE)

```bash
curl -X DELETE http://localhost:8080/api/tasks/a1b2c3d4-e5f6-7890-abcd-ef1234567890
```

**Response (200 OK):**
```json
{
  "message": "Task deleted successfully"
}
```

**Error Response (404 Not Found):**
```json
{
  "error": "Task not found"
}
```

---

## 🧪 Complete CRUD Test

### Full test script:

```bash
# 1. Check server is running
curl http://localhost:8080/

# 2. Create first task
TASK1=$(curl -s -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Task 1",
    "description": "First test task",
    "isCompleted": false
  }')

echo "Task created: $TASK1"

# 3. Extract task ID (requires jq)
# TASK1_ID=$(echo $TASK1 | jq -r '.id')

# 4. Create second task
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Task 2",
    "description": "Second test task",
    "isCompleted": false
  }'

# 5. List all tasks
echo -e "\n\n=== All tasks ==="
curl http://localhost:8080/api/tasks

# 6. Update task (replace ID with real one)
# curl -X PUT http://localhost:8080/api/tasks/$TASK1_ID \
#   -H "Content-Type: application/json" \
#   -d '{
#     "title": "Task 1 - Updated",
#     "description": "Updated task",
#     "isCompleted": true
#   }'

# 7. Delete task (replace ID with real one)
# curl -X DELETE http://localhost:8080/api/tasks/$TASK1_ID
```

---

## 🐛 HTTP Response Codes

| Code | Description |
|------|-------------|
| 200 OK | Successful operation |
| 201 Created | Resource created successfully |
| 400 Bad Request | Malformed request or invalid data |
| 404 Not Found | Resource not found |
| 500 Internal Server Error | Internal server error |

---

## 🔧 Recommended Tools

- **cURL**: Command-line HTTP client (included in this document)
- **Postman**: HTTP client with GUI
- **HTTPie**: User-friendly HTTP client for terminal (`http` command)
- **Thunder Client**: VS Code extension
- **REST Client**: VS Code extension

---

## 📱 Example with HTTPie

If you prefer HTTPie (more readable):

```bash
# GET
http GET :8080/api/tasks

# POST
http POST :8080/api/tasks title="New task" description="Description" isCompleted:=false

# PUT
http PUT :8080/api/tasks/ID title="Updated" description="New description" isCompleted:=true

# DELETE
http DELETE :8080/api/tasks/ID
```

---

## 🎯 Implemented Validations

- ✅ `title` field cannot be empty
- ✅ Task existence validation before update/delete
- ✅ Error handling with descriptive messages
- ✅ JSON format validation in requests
