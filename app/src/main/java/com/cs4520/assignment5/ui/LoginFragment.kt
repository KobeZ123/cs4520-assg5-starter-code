package com.cs4520.assignment5.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cs4520.assignment5.Screen
import com.cs4520.assignment5.domain.LoginFragmentViewModel

@Composable
fun LoginFragment(
    navHostController: NavHostController,
    loginFragmentViewModel: LoginFragmentViewModel = viewModel()
) {
    val context = LocalContext.current
    val usernameText by loginFragmentViewModel.usernameText
    val passwordText by loginFragmentViewModel.passwordText
    val errorMessageText by loginFragmentViewModel.errorMessageText

    if (errorMessageText.isNotEmpty()) {
        Toast.makeText(
            context,
            errorMessageText,
            Toast.LENGTH_LONG
        ).show()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextField(
                value = usernameText,
                onValueChange = { loginFragmentViewModel.setUsernameText(it) },
                label = { Text("Username") },
                singleLine = true,
            )

            TextField(
                value = passwordText,
                onValueChange = { loginFragmentViewModel.setPasswordText(it) },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Button(
                onClick = {
                    if (loginFragmentViewModel.login()) {
                        navHostController.navigate(Screen.PRODUCT_LIST.name)
                    }
                }
            ) {
                Text(text = "Login")
            }
        }
    }
}

@Preview
@Composable
fun LoginFragmentPreview() {
    MaterialTheme{
        LoginFragment(navHostController = rememberNavController())
    }
}