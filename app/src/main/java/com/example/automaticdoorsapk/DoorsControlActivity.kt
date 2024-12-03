package com.example.automaticdoorsapk

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

class OpenCloseDoorsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OpenCloseDoorsScreen()
        }
    }
}

@Composable
fun OpenCloseDoorsScreen() {
    // Estado para controlar o status das portas
    val isInternalDoorOpen = remember { mutableStateOf(false) }
    val isExternalDoorOpen = remember { mutableStateOf(false) }

    // Função para abrir/fechar as portas
    fun toggleInternalDoor() {
        if (isInternalDoorOpen.value) {
            isInternalDoorOpen.value = false
        } else {
            // Fechar porta externa, se aberta
            if (isExternalDoorOpen.value) {
                isExternalDoorOpen.value = false
            }
            isInternalDoorOpen.value = true
        }
    }

    fun toggleExternalDoor() {
        if (isExternalDoorOpen.value) {
            isExternalDoorOpen.value = false
        } else {
            // Fechar porta interna, se aberta
            if (isInternalDoorOpen.value) {
                isInternalDoorOpen.value = false
            }
            isExternalDoorOpen.value = true
        }
    }

    // Layout da tela
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Controle de Portas",
            modifier = Modifier.padding(bottom = 32.dp),
            style = androidx.compose.material3.MaterialTheme.typography.headlineMedium
        )

        Button(
            onClick = { toggleInternalDoor() },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text(text = if (isInternalDoorOpen.value) "Fechar Porta Interna" else "Abrir Porta Interna")
        }

        Text(
            text = "Porta Interna: ${if (isInternalDoorOpen.value) "Aberta" else "Fechada"}",
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Button(
            onClick = { toggleExternalDoor() },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text(text = if (isExternalDoorOpen.value) "Fechar Porta Externa" else "Abrir Porta Externa")
        }

        Text(
            text = "Porta Externa: ${if (isExternalDoorOpen.value) "Aberta" else "Fechada"}"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    OpenCloseDoorsScreen()
}

fun openOpenCloseDoorsActivity(context: android.content.Context) {
    val intent = Intent(context, OpenCloseDoorsActivity::class.java)
    context.startActivity(intent)
}