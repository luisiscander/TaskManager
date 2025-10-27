package com.taskmanager.domain.usecase

import com.taskmanager.data.repository.TaskRepository

class DeleteTaskUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(id: String): Boolean {
        return repository.deleteTask(id)
    }
}

