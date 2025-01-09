package com.example.automaticdoorsapk.network

import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import android.content.Context

class MqttManager(context: Context, private val brokerUrl: String, private val clientId: String) {

    private val mqttClient: MqttAndroidClient = MqttAndroidClient(context, brokerUrl, clientId)

    fun connect(onConnected: () -> Unit, onError: (Throwable) -> Unit) {
        val options = MqttConnectOptions().apply {
            isCleanSession = true
        }

        mqttClient.connect(options, null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                onConnected()
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                exception?.let { onError(it) }
            }
        })
    }

    fun publish(topic: String, message: String, qos: Int = 1, retained: Boolean = false) {
        val mqttMessage = MqttMessage(message.toByteArray()).apply {
            this.qos = qos
            this.isRetained = retained
        }
        mqttClient.publish(topic, mqttMessage)
    }

    fun disconnect() {
        mqttClient.disconnect()
    }
}