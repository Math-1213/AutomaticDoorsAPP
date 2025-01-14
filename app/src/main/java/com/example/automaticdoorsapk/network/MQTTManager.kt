package com.example.automaticdoorsapk.network

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

class MqttManager(context: Context, private val brokerUrl: String, private val clientId: String) {

    private val mqttClient: MqttAndroidClient = MqttAndroidClient(context, brokerUrl, clientId)
    private var isConnected: Boolean = false

    // LiveData para monitorar o status da conexão
    private val _connectionStatus = MutableLiveData<Boolean>()
    val connectionStatus: LiveData<Boolean> get() = _connectionStatus

    fun connect(onConnected: () -> Unit, onError: (Throwable) -> Unit) {
        val options = MqttConnectOptions().apply {
            isCleanSession = true
        }

        mqttClient.connect(options, null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                isConnected = true
                _connectionStatus.postValue(true)
                Log.d("MqttManager", "Conectado ao broker")
                // Inscrever-se em tópicos apenas após a conexão
                subscribeToRfidTopic(onMessageReceived = { message ->
                    Log.d("RegisterTagActivity", "Mensagem recebida do tópico RFID: $message")
                }, onError = { throwable ->
                    Log.e("RegisterTagActivity", "Erro ao inscrever no tópico RFID: ${throwable.message}")
                })
                subscribeToRegModeTopic(onMessageReceived = { message ->
                    Log.d("RegisterTagActivity", "Mensagem recebida do tópico inRegMode: $message")
                }, onError = { throwable ->
                    Log.e("RegisterTagActivity", "Erro ao inscrever no tópico inRegMode: ${throwable.message}")
                })
                onConnected()
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                isConnected = false
                _connectionStatus.postValue(false)
                Log.e("MqttManager", "Falha ao conectar: ${exception?.message}")
                exception?.let { onError(it) }
            }
        })
    }


    fun publish(topic: String, message: String) {
        if (mqttClient.isConnected) {
            val mqttMessage = MqttMessage(message.toByteArray())
            mqttClient.publish(topic, mqttMessage)
        } else {
            Log.e("MqttManager", "MQTT client is not connected")
            // Aqui você pode tentar reconectar ou mostrar uma mensagem ao usuário
        }
    }

    fun subscribe(topic: String, onMessageReceived: (String) -> Unit, onError: (Throwable) -> Unit) {
        if (mqttClient.isConnected) {
            try {
                mqttClient.subscribe(topic, 0) // QoS level can be adjusted
                mqttClient.setCallback(object : MqttCallback {
                    override fun messageArrived(topic: String?, message: MqttMessage?) {
                        message?.let {
                            onMessageReceived(String(it.payload))
                        }
                    }

                    override fun connectionLost(cause: Throwable?) {
                        Log.e("MqttManager", "Connection lost: ${cause?.message}")
                        isConnected = false
                        _connectionStatus.postValue(false) // Atualiza o LiveData
                    }

                    override fun deliveryComplete(token: IMqttDeliveryToken?) {
                        // Delivery complete logic
                    }
                })
            } catch (e: MqttException) {
                onError(e)
            }
        } else {
            Log.e("MqttManager", "MQTT client is not connected")
        }
    }

    fun disconnect() {
        try {
            mqttClient.disconnect()
            isConnected = false
            _connectionStatus.postValue(false) // Atualiza o LiveData
        } catch (e: MqttException) {
            // Handle disconnect exception
            e.printStackTrace() // or log the exception
        }
    }

    fun isConnected(): Boolean {
        return mqttClient.isConnected
    }

    fun subscribeToRfidTopic(onMessageReceived: (String) -> Unit, onError: (Throwable) -> Unit) {
        try {
            mqttClient.subscribe("home/doors/RFID", 1)
            mqttClient.setCallback(object : MqttCallback {
                override fun messageArrived(topic: String?, message: MqttMessage?) {
                    if (topic == "home/doors/RFID" && message != null) {
                        onMessageReceived(String(message.payload))
                    }
                }

                override fun connectionLost(cause: Throwable?) {
                    onError(cause ?: Throwable("Conexão perdida"))
                }

                override fun deliveryComplete(token: IMqttDeliveryToken?) {}
            })
        } catch (e: MqttException) {
            onError(e)
        }
    }

    // Função para se inscrever no tópico home/doors/inRegMode
    fun subscribeToRegModeTopic(onMessageReceived: (String) -> Unit, onError: (Throwable) -> Unit) {
        mqttClient?.subscribe("home/doors/inRegMode", 1) { _, message ->
            onMessageReceived(String(message.payload))
        }
        // Adicionando tratamento de erro
        mqttClient?.setCallback(object : MqttCallback {
            override fun messageArrived(topic: String?, message: MqttMessage?) {
                if (topic == "home/doors/inRegMode" && message != null) {
                    onMessageReceived(String(message.payload))
                }
            }

            override fun connectionLost(cause: Throwable?) {
                onError(cause ?: Throwable("Conexão perdida"))
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {}
        })
    }
}
