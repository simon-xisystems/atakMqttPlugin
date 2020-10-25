package com.atakmap.android.plugintemplate

import android.content.Context
import android.os.Handler
import android.util.Log
import com.atakmap.comms.CommsMapComponent
import com.atakmap.comms.CommsMapComponent.PreSendProcessor
import com.atakmap.coremap.cot.event.CotEvent
import java.text.SimpleDateFormat
import java.util.*

/*
 *
 * Class to grab out going COT messages so we can tie in LSA conversion to outgoin ATAK events.
 *
 * This is called by on registration, now need to add fucntioality for all handlers for outgoing
 * messages.
 *
 * */
class CotHandler(private val commsMapComponent: CommsMapComponent,

                 private val atakContext: Context
) : PreSendProcessor {
    fun destroy() {
        commsMapComponent.registerPreSendProcessor(null)
    }

    /*
     * So thought about calling a seperate instance of this class for each event type, but only
     * calls last instanciated instance instead, hence the if else
     *
     * */
    override fun processCotEvent(cotEvent: CotEvent, toUIDs: Array<String>) {
        //Log.d(TAG, "bob: " + cotEvent + " UIDs: " + Arrays.toString(toUIDs));
        println("bob: processing cot event $cotEvent")
        val type = cotEvent.type
        val casEvent = "b-r-f-h-c"
        //Log.d(TAG, "bob: " + cotMessage.getType() );
        val calendar = Calendar.getInstance()
        val dateFormat: SimpleDateFormat
        dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val timeFormat: SimpleDateFormat
        timeFormat = SimpleDateFormat("HH:mm:ss")
        val zuluDate = dateFormat.format(calendar.time)
        val zuluTime = timeFormat.format(calendar.time)
        if (cotEvent.type == "u-d-f") {
            // Log.d(TAG, "bob:  Display Map Drawn Polygon ");
        } else if (cotEvent.type == "u-d-r") {
            // Log.d(TAG, "bob:  Display Map Drawn Square ");
        } else if (cotEvent.type == "u-d-c-c") {
            // Log.d(TAG, "bob:  Display Map Drawn Circle ");
        } else if (cotEvent.type == "u-d-f-m") {
            // Log.d(TAG, "bob:  Display Map freehand ");
        } else if (cotEvent.type == "b-m-r") {
            // Log.d(TAG, "bob:  Display Map Route ");
        } else if (cotEvent.type == "b-t-f") {
            // Log.d(TAG, "bob:  Display Chat Event ");
        } else if (cotEvent.type == casEvent) {
            // Log.d(TAG, "bob:  Display Casualty Request ");

            //Todo{need a Cas Alert Event to trigger this.}
        } else if (cotEvent.type == "a-f-G-U-C") {
            // Log.d(TAG, "bob:  Own Location Report ");

            val send = uiPassThrough
            send.ownLocationReport(cotEvent.toString())
            Log.d(TAG, "bob: Sending own position report  ")
        } else {
            Log.d(TAG, "bob: Sending point item")
        }
    }

    companion object {
        private val TAG = CotHandler::class.java.simpleName
    }

    init {
        commsMapComponent.registerPreSendProcessor(this)
    }
}