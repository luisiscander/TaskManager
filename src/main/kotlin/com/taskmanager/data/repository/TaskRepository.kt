package com.taskmanager.data.repository

import com.taskmanager.domain.model.Task

interface TaskRepository {
    suspend fun getAllTasks(): List<Task>
    suspend fun getTaskById(id: String): Task?
    suspend fun createTask(task: Task): Task
    suspend fun updateTask(task: Task): Task?
    suspend fun deleteTask(id: String): Boolean
}
