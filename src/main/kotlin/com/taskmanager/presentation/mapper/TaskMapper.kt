package com.taskmanager.presentation.mapper

import com.taskmanager.domain.model.Task
import com.taskmanager.presentation.dto.TaskRequest
import com.taskmanager.presentation.dto.TaskDto
import com.taskmanager.presentation.dto.UpdateTaskRequest
import java.util.UUID

fun Task.toDto(): TaskDto {
    return TaskDto(
        id = id,
        title = title,
        description = description,
        isCompleted = isCompleted,
        createdAt = createdAt
    )
}

fun TaskDto.toDomain(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        isCompleted = isCompleted,
        createdAt = createdAt
    )
}

fun TaskRequest.toDomain(): Task {
    return Task(
        id = UUID.randomUUID().toString(),
        title = title,
        description = description,
        isCompleted = isCompleted
    )
}

fun UpdateTaskRequest.toDomain(id: String, createdAt: Long): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        isCompleted = isCompleted,
        createdAt = createdAt
    )
}

