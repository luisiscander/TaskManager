package com.taskmanager.di

import com.taskmanager.data.datasource.TaskDataSource
import com.taskmanager.data.datasource.TaskDataSourceImpl
import com.taskmanager.data.repository.TaskRepository
import com.taskmanager.data.repository.TaskRepositoryImpl
import org.koin.dsl.module

val dataModule = module {
    
    // Data Source - Singleton
    single<TaskDataSource> {
        TaskDataSourceImpl()
    }
    
    // Repository - Singleton
    single<TaskRepository> {
        TaskRepositoryImpl(get())
    }
}

