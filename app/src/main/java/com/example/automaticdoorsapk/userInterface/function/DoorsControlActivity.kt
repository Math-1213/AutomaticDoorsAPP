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
import com.google.android.gms.location.LocationServices
import com.example.automaticdoorsapk.userInterface.function.LocationHelper

class OpenCloseDoorsActivity : ComponentActivity() {

    private lateinit var locationHelper: LocationHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicialize o LocationHelper
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationHelper = LocationHelper(this, fusedLocationClient)

        setContent {
            OpenCloseDoorsScreen(
                onInternalDoorToggled = { locationHelper.fetchLocation() },
                onExternalDoorToggled = { locationHelper.fetchLocation() }
            )
        }
    }
}

@Composable
fun OpenCloseDoorsScreen(
    onInternalDoorToggled: () -> Unit,
    onExternalDoorToggled: () -> Unit
) {
    var tempName by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var isNameValid by remember { mutableStateOf(false) }

    val isInternalDoorOpen = remember { mutableStateOf(false) }
    val isExternalDoorOpen = remember { mutableStateOf(false) }

    fun toggleInternalDoor() {
        if (isInternalDoorOpen.value) {
            isInternalDoorOpen.value = false
        } else {
            if (isExternalDoorOpen.value) {
                isExternalDoorOpen.value = false
            }
            isInternalDoorOpen.value = true
        }
        onInternalDoorToggled() // Chama o registro da localização
    }

    fun toggleExternalDoor() {
        if (isExternalDoorOpen.value) {
            isExternalDoorOpen.value = false
        } else {
            if (isInternalDoorOpen.value) {
                isInternalDoorOpen.value = false
            }
            isExternalDoorOpen.value = true
        }
        onExternalDoorToggled() // Chama o registro da localização
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (name.isEmpty()) {
            Text(
                text = "Digite seu nome:",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = tempName,
                onValueChange = {
                    tempName = it
                    isNameValid = tempName.isNotEmpty()
                },
                label = { Text("Nome") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Button(
                onClick = { name = tempName },
                modifier = Modifier.padding(top = 16.dp),
                enabled = isNameValid
            ) {
                Text("Confirmar Nome")
            }
        } else {
            Button(
                onClick = {
                    isNameValid = false
                    name = ""
                }
            ) {
                Text(text = "Mudar Nome")
            }

            Text(
                text = "Bem-vindo, $name!",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 32.dp)
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
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    OpenCloseDoorsScreen(
        onInternalDoorToggled = {},
        onExternalDoorToggled = {}
    )
}

fun openOpenCloseDoorsActivity(context: android.content.Context) {
    val intent = Intent(context, OpenCloseDoorsActivity::class.java)
    context.startActivity(intent)
}
