package com.example.automaticdoorsapk.network

import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import android.content.Context
import android.util.Log

class MqttManager(context: Context, private val brokerUrl: String, private val clientId: String) {

    private val mqttClient: MqttAndroidClient = MqttAndroidClient(context, brokerUrl, clientId)
    private var isConnected: Boolean = false

    fun connect(onConnected: () -> Unit, onError: (Throwable) -> Unit) {
        val options = MqttConnectOptions().apply {
            isCleanSession = true
            // You can set additional options here if needed, like username/password
        }

        mqttClient.connect(options, null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                isConnected = true
                Log.d("MqttManager", "Conectado ao broker")
                onConnected()
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                isConnected = false
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
                    }

                    override fun deliveryComplete(token: IMqttDeliveryToken?) {
                        // Delivery complete logic (if needed)
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
        } catch (e: MqttException) {
            // Handle disconnect exception
            e.printStackTrace() // or log the exception
        }
    }

    fun isConnected(): Boolean {
        return mqttClient.isConnected
    }
}
