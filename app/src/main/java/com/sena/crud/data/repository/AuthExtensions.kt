package com.sena.crud.data.repository

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException

fun Throwable.authMessage(): String {
    return when (this) {
        is FirebaseAuthInvalidUserException -> "El usuario no existe."
        is FirebaseAuthInvalidCredentialsException -> "Credenciales incorrectas."
        is FirebaseAuthUserCollisionException -> "El correo ya está registrado."
        is FirebaseAuthException -> "Error de autenticación: ${this.errorCode}"
        is FirebaseException -> "Error de Firebase: ${this.message}"
        else -> this.message ?: "Ocurrió un error inesperado."
    }
}
