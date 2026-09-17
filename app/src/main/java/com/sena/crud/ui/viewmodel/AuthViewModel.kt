package com.sena.crud.ui.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sena.crud.data.repository.authMessage
import com.sena.crud.domain.usecase.*
import com.sena.crud.ui.state.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel class AuthViewModel @Inject constructor(private val reg:RegisterUserUseCase,private val log:LoginUserUseCase,private val out:LogoutUserUseCase,private val cur:GetCurrentUserUseCase):ViewModel(){
 private val _state=MutableStateFlow(AuthUiState(isConfigured=cur.isConfigured()));val state:StateFlow<AuthUiState> = _state.asStateFlow()
 fun login(e:String,p:String,ok:()->Unit){if(_state.value.isLoading)return;viewModelScope.launch{_state.update{it.copy(isLoading=true,errorMessage=null)};log(e.trim(),p).onSuccess{_state.update{it.copy(isLoading=false)};ok()}.onFailure{x->_state.update{it.copy(isLoading=false,errorMessage=x.authMessage())}}}}
 fun register(e:String,p:String,ok:()->Unit){if(_state.value.isLoading)return;viewModelScope.launch{_state.update{it.copy(isLoading=true,errorMessage=null)};reg(e.trim(),p).onSuccess{_state.update{it.copy(isLoading=false)};ok()}.onFailure{x->_state.update{it.copy(isLoading=false,errorMessage=x.authMessage())}}}}
 fun logout()=out();fun userId()=cur();fun email()=cur.email()
}
