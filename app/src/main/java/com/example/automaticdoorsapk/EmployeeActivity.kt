package com.example.automaticdoorsapk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.automaticdoorsapk.userInterface.EmployeeScreen

class ServerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmployeeScreen()  // Tela do Servidor
        }
    }
}
