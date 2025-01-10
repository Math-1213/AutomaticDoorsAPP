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

    fun insertLog(logEntry: LogEntry) {
        viewModelScope.launch {
            logEntryDao.insertLog(logEntry)
        }
    }
}

//TODO