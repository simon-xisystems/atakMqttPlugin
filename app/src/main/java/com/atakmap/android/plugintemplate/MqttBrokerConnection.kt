package com.atakmap.android.plugintemplate

import android.content.Context
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import java.lang.Exception

class MqttBrokerConnection(context: Context) {
    private lateinit var mqttClient: MqttAndroidClient
    private var context = context

    fun connectMqtt(context: Context){

        val serverURI = "tcp://broker.hivemq.com:1883"
        mqttClient = MqttAndroidClient(context, serverURI, "atak_client")
        mqttClient.setCallback(object: MqttCallback {
            override fun messageArrived(topic: String?, message: MqttMessage?) {
                println("bob: MQTT Message Arrived")

            }

            override fun connectionLost(cause: Throwable?) {
                println("bob: MQTT Broker Connection Lost")
            }


            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                println("bob: MQTT delivery complete")
            }

        })

        val options  = MqttConnectOptions()
        try{
            mqttClient.connect(options,context,object: IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    println("bob: MQTT Broker connection success")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    println("bob: MQTT Broker connection failure")
                }


            })
        }catch (e: Exception){
            println("bob: Mqttclinent.connect error $e")
        }




    }

}