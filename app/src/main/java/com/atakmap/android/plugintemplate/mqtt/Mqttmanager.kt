package com.atakmap.android.plugintemplate.mqtt

import android.content.Context
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import java.lang.Exception

class Mqttmanager(
        context: Context
) {
    private lateinit var mqttClient: MqttAndroidClient
    private var context = context


    fun connectMqtt() {

        val serverURI = "tcp://broker.hivemq.com:1883"
        mqttClient = MqttAndroidClient(context, serverURI, "atak_client")
        mqttClient.setCallback(object : MqttCallback {
            override fun messageArrived(topic: String?, message: MqttMessage?) {
                println("bob: MQTT Message Arrived $message : $topic")

            }

            override fun connectionLost(cause: Throwable?) {
                println("bob: MQTT Broker Connection Lost")
            }


            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                println("bob: MQTT delivery complete")
            }

        })

        val options = MqttConnectOptions()
        try {
            mqttClient.connect(options, context, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    println("bob: MQTT Broker connection success")
                    subscribe()
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    println("bob: MQTT Broker connection failure $exception")
                }


            })
        } catch (e: Exception) {
            println("bob: Mqttclinent.connect error $e")
        }


    }


    fun subscribe() {

        val topic = "testtopic/atak"
        val qos = 2 // Mention your qos value
        try {
            mqttClient.subscribe(topic, qos, context, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    // Give your callback on Subscription here
                    if(topic.contains("atak")){

                        println("bob:  subscribed to $topic")
                    }

                }

                override fun onFailure(
                        asyncActionToken: IMqttToken,
                        exception: Throwable
                ) {
                    // Give your subscription failure callback here
                    println("bob: Subscription failure")
                }
            })
        } catch (e: MqttException) {
            // Give your subscription failure callback here
            println("bob: $e : exception")
        }


    }

    //fun publish(topic: String, data: String) {
    fun publish(topic: String, data: String) {
        val encodedPayload : ByteArray
        try {
            encodedPayload = data.toByteArray(charset("UTF-8"))
            val message = MqttMessage(encodedPayload)
            message.qos = 2
            message.isRetained = false
            mqttClient.publish(topic, message)
        } catch (e: Exception) {

            println("bob: publish error $e")
            // Give Callback on error here
        } catch (e: MqttException) {
            // Give Callback on error here
            println("bob: publish error $e")
        }
    }


}