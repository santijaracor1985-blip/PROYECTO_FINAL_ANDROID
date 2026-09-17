package com.sena.crud.domain.model

sealed interface OperationState<out T> {
    data object Idle : OperationState<Nothing>
    data object Loading : OperationState<Nothing>
    data class Success<T>(val data: T) : OperationState<T>
    data class Error(val message: String) : OperationState<Nothing>
}
