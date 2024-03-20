package com.cs4520.assignment5.ui

import android.widget.EditText
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun LoginFragment() {
    var usernameText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }

    Column {
        TextField(
            value = usernameText,
            onValueChange = {usernameText = it},
            label = { Text("Username")}
        )

        TextField(
            value = passwordText,
            onValueChange = { passwordText = it },
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Login")
        }
    }
}