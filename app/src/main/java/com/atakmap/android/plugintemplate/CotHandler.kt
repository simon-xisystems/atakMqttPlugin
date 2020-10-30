package com.atakmap.android.plugintemplate

import android.content.Context
import com.atakmap.comms.CommsMapComponent
import com.atakmap.coremap.cot.event.CotEvent


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


        println("MQTT: COT Event Processed")

        val casEvent = "b-r-f-h-c"
        val cotMessage = cotEvent!!
        val type = cotMessage.type

        //For the demo we just send the entire COT event
        uiPassThrough.ownLocationReport(cotEvent.toString())

        //This is a method to seperate out differnet COT events, could be used to generate different topics.

        when{

            cotMessage.type =="u-d-f" -> println("MQTT: Display Map Drawn Polygon ")
            cotMessage.type == "u-d-r" -> println("MQTT: Display Map Drawn Square  ")
            cotMessage.type == "u-d-c-c" -> println("MQTT: Display Map Drawn Circle ")
            cotMessage.type == "u-d-f-m" -> println("MQTT:  Display Map freehand  ")
            cotMessage.type == "b-m-r" -> println("MQTT: Display Map Route  ")
            cotMessage.type == "b-t-f" -> println("MQTT:  Display Chat Event ")
            cotMessage.type == "b-r-f-h-c" ->  println("MQTT:  Display Casualty Request ")
            cotMessage.type == "a-f-G-U-C" -> println("MQTT: own position report")


            else -> println("MQTT:  point item ")

        }






    }

}