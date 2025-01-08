package com.example.automaticdoorsapk.userInterface.function

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.automaticdoorsapk.userInterface.function.data.Tag

class EditTagActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tagId = intent.getStringExtra("tagId") ?: ""
        val tagName = intent.getStringExtra("tagName") ?: ""
        val tagDescription = intent.getStringExtra("tagDescription") ?: ""

        setContent {
            EditTagScreen(
                initialTag = Tag(tagId, tagName, tagDescription),
                onSaveTag = { updatedTag ->
                    Toast.makeText(this, "Tag ${updatedTag.name} atualizada!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            )
        }
    }
}

@Composable
fun EditTagScreen(initialTag: Tag, onSaveTag: (Tag) -> Unit) {
    var tagName by remember { mutableStateOf(initialTag.name) }
    var tagDescription by remember { mutableStateOf(initialTag.description) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Editar Tag", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Text("ID: ${initialTag.id}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = tagName,
            onValueChange = { tagName = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = tagDescription,
            onValueChange = { tagDescription = it },
            label = { Text("Descrição") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                onSaveTag(Tag(initialTag.id, tagName, tagDescription))
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salvar")
        }
    }
}
