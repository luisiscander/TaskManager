package com.taskmanager.domain.usecase

import com.taskmanager.domain.model.Task
import com.taskmanager.data.repository.TaskRepository

class GetAllTasksUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(): List<Task> {
        return repository.getAllTasks()
    }
}

