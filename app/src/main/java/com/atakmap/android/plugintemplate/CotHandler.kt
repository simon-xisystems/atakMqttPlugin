package com.atakmap.android.plugintemplate

import android.content.Context
import com.atakmap.android.plugintemplate.mqtt.lffiMessage
import com.atakmap.android.plugintemplate.mqtt.movementRecord
import com.atakmap.android.plugintemplate.mqtt.positionRecord
import com.atakmap.comms.CommsMapComponent
import com.atakmap.coremap.cot.event.CotEvent
import com.atakmap.coremap.maps.time.CoordinatedTime
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.text.SimpleDateFormat
import java.util.*

class CotHandler: CommsMapComponent.PreSendProcessor {
    private var commsMapComponent1: CommsMapComponent? = null
    private var atakContext1: Context? = null

    fun OutboundCotMessageHandler(commsMapComponent: CommsMapComponent,
                                  atakContext: Context
    ) {
        commsMapComponent1 = commsMapComponent
        atakContext1 = atakContext
        commsMapComponent.registerPreSendProcessor(this)
    }

    override fun processCotEvent(cotEvent: CotEvent?, toUID: Array<out String>?) {


        println("bob: COT Event Processed")

        val gson = Gson()


        var trackPositionData = positionRecord(
                 latitudeInDecimalDegrees = cotEvent!!.geoPoint!!.latitude,
                 longitudeInDecimalDegrees = cotEvent!!.geoPoint!!.longitude,
                 zuluTimeOfFix = cotEvent?.time as CoordinatedTime ,
                 horizontalAccuracyInM = cotEvent?.geoPoint.ce,
                 altitudeInM = 0.00,
                 verticalAccuracyInM =0.00
        )

        var movementData = movementRecord(
                bearing = 0.00f,
                bearingAccuracy = 0.00f,
                speed = 0.00f,
                speedAccuracy = 0.00f,
                inclination = 0.00f,
                inclinationAccuracy =0.00f
        )


        var lffi = lffiMessage(
                trackSourceTransponderId = cotEvent.uid,
                trackSourceSystem = "ATAK",
                trackSecurity = "unclassified",
                trackSIDC = "",
                trackPositionalData = trackPositionData,
                trackMovementData = movementData
        )


        var lffiMessage = {
            "trackSourceTransponderId:" + cotEvent.uid +
                    "zuluTimeOfFix:" + cotEvent.time +
                    "latitudeInDecimalDegrees:" + cotEvent.geoPoint.latitude +
                    "longitudeInDecimalDegrees:" + cotEvent.geoPoint.longitude

        }


        val casEvent = "b-r-f-h-c"




        val cotMessage = cotEvent!!
        val type = cotMessage.type


        when{

            cotMessage.type =="u-d-f" -> println("bob: Display Map Drawn Polygon ")
            cotMessage.type == "u-d-r" -> println("bob: Display Map Drawn Square  ")
            cotMessage.type == "u-d-c-c" -> println("bob: Display Map Drawn Circle ")
            cotMessage.type == "u-d-f-m" -> println("bob:  Display Map freehand  ")
            cotMessage.type == "b-m-r" -> println("bob: Display Map Route  ")
            cotMessage.type == "b-t-f" -> println("bob:  Display Chat Event ")
            cotMessage.type == "b-r-f-h-c" ->  println("bob:  Display Casualty Request ")
            cotMessage.type == "a-f-G-U-C" ->{
                println("bob:  Sending own position report $lffi")
                lffi.trackSIDC = "SFGPUCI-----"
                val jsonLffi = gson.toJson(lffi)
                //println("json: $jsonLffi")
                uiPassThrough.ownLocationReport(lffi.toString())
            }

            else -> println("bob:  point item ")

        }






    }

}