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
import android.content.Context
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.automaticdoorsapk.network.MqttManager

class OpenCloseDoorsActivity : ComponentActivity() {

    private val mqttManager = MqttManager() // Certifique-se de inicializar corretamente
    private val viewModel: OpenCloseDoorsViewModel by viewModels { 
        OpenCloseDoorsViewModelFactory(mqttManager) 
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OpenCloseDoorsScreen(viewModel)
        }
    }
}
@Composable
fun OpenCloseDoorsScreen(viewModel: OpenCloseDoorsViewModel = viewModel()) {
    // Observa os estados do ViewModel
    val userName by viewModel.userName.observeAsState("")
    val isInternalDoorOpen by viewModel.isInternalDoorOpen.observeAsState(false)
    val isExternalDoorOpen by viewModel.isExternalDoorOpen.observeAsState(false)

    var tempName by remember { mutableStateOf("") }
    var isNameValid by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (userName.isEmpty()) {
            // Se o nome do usuário ainda não foi definido
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
                onClick = { viewModel.setUserName(tempName) },
                modifier = Modifier.padding(top = 16.dp),
                enabled = isNameValid
            ) {
                Text("Confirmar Nome")
            }
        } else {
            // Controles para abrir/fechar portas
            Button(
                onClick = { viewModel.setUserName("") },
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text("Mudar Nome")
            }

            Text(
                text = "Bem-vindo, $userName!",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Controle da Porta Interna
            Button(
                onClick = { viewModel.toggleInternalDoor() },
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(text = if (isInternalDoorOpen) "Fechar Porta Interna" else "Abrir Porta Interna")
            }

            Text(
                text = "Porta Interna: ${if (isInternalDoorOpen) "Aberta" else "Fechada"}",
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Controle da Porta Externa
            Button(
                onClick = { viewModel.toggleExternalDoor() },
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(text = if (isExternalDoorOpen) "Fechar Porta Externa" else "Abrir Porta Externa")
            }

            Text(
                text = "Porta Externa: ${if (isExternalDoorOpen) "Aberta" else "Fechada"}"
            )
        }
    }
}

fun openOpenCloseDoorsActivity(context: Context) {
    val intent = Intent(context, OpenCloseDoorsActivity::class.java)
    context.startActivity(intent)
}
