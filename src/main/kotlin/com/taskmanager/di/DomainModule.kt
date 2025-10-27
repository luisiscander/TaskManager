package com.taskmanager.di

import com.taskmanager.domain.usecase.*
import org.koin.dsl.module

val domainModule = module {
    
    // Use Cases - Factory (nueva instancia cada vez que se solicita)
    factory {
        GetAllTasksUseCase(get())
    }
    
    factory {
        GetTaskByIdUseCase(get())
    }
    
    factory {
        CreateTaskUseCase(get())
    }
    
    factory {
        UpdateTaskUseCase(get())
    }
    
    factory {
        DeleteTaskUseCase(get())
    }
}

