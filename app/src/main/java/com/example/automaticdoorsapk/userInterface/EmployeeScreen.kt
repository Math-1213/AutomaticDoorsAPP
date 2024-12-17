package com.example.automaticdoorsapk.userInterface

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.automaticdoorsapk.userInterface.function.openOpenCloseDoorsActivity

@Composable
fun EmployeeScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Server Dashboard", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(bottom = 24.dp))

        Button(onClick = { openOpenCloseDoorsActivity(context) }) {
            Text("Abrir/Fechar Portas")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { /* Visualizar Log */ }) {
            Text("Visualizar Log")
        }
    }
}

