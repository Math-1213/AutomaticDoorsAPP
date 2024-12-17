package com.example.automaticdoorsapk.userInterface.function.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LogEntryDao {

    @Insert
    suspend fun insertLog(logEntry: LogEntry)

    @Query("SELECT * FROM log_entries ORDER BY id DESC")
    fun getAllLogs(): LiveData<List<LogEntry>>
}
