package com.sena.crud.ui.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sena.crud.domain.model.Task
import com.sena.crud.domain.usecase.*
import com.sena.crud.ui.state.TaskListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel class TaskViewModel @Inject constructor(private val obs:ObserveTasksUseCase,private val create:CreateTaskUseCase,private val update:UpdateTaskUseCase,private val delete:DeleteTaskUseCase):ViewModel(){
 private val _state = MutableStateFlow(TaskListUiState())
 val state: StateFlow<TaskListUiState> = _state.asStateFlow()
 private var job: Job? = null
 private var ownerId: String? = null

 fun start(uid: String) {
  if (ownerId == uid && job != null) return
  job?.cancel()
  ownerId = uid
  job = viewModelScope.launch {
   _state.update { it.copy(isLoading = true) }
   obs(uid)
    .catch { x ->
     _state.update { it.copy(isLoading = false, errorMessage = x.message ?: "No se pudieron cargar las tareas.") }
    }
    .collect { v ->
     _state.update { it.copy(isLoading = false, tasks = v, errorMessage = null) }
    }
  }
 }

 fun create(uid: String, t: String, d: String, ok: () -> Unit) = viewModelScope.launch {
  val n = System.currentTimeMillis()
  create(Task(ownerId = uid, title = t.trim(), description = d.trim(), createdAt = n, updatedAt = n))
   .onSuccess { ok() }
   .onFailure { x ->
    _state.update { it.copy(errorMessage = x.message ?: "No se pudo crear la tarea.") }
   }
 }

 fun update(t: Task, title: String, d: String, c: Boolean, ok: () -> Unit) = viewModelScope.launch {
  update(t.copy(title = title.trim(), description = d.trim(), completed = c, updatedAt = System.currentTimeMillis()))
   .onSuccess { ok() }
   .onFailure { x ->
    _state.update { it.copy(errorMessage = x.message ?: "No se pudo actualizar la tarea.") }
   }
 }

 fun delete(t: Task, ok: () -> Unit) = viewModelScope.launch {
  delete(t)
   .onSuccess { ok() }
   .onFailure { x ->
    _state.update { it.copy(errorMessage = x.message ?: "No se pudo eliminar la tarea.") }
   }
 }
}
