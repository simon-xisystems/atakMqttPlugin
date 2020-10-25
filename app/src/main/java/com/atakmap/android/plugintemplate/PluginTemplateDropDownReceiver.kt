package com.atakmap.android.plugintemplate

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Button
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.atak.plugins.impl.PluginLayoutInflater
import com.atakmap.android.dropdown.DropDown.OnStateListener
import com.atakmap.android.dropdown.DropDownReceiver
import com.atakmap.android.maps.MapView
import com.atakmap.android.plugintemplate.mqtt.Mqttmanager
import com.atakmap.android.plugintemplate.plugin.R
import com.atakmap.comms.CommsMapComponent
import com.atakmap.coremap.log.Log
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import java.lang.Exception

class PluginTemplateDropDownReceiver(mapView: MapView?,
                                     private val pluginContext: Context) : DropDownReceiver(mapView), OnStateListener {
    private val templateView: View
    private var cotHandler: CotHandler? = null
    private val lifeCycleOwner: LifecycleOwner
    private var outboundCotMessageHandler: OutboundCotMessageHandler? = null
   // private lateinit var mqttClient: MqttAndroidClient
    /**************************** CONSTRUCTOR  */
    init {

        // Remember to use the PluginLayoutInflator if you are actually inflating a custom view
        // In this case, using it is not necessary - but I am putting it here to remind
        // developers to look at this Inflator
        templateView = PluginLayoutInflater.inflate(pluginContext, R.layout.main_layout, null)
        lifeCycleOwner = mapView?.context as LifecycleOwner
    }




    /**************************** PUBLIC METHODS  */
    public override fun disposeImpl() {}

    /**************************** INHERITED METHODS  */
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action ?: return
        if (action == SHOW_PLUGIN) {
            Log.d(TAG, "showing plugin drop down")
            showDropDown(templateView, HALF_WIDTH, FULL_HEIGHT, FULL_WIDTH,
                    HALF_HEIGHT, false)

            //this.cotHandler = CotHandler(CommsMapComponent.getInstance(), getMapView().context)
            this.outboundCotMessageHandler = OutboundCotMessageHandler(CommsMapComponent.getInstance(), getMapView().context)

            val mqttManager = Mqttmanager(mapView.context)
            mqttManager.connectMqtt()


            val callSign = mapView.deviceCallsign


            val topic = "testtopic/atak"

            val mqttButton = templateView.findViewById<Button>(R.id.mqttPublish)


            val ownloc = uiPassThrough.ownReport

            ownloc.observe(lifeCycleOwner, Observer {ownCot ->
                mqttManager.publish(topic, ownCot)
                println("bob: send a cot $ownCot")

            })


            mqttButton.setOnClickListener {
                mqttManager.publish(topic, callSign)
            }



//            connectMqtt(mapView.context)
//            mqttSubscribe("testtopic/#")

        }
    }

    override fun onDropDownSelectionRemoved() {}
    override fun onDropDownVisible(v: Boolean) {}
    override fun onDropDownSizeChanged(width: Double, height: Double) {}
    override fun onDropDownClose() {}

    companion object {
        val TAG = PluginTemplateDropDownReceiver::class.java
                .simpleName
        const val SHOW_PLUGIN = "com.atakmap.android.plugintemplate.SHOW_PLUGIN"
    }










}