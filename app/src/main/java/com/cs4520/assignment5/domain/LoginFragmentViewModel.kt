package com.cs4520.assignment5.domain

import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.MutableStateFlow

class LoginFragmentViewModel: ViewModel() {
    var usernameText: MutableState<String> = mutableStateOf("")

    var passwordText: MutableState<String> = mutableStateOf("")

    var errorMessageText: MutableState<String> = mutableStateOf("")

    /**
     * updates the username text
     */
    public fun setUsernameText(text: String) {
        usernameText.value = text
        clearError()
    }

    /**
     * updates the username text
     */
    public fun setPasswordText(text: String) {
        passwordText.value = text
        clearError()
    }

    /**
     * clear input text
     */
    private fun clearInput() {
        usernameText.value = ""
        passwordText.value = ""
    }

    /**
     * clear error text
     */
    private fun clearError() {
        errorMessageText.value = ""
    }

    /**
     * checks if the user has entered the right username and password
     * username: "admin"
     * password: "admin"
     */
    public fun login(): Boolean {
        if (usernameText.value == "admin"
            && passwordText.value == "admin") {
            clearInput()
            return true
        } else if (usernameText.value.isEmpty() || passwordText.value.isEmpty()) {
            errorMessageText.value = "Please enter your username AND password!"
        } else {
            errorMessageText.value = "Invalid credentials, Try again!"
            clearInput()
        }
        return false
    }
}
