package com.sena.crud.domain.usecase
import com.sena.crud.domain.repository.AuthRepository
import javax.inject.Inject
class RegisterUserUseCase @Inject constructor(private val r:AuthRepository){suspend operator fun invoke(e:String,p:String)=r.register(e,p)}
class LoginUserUseCase @Inject constructor(private val r:AuthRepository){suspend operator fun invoke(e:String,p:String)=r.login(e,p)}
class LogoutUserUseCase @Inject constructor(private val r:AuthRepository){operator fun invoke()=r.logout()}
class GetCurrentUserUseCase @Inject constructor(private val r:AuthRepository){operator fun invoke()=r.currentUserId();fun email()=r.currentUserEmail();fun isConfigured()=r.isConfigured()}
