package com.example.automaticdoorsapk.userInterface.function.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "log_entries")
data class LogEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: Long, // Timestamp de quando a ação ocorreu
    val loginName: String, // Nome do usuário
    val methodOfLogin: String, // Método de login (e.g., manual, cartão, app)
    val additionalInfo: String // Informações adicionais sobre a ação
)