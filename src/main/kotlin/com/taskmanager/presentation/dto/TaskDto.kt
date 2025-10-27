package com.taskmanager.presentation.dto

import kotlinx.serialization.Serializable

@Serializable
data class TaskDto(
    val id: String,
    val title: String,
    val description: String,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

