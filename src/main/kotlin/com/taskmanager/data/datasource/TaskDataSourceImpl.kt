package com.taskmanager.data.datasource

import com.taskmanager.domain.model.Task
import java.util.concurrent.ConcurrentHashMap

class TaskDataSourceImpl : TaskDataSource {
    private val tasks = ConcurrentHashMap<String, Task>()

    override suspend fun getAllTasks(): List<Task> {
        return tasks.values.toList()
    }

    override suspend fun getTaskById(id: String): Task? {
        return tasks[id]
    }

    override suspend fun insertTask(task: Task): Task {
        tasks[task.id] = task
        return task
    }

    override suspend fun updateTask(task: Task): Task? {
        return if (tasks.containsKey(task.id)) {
            tasks[task.id] = task
            task
        } else {
            null
        }
    }

    override suspend fun deleteTask(id: String): Boolean {
        return tasks.remove(id) != null
    }
}
