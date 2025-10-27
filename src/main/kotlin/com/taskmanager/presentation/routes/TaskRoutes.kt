package com.taskmanager.presentation.routes

import com.taskmanager.domain.usecase.*
import com.taskmanager.presentation.dto.TaskRequest
import com.taskmanager.presentation.dto.UpdateTaskRequest
import com.taskmanager.presentation.mapper.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.taskRoutes() {
    // Inyección de dependencias con Koin
    val getAllTasksUseCase by inject<GetAllTasksUseCase>()
    val getTaskByIdUseCase by inject<GetTaskByIdUseCase>()
    val createTaskUseCase by inject<CreateTaskUseCase>()
    val updateTaskUseCase by inject<UpdateTaskUseCase>()
    val deleteTaskUseCase by inject<DeleteTaskUseCase>()
    
    route("/api/tasks") {
        
        // GET /api/tasks - Obtener todas las tareas
        get {
            val tasks = getAllTasksUseCase()
            call.respond(HttpStatusCode.OK, tasks.map { it.toDto() })
        }
        
        // GET /api/tasks/{id}
        get("/{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                mapOf("error" to "ID es requerido")
            )
            
            val task = getTaskByIdUseCase(id)
            if (task != null) {
                call.respond(HttpStatusCode.OK, task.toDto())
            } else {
                call.respond(
                    HttpStatusCode.NotFound,
                    mapOf("error" to "Tarea no encontrada")
                )
            }
        }
        

        post {
            try {
                val request = call.receive<TaskRequest>()
                
                if (request.title.isBlank()) {
                    return@post call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to "El título no puede estar vacío")
                    )
                }
                
                val task = createTaskUseCase(request.toDomain())
                call.respond(HttpStatusCode.Created, task.toDto())
                
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to "Solicitud inválida")
                )
            }
        }
        
        // PUT /api/tasks/{id} - Actualizar tarea existente
        put("/{id}") {
            val id = call.parameters["id"] ?: return@put call.respond(
                HttpStatusCode.BadRequest,
                mapOf("error" to "ID es requerido")
            )
            
            try {
                val request = call.receive<UpdateTaskRequest>()
                
                if (request.title.isBlank()) {
                    return@put call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to "El título no puede estar vacío")
                    )
                }
                
                // Verificar que la tarea existe
                val existingTask = getTaskByIdUseCase(id)
                if (existingTask == null) {
                    return@put call.respond(
                        HttpStatusCode.NotFound,
                        mapOf("error" to "Tarea no encontrada")
                    )
                }
                
                val updatedTask = updateTaskUseCase(
                    request.toDomain(id, existingTask.createdAt)
                )
                
                if (updatedTask != null) {
                    call.respond(HttpStatusCode.OK, updatedTask.toDto())
                } else {
                    call.respond(
                        HttpStatusCode.NotFound,
                        mapOf("error" to "Tarea no encontrada")
                    )
                }
                
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to "Solicitud inválida")
                )
            }
        }
        
        // DELETE /api/tasks/{id} - Eliminar tarea
        delete("/{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(
                HttpStatusCode.BadRequest,
                mapOf("error" to "ID es requerido")
            )
            
            val deleted = deleteTaskUseCase(id)
            if (deleted) {
                call.respond(
                    HttpStatusCode.OK,
                    mapOf("message" to "Tarea eliminada exitosamente")
                )
            } else {
                call.respond(
                    HttpStatusCode.NotFound,
                    mapOf("error" to "Tarea no encontrada")
                )
            }
        }
    }
}

