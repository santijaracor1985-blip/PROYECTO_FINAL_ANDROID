package com.sena.crud.ui.state

import com.sena.crud.domain.model.Task
import com.sena.crud.domain.model.TaskDraft

data class AuthUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isConfigured: Boolean = true
)

data class TaskListUiState(
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class DraftListUiState(
    val drafts: List<TaskDraft> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

enum class AppScreen {
    Start,
    Login,
    Register,
    Tasks,
    Drafts
}
