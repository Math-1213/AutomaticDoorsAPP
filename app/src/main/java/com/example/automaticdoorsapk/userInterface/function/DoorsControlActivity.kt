package com.example.automaticdoorsapk.userInterface.function

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue

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
    // State for the user's name
    var tempName by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var isNameValid by remember { mutableStateOf(false) } // To track if name is valid

    // States for controlling the doors
    val isInternalDoorOpen = remember { mutableStateOf(false) }
    val isExternalDoorOpen = remember { mutableStateOf(false) }

    // Functions to toggle door states
    fun toggleInternalDoor() {
        if (isInternalDoorOpen.value) {
            isInternalDoorOpen.value = false
        } else {
            // Close external door if it's open
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
            // Close internal door if it's open
            if (isInternalDoorOpen.value) {
                isInternalDoorOpen.value = false
            }
            isExternalDoorOpen.value = true
        }
    }

    // Layout for the screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Name input section
        if (name.isEmpty()) {
            Text(
                text = "Digite seu nome:",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Input field for the person's name
            OutlinedTextField(
                value = tempName,
                onValueChange = {
                    tempName = it
                    isNameValid = tempName.isNotEmpty() // Validate name
                },
                label = { Text("Nome") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Button to confirm the name
            Button(
                onClick = {
                    name = tempName;
                },
                modifier = Modifier.padding(top = 16.dp),
                enabled = isNameValid // Button only enabled if name is valid
            ) {
                Text("Confirmar Nome")
            }
        } else {
            // If the name is entered, show the door controls
            Button(
                onClick = {
                    isNameValid = false;
                    name = "";
                }
            ){
                Text(text = "Mudar Nome")
            }

            Text(
                text = "Bem-vindo, $name!",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Toggle Internal Door Button
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

            // Toggle External Door Button
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
