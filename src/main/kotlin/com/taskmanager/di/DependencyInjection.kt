package com.taskmanager.di

import com.taskmanager.data.datasource.TaskDataSourceImpl
import com.taskmanager.data.datasource.TaskDataSource
import com.taskmanager.data.repository.TaskRepositoryImpl
import com.taskmanager.data.repository.TaskRepository
import com.taskmanager.domain.usecase.*

object DependencyInjection {
    
    // Data Source
    private val taskDataSource: TaskDataSource by lazy {
        TaskDataSourceImpl()
    }
    
    // Repository
    private val taskRepository: TaskRepository by lazy {
        TaskRepositoryImpl(taskDataSource)
    }
    
    // Use Cases
    val getAllTasksUseCase: GetAllTasksUseCase by lazy {
        GetAllTasksUseCase(taskRepository)
    }
    
    val getTaskByIdUseCase: GetTaskByIdUseCase by lazy {
        GetTaskByIdUseCase(taskRepository)
    }
    
    val createTaskUseCase: CreateTaskUseCase by lazy {
        CreateTaskUseCase(taskRepository)
    }
    
    val updateTaskUseCase: UpdateTaskUseCase by lazy {
        UpdateTaskUseCase(taskRepository)
    }
    
    val deleteTaskUseCase: DeleteTaskUseCase by lazy {
        DeleteTaskUseCase(taskRepository)
    }
}

