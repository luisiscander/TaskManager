package com.taskmanager.presentation.dto

import kotlinx.serialization.Serializable

@Serializable
data class TaskRequest(
    val title: String,
    val description: String,
    val isCompleted: Boolean = false
)
