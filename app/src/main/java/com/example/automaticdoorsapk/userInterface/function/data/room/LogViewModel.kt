package com.example.automaticdoorsapk.userInterface.function.data.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.automaticdoorsapk.userInterface.function.data.room.AppDatabase
import com.example.automaticdoorsapk.userInterface.function.data.room.LogEntry
import kotlinx.coroutines.launch

class LogViewModel(application: Application) : AndroidViewModel(application) {

    private val logEntryDao = AppDatabase.getDatabase(application).logEntryDao()
    val allLogs: LiveData<List<LogEntry>> = logEntryDao.getAllLogs()

    // Função para inserir um log individual
    fun insertLog(logEntry: LogEntry) {
        viewModelScope.launch {
            logEntryDao.insertLog(logEntry)
        }
    }
    // Função para inserir logs de exemplo
    fun insertExampleLogs() {
        viewModelScope.launch {
            val exampleLogEntries = listOf(
                LogEntry(
                    date = System.currentTimeMillis(),
                    loginName = "user1",
                    methodOfLogin = "manual",
                    additionalInfo = "Acesso realizado pela porta principal"
                ),
                LogEntry(
                    date = System.currentTimeMillis() - 1000000,
                    loginName = "admin",
                    methodOfLogin = "cartão",
                    additionalInfo = "Acesso com cartão de segurança"
                ),
                LogEntry(
                    date = System.currentTimeMillis() - 5000000,
                    loginName = "guest",
                    methodOfLogin = "app",
                    additionalInfo = "Acesso feito através do aplicativo"
                )
            )

            // Inserindo os logs de exemplo no banco de dados
            exampleLogEntries.forEach { logEntry ->
                logEntryDao.insertLog(logEntry)
            }
        }
    }
}
