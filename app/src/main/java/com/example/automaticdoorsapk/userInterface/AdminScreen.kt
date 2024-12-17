package com.example.automaticdoorsapk.userInterface

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.automaticdoorsapk.userInterface.function.navigateToRegisterTag
import com.example.automaticdoorsapk.userInterface.function.openOpenCloseDoorsActivity

@Composable
fun AdminScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Admin Dashboard",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Remove "Abrir/Fechar Portas" button, and change "Permitir Acesso Remoto" button action
        Button(onClick = { openOpenCloseDoorsActivity(context) }) {
            Text("Permitir Acesso Remoto")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button for "Cadastrar Nova Tag"
        Button(onClick = { navigateToRegisterTag(context) }) {
            Text("Cadastrar Nova Tag")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button for "Visualizar Log"
        Button(onClick = { /* Visualizar Log */ }) {
            Text("Visualizar Log")
        }
    }
}



