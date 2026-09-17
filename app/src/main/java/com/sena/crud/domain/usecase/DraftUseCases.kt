package com.sena.crud.domain.usecase
import com.sena.crud.domain.model.TaskDraft
import com.sena.crud.domain.repository.DraftRepository
import javax.inject.Inject
class ObserveDraftsUseCase @Inject constructor(private val r:DraftRepository){operator fun invoke(id:String)=r.observeDrafts(id)}
class SaveDraftUseCase @Inject constructor(private val r:DraftRepository){suspend operator fun invoke(d:TaskDraft)=r.saveDraft(d)}
class UpdateDraftUseCase @Inject constructor(private val r:DraftRepository){suspend operator fun invoke(d:TaskDraft)=r.updateDraft(d)}
class DeleteDraftUseCase @Inject constructor(private val r:DraftRepository){suspend operator fun invoke(d:TaskDraft)=r.deleteDraft(d)}
