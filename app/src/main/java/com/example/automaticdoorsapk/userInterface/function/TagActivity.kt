package com.example.automaticdoorsapk.userInterface.function

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.automaticdoorsapk.userInterface.function.data.Tag

class TagsActivity : ComponentActivity() {

    private val viewModel: TagsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TagsScreen(viewModel = viewModel) { tag ->
                intent.putExtra("tagId", tag.id)
                intent.putExtra("tagName", tag.name)
                intent.putExtra("tagDescription", tag.description)
                startActivity(intent)
            }
        }
        viewModel.loadTags() // Carrega as tags
    }
}

@Composable
fun TagsScreen(viewModel: TagsViewModel, onEditTag: (Tag) -> Unit) {
    val tags by viewModel.tags.observeAsState(emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Tags Registradas",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(tags) { tag ->
                TagItem(tag = tag, onEditTag = onEditTag)
            }
        }
    }
}

@Composable
fun TagItem(tag: Tag, onEditTag: (Tag) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = "ID: ${tag.id}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Nome: ${tag.name}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Descrição: ${tag.description}", style = MaterialTheme.typography.bodySmall)
            }
            Button(onClick = { onEditTag(tag) }) {
                Text("Editar")
            }
        }
    }
}