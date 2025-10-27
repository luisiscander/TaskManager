package com.taskmanager.data.datasource

import com.taskmanager.domain.model.Task

interface TaskDataSource {
    suspend fun getAllTasks(): List<Task>
    suspend fun getTaskById(id: String): Task?
    suspend fun insertTask(task: Task): Task
    suspend fun updateTask(task: Task): Task?
    suspend fun deleteTask(id: String): Boolean
}

