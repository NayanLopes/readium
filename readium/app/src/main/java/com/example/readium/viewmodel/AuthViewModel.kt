package com.example.readium.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readium.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    
    private val repository = FirebaseRepository()
    
    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()
    
    private val _user = MutableStateFlow<FirebaseUser?>(null)
    val user: StateFlow<FirebaseUser?> = _user.asStateFlow()
    
    init {
        checkAuthState()
    }
    
    private fun checkAuthState() {
        val currentUser = repository.getCurrentUser()
        _user.value = currentUser
        _authState.value = if (currentUser != null) {
            AuthState.Authenticated
        } else {
            AuthState.Unauthenticated
        }
    }
    
    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = repository.signInWithEmail(email, password)
            
            result.fold(
                onSuccess = { user ->
                    _user.value = user
                    _authState.value = AuthState.Authenticated
                },
                onFailure = { exception ->
                    _authState.value = AuthState.Error(exception.message ?: "Erro desconhecido")
                }
            )
        }
    }
    
    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = repository.createUserWithEmail(email, password)
            
            result.fold(
                onSuccess = { user ->
                    _user.value = user
                    _authState.value = AuthState.Authenticated
                },
                onFailure = { exception ->
                    _authState.value = AuthState.Error(exception.message ?: "Erro desconhecido")
                }
            )
        }
    }
    
    fun signOut() {
        repository.signOut()
        _user.value = null
        _authState.value = AuthState.Unauthenticated
    }
}

sealed class AuthState {
    object Loading : AuthState()
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    data class Error(val message: String) : AuthState()
}
