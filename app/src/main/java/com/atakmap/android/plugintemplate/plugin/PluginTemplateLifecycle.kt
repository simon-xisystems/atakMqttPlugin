package com.atakmap.android.plugintemplate.plugin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import com.atakmap.android.maps.MapComponent
import com.atakmap.android.maps.MapView
import com.atakmap.android.plugintemplate.PluginTemplateMapComponent
import com.atakmap.coremap.log.Log
import transapps.maps.plugin.lifecycle.Lifecycle
import java.util.*

class PluginTemplateLifecycle(private val pluginContext: Context) : Lifecycle {
    private val overlays: MutableCollection<MapComponent>
    private var mapView: MapView?
    override fun onConfigurationChanged(arg0: Configuration) {
        for (c in overlays) c.onConfigurationChanged(arg0)
    }

    override fun onCreate(arg0: Activity,
                          arg1: transapps.mapi.MapView) {
        if (arg1 == null || arg1.view !is MapView) {
            Log.w(TAG, "This plugin is only compatible with ATAK MapView")
            return
        }
        mapView = arg1.view as MapView
        overlays
                .add(PluginTemplateMapComponent())

        // create components
        val iter = overlays
                .iterator()
        var c: MapComponent
        while (iter.hasNext()) {
            c = iter.next()
            try {
                c.onCreate(pluginContext,
                        arg0.intent,
                        mapView)
            } catch (e: Exception) {
                Log.w(TAG,
                        "Unhandled exception trying to create overlays MapComponent",
                        e)
                iter.remove()
            }
        }

        startMqttService()

    }

    override fun onDestroy() {
        for (c in overlays) c.onDestroy(pluginContext, mapView)
    }

    override fun onFinish() {
        // XXX - no corresponding MapComponent method
    }

    override fun onPause() {
        for (c in overlays) c.onPause(pluginContext, mapView)
    }

    override fun onResume() {
        for (c in overlays) c.onResume(pluginContext, mapView)
    }

    override fun onStart() {
        for (c in overlays) c.onStart(pluginContext, mapView)
    }

    override fun onStop() {
        for (c in overlays) c.onStop(pluginContext, mapView)
    }

    companion object {
        private const val TAG = "PluginTemplateLifecycle"
    }

    fun startMqttService(){
        val serviceIntent = Intent()
        serviceIntent.action = "org.eclipse.paho.android.service.MqttService"
        try{
            mapView?.context?.startService(serviceIntent)
        }catch(e: java.lang.Exception){
            println("bob: service didnt start")
        }

        println("bob: starting MQTT Serivce")

    }




    init {
        overlays = LinkedList()
        mapView = null
        PluginNativeLoader.init(pluginContext)
    }
}