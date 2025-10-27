package com.taskmanager.data.repository

import com.taskmanager.data.datasource.TaskDataSource
import com.taskmanager.domain.model.Task
import com.taskmanager.data.repository.TaskRepository

class TaskRepositoryImpl(
    private val dataSource: TaskDataSource
) : TaskRepository {
    
    override suspend fun getAllTasks(): List<Task> {
        return dataSource.getAllTasks()
    }

    override suspend fun getTaskById(id: String): Task? {
        return dataSource.getTaskById(id)
    }

    override suspend fun createTask(task: Task): Task {
        return dataSource.insertTask(task)
    }

    override suspend fun updateTask(task: Task): Task? {
        return dataSource.updateTask(task)
    }

    override suspend fun deleteTask(id: String): Boolean {
        return dataSource.deleteTask(id)
    }
}

