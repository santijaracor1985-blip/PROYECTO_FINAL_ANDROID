package com.sena.crud.domain.usecase
import com.sena.crud.domain.model.Task
import com.sena.crud.domain.repository.TaskRepository
import javax.inject.Inject
class ObserveTasksUseCase @Inject constructor(private val r:TaskRepository){operator fun invoke(id:String)=r.observeTasks(id)}
class CreateTaskUseCase @Inject constructor(private val r:TaskRepository){suspend operator fun invoke(t:Task)=r.createTask(t)}
class UpdateTaskUseCase @Inject constructor(private val r:TaskRepository){suspend operator fun invoke(t:Task)=r.updateTask(t)}
class DeleteTaskUseCase @Inject constructor(private val r:TaskRepository){suspend operator fun invoke(t:Task)=r.deleteTask(t)}
