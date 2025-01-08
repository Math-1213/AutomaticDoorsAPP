package com.example.automaticdoorsapk.userInterface.function

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.automaticdoorsapk.userInterface.function.data.Tag
import com.example.automaticdoorsapk.network.MqttManager

class RegisterTagActivity : ComponentActivity() {

    private val mqttManager = MqttManager() // Certifique-se de inicializar corretamente
    private val viewModel: RegisterTagViewModel by viewModels {
        RegisterTagViewModelFactory(mqttManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegisterTagScreen(viewModel)
        }
    }
}

@Composable
fun RegisterTagScreen(viewModel: RegisterTagViewModel) {
    val isRegisterMode by viewModel.isRegisterMode.observeAsState(false)
    val tagId by viewModel.tagId.observeAsState("Aguardando Tag...")
    val tagName by viewModel.tagName.observeAsState("")
    val tagDescription by viewModel.tagDescription.observeAsState("")
    val isTagValid by viewModel.isTagValid.observeAsState(false)

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Botão para alternar entre os modos
        Button(
            onClick = { viewModel.toggleMode() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isRegisterMode) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
            )
        ) {
            Text(if (isRegisterMode) "Parar Registro" else "Iniciar Registro")
        }

        Spacer(modifier = Modifier.height(32.dp))

        if (isRegisterMode) {
            // Modo de Registro
            Text("Modo Registro Ativo", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            Text("ID da Tag: $tagId", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = tagName,
                onValueChange = { viewModel.setTagName(it) },
                label = { Text("Nome do Proprietário") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = tagDescription,
                onValueChange = { viewModel.setTagDescription(it) },
                label = { Text("Informações Adicionais") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    viewModel.saveTag()
                    Toast.makeText(context, "Tag registrada com sucesso!", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = isTagValid
            ) {
                Text("Salvar Tag")
            }
        } else {
            // Modo de Funcionamento
            Text("Modo Funcionamento Ativo", style = MaterialTheme.typography.headlineMedium)
        }
    }
}
