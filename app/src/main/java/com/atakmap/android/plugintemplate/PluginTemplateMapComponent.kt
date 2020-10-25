package com.atakmap.android.plugintemplate

import android.content.Context
import android.content.Intent
import com.atakmap.android.dropdown.DropDownMapComponent
import com.atakmap.android.ipc.AtakBroadcast.DocumentedIntentFilter
import com.atakmap.android.maps.MapView
import com.atakmap.android.plugintemplate.plugin.R
import com.atakmap.coremap.log.Log
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import java.lang.Exception

class PluginTemplateMapComponent : DropDownMapComponent() {
    private var pluginContext: Context? = null
    private var ddr: PluginTemplateDropDownReceiver? = null
    //private lateinit var mqttAndroidClient: MqttAndroidClient
    private lateinit var mqttClient: MqttAndroidClient
    //private lateinit var mqttClient: MqttHelper


    //private val mapView: MapView? = null
    override fun onCreate(context: Context, intent: Intent,
                          view: MapView) {
        context.setTheme(R.style.ATAKPluginTheme)
        super.onCreate(context, intent, view)
        pluginContext = context
        ddr = PluginTemplateDropDownReceiver(
                view, context)
        Log.d(TAG, "registering the plugin filter")
        val ddFilter = DocumentedIntentFilter()
        ddFilter.addAction(PluginTemplateDropDownReceiver.SHOW_PLUGIN)
        registerDropDownReceiver(ddr, ddFilter)





        /*startMqttService()
        connectMqtt(mapView!!.context)*/




    }

    override fun onDestroyImpl(context: Context, view: MapView) {
        super.onDestroyImpl(context, view)
    }




    fun connectMqtt(context: Context){

        val serverURI = "tcp://broker.hivemq.com:1883"
        mqttClient = MqttAndroidClient(context, serverURI, "atak_client")
        mqttClient.setCallback(object: MqttCallback{
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
            mqttClient.connect(options,context,object: IMqttActionListener{
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




    companion object {
        private const val TAG = "PluginTemplateMapComponent"
        const val TAG2 = "AndroidMqttClient"
    }

















}





