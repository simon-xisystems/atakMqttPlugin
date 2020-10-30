package com.atakmap.android.plugintemplate

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.Toast
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


class PluginTemplateDropDownReceiver(mapView: MapView?,
                                     private val pluginContext: Context) : DropDownReceiver(mapView), OnStateListener {
    private val templateView: View

    private val lifeCycleOwner: LifecycleOwner


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

        //declare out COT handler, allowing the plugin to pick up on outgoing COT

            val handler = CotHandler()
            handler.OutboundCotMessageHandler(CommsMapComponent.getInstance(),mapView.context)

           //initialise out MQTT client
            val mqttManager = Mqttmanager(mapView.context)
            mqttManager.connectMqtt()


            //Grab our call sign for the test MQTT button
            val callSign = mapView.deviceCallsign

            //a random made up topic
            val topic = "testtopic/atak"

            val mqttButton = templateView.findViewById<Button>(R.id.mqttPublish)

            //initialise out singleton, allowing us to trigger live data updates from Cot events.
            val ownloc = uiPassThrough.ownReport

            ownloc.observe(lifeCycleOwner, Observer {ownCot ->
                //when live data observation occurs send a MQTT message
                mqttManager.publish(topic, ownCot)
                Toast.makeText(mapView.context, "MQTT Send to $topic",Toast.LENGTH_SHORT).show()

            })


            mqttButton.setOnClickListener {
                //publish a MQTT topic when we hit the test button.
                mqttManager.publish(topic, callSign)
            }


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