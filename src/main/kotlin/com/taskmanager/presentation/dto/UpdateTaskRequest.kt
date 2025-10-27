package com.taskmanager.presentation.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdateTaskRequest(
    val title: String,
    val description: String,
    val isCompleted: Boolean
)

