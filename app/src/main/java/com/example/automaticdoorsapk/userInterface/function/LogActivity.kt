package com.example.automaticdoorsapk.userInterface.function

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.automaticdoorsapk.userInterface.function.ui.theme.AutomaticDoorsAPKTheme

class LogActivity : ComponentActivity() {

    private lateinit var logViewModel: LogViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logViewModel = ViewModelProvider(this).get(LogViewModel::class.java)

        setContent {
            AutomaticDoorsAPKTheme {
                LogScreen(logViewModel)
            }
        }
    }
}

@Composable
fun LogScreen(viewModel: LogViewModel) {
    // Observe the log entries
    val logEntries = viewModel.allLogs.observeAsState(emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Log Entries",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // LazyColumn to display the log entries
        LazyColumn {
            items(logEntries.value) { entry ->
                LogEntryItem(entry)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun LogEntryItem(entry: LogEntry) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.small)
            .padding(16.dp)
    ) {
        Text(text = "Date: ${entry.date}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Login Name: ${entry.loginName}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Method of Login: ${entry.methodOfLogin}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Additional Info: ${entry.additionalInfo}", style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLogScreen() {
    LogScreen(viewModel = LogViewModel(Application())) // Preview requires context, so you can pass mock data for preview
}
