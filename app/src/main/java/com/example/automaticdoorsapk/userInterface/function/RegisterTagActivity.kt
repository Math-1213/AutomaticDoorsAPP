package com.example.automaticdoorsapk.userInterface.function

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.automaticdoorsapk.userInterface.function.data.Tag

class RegisterTagActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegisterTagScreen()
        }
    }
}

@Composable
fun RegisterTagScreen(viewModel: RegisterTagViewModel = viewModel()) {
    // Observar o estado do ViewModel
    val tagName by viewModel.tagName.observeAsState("")
    val tagDescription by viewModel.tagDescription.observeAsState("")
    val isTagValid by viewModel.isTagValid.observeAsState(false)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Registrar Nova Tag",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Text("Tag ID: 123456789", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de entrada para o nome da tag
        OutlinedTextField(
            value = tagName,
            onValueChange = { viewModel.setTagName(it) },
            label = { Text("Nome da Tag") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de entrada para a descrição da tag
        OutlinedTextField(
            value = tagDescription,
            onValueChange = { viewModel.setTagDescription(it) },
            label = { Text("Descrição da Tag") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = false
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (isTagValid) {
                    viewModel.saveTag() // Salvar a tag através do ViewModel
                    Toast.makeText(LocalContext.current, "Tag registrada com sucesso!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(LocalContext.current, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isTagValid
        ) {
            Text("Registrar Tag")
        }
    }
}


fun saveTag(tag: Tag) {
    // This is where you'd save the tag to a database or API
    // For this example, we just log it to the console (or use in-memory storage)
    println("Tag saved: $tag")
}

fun navigateToRegisterTag(context: android.content.Context) {
    val intent = Intent(context, RegisterTagActivity::class.java)
    context.startActivity(intent)
}
