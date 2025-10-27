package com.taskmanager.di

import org.koin.dsl.module

/**
 * Lista de todos los módulos de Koin de la aplicación
 */
val appModules = listOf(
    dataModule,
    domainModule
)

