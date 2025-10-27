package com.taskmanager.domain.usecase

import com.taskmanager.domain.model.Task
import com.taskmanager.data.repository.TaskRepository

class UpdateTaskUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(task: Task): Task? {
        return repository.updateTask(task)
    }
}

