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
fun RegisterTagScreen() {
    // State to store the tag information
    //TO DO
    var tagId by remember { mutableStateOf("123456789") }  // Assume this ID comes from the RFID system*
    var tagName by remember { mutableStateOf("") }
    var tagDescription by remember { mutableStateOf("") }
    var isTagValid by remember { mutableStateOf(false) }

    // Validate input
    isTagValid = tagName.isNotEmpty() && tagDescription.isNotEmpty()

    // Layout for the screen
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

        Text("Tag ID: $tagId", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Input field for the tag name
        OutlinedTextField(
            value = tagName,
            onValueChange = { tagName = it },
            label = { Text("Nome da Tag") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Input field for the tag description
        OutlinedTextField(
            value = tagDescription,
            onValueChange = { tagDescription = it },
            label = { Text("Descrição da Tag") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = false
        )

        Spacer(modifier = Modifier.height(32.dp))
        val context = LocalContext.current  // Get the context from Compose

        // Button to register the tag
        Button(
            onClick = {
                if (isTagValid) {
                    // Here we would save the tag to a local database or API
                    saveTag(Tag(id = tagId, name = tagName, description = tagDescription))
                    Toast.makeText(
                        context,
                        "Tag registrada com sucesso!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // Notify the user to fill all fields
                    Toast.makeText(
                        context,
                        "Por favor, preencha todos os campos.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isTagValid  // Button is enabled only when all fields are filled
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