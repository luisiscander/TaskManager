package com.taskmanager.domain.model

data class Task(
    val id: String,
    val title: String,
    val description: String,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

