package com.example.automaticdoorsapk.userInterface

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.automaticdoorsapk.AdminActivity
import com.example.automaticdoorsapk.UserActivity
import com.example.automaticdoorsapk.ServerActivity

@Composable
fun LoginScreen() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Login", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(bottom = 24.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Usuário") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Senha") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(onDone = { /* Ação ao apertar Done */ }),
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Mostrar senha"
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val userType = validateUser(username, password)
                when (userType) {
                    "admin" -> navigateToAdminActivity()
                    "user" -> navigateToUserActivity()
                    "server" -> navigateToServerActivity()
                    else -> {
                        // Mensagem de erro
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Entrar")
        }
    }
}

fun validateUser(username: String, password: String): String {
    // Simula a validação do tipo de usuário com base nas credenciais
    return when (username) {
        "admin" -> "admin"
        "user" -> "user"
        "server" -> "server"
        else -> ""
    }
}

fun navigateToAdminActivity() {
    // Navega para a Activity do Admin
    val context = LocalContext.current
    val intent = Intent(context, AdminActivity::class.java)
    context.startActivity(intent)
}

fun navigateToUserActivity() {
    // Navega para a Activity do Usuário
    val context = LocalContext.current
    val intent = Intent(context, UserActivity::class.java)
    context.startActivity(intent)
}

fun navigateToServerActivity() {
    // Navega para a Activity do Servidor
    val context = LocalContext.current
    val intent = Intent(context, ServerActivity::class.java)
    context.startActivity(intent)
}
