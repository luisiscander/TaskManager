package com.taskmanager.domain.usecase

import com.taskmanager.domain.model.Task
import com.taskmanager.data.repository.TaskRepository

class GetTaskByIdUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(id: String): Task? {
        return repository.getTaskById(id)
    }
}

