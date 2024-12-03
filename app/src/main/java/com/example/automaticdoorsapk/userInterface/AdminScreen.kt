package com.example.automaticdoorsapk.userInterface

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

@Composable
fun AdminScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Admin Dashboard", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(bottom = 24.dp))

        Button(onClick = { /* Abrir/Fechar Portas */ }) {
            Text("Abrir/Fechar Portas")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { /* Terminar Cadastro de Nova Tag */ }) {
            Text("Cadastrar Nova Tag")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { /* Visualizar Log */ }) {
            Text("Visualizar Log")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { /* Permitir Acesso Remoto */ }) {
            Text("Permitir Acesso Remoto")
        }
    }
}
