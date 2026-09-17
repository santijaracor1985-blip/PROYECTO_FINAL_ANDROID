package com.sena.crud.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sena.crud.domain.model.TaskDraft
import com.sena.crud.domain.usecase.DeleteDraftUseCase
import com.sena.crud.domain.usecase.ObserveDraftsUseCase
import com.sena.crud.domain.usecase.SaveDraftUseCase
import com.sena.crud.domain.usecase.UpdateDraftUseCase
import com.sena.crud.ui.state.DraftListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DraftViewModel @Inject constructor(
 private val observeDrafts: ObserveDraftsUseCase,
 private val saveDraft: SaveDraftUseCase,
 private val updateDraft: UpdateDraftUseCase,
 private val deleteDraft: DeleteDraftUseCase
) : ViewModel() {

 private val _state = MutableStateFlow(DraftListUiState())
 val state: StateFlow<DraftListUiState> = _state.asStateFlow()

 private var job: Job? = null

 fun start(ownerId: String) {
  job?.cancel()

  job = viewModelScope.launch {
   observeDrafts(ownerId).collect { drafts ->
    _state.value = _state.value.copy(
     drafts = drafts
    )
   }
  }
 }

 fun save(
  ownerId: String,
  id: Int,
  title: String,
  description: String,
  onDone: () -> Unit
 ) {
  if (title.isBlank()) {
   _state.value = _state.value.copy(
    errorMessage = "El título es obligatorio."
   )
   return
  }

  viewModelScope.launch {
   _state.value = _state.value.copy(
    isLoading = true,
    errorMessage = null
   )

   val draft = TaskDraft(
    id = id,
    ownerId = ownerId,
    title = title.trim(),
    description = description.trim(),
    savedAt = System.currentTimeMillis()
   )

   val result = if (id == 0) {
    saveDraft(draft)
   } else {
    updateDraft(draft)
   }

   result
    .onSuccess {
     _state.value = _state.value.copy(
      isLoading = false,
      errorMessage = null
     )
     onDone()
    }
    .onFailure { error ->
     _state.value = _state.value.copy(
      isLoading = false,
      errorMessage = error.message ?: "No se pudo guardar el borrador."
     )
    }
  }
 }

 fun delete(
  draft: TaskDraft,
  onDone: () -> Unit
 ) {
  viewModelScope.launch {
   _state.value = _state.value.copy(
    isLoading = true,
    errorMessage = null
   )

   deleteDraft(draft)
    .onSuccess {
     _state.value = _state.value.copy(
      isLoading = false,
      errorMessage = null
     )
     onDone()
    }
    .onFailure { error ->
     _state.value = _state.value.copy(
      isLoading = false,
      errorMessage = error.message ?: "No se pudo eliminar el borrador."
     )
    }
  }
 }

 fun clearError() {
  _state.value = _state.value.copy(
   errorMessage = null
  )
 }

 override fun onCleared() {
  job?.cancel()
  super.onCleared()
 }
}