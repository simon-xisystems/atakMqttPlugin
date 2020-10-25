package com.atakmap.android.plugintemplate;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.atakmap.comms.CommsMapComponent;
import com.atakmap.coremap.cot.event.CotDetail;
import com.atakmap.coremap.cot.event.CotEvent;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;


/*
 *
 * Class to grab out going COT messages so we can tie in LSA conversion to outgoin ATAK events.
 *
 * This is called by on registration, now need to add fucntioality for all handlers for outgoing
 * messages.
 *
 * */

public class OutboundCotMessageHandler implements CommsMapComponent.PreSendProcessor {
    private static final String TAG = OutboundCotMessageHandler.class.getSimpleName();

    private CommsMapComponent commsMapComponent;

    private Context atakContext;



    public OutboundCotMessageHandler(CommsMapComponent commsMapComponent,

                                     Context atakContext
                                     ) {
        this.commsMapComponent = commsMapComponent;

        this.atakContext = atakContext;


        commsMapComponent.registerPreSendProcessor(this);
    }

    public void destroy() {
        this.commsMapComponent.registerPreSendProcessor(null);
    }

    /*
     * So thought about calling a seperate instance of this class for each event type, but only
     * calls last instanciated instance instead, hence the if else
     *
     * */


    @Override
    public void processCotEvent(final CotEvent cotEvent, String[] toUIDs) {
        //Log.d(TAG, "bob: " + cotEvent + " UIDs: " + Arrays.toString(toUIDs));

        CotEvent cotMessage = cotEvent;
        String type = cotMessage.getType();
        String casEvent = "b-r-f-h-c";
        //Log.d(TAG, "bob: " + cotMessage.getType() );

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat;
        timeFormat = new SimpleDateFormat("HH:mm:ss");
        String zuluDate = dateFormat.format(calendar.getTime());
        String zuluTime = timeFormat.format(calendar.getTime());
        Log.d(TAG, "bob: Sending own position report  " );
        uiPassThrough send = uiPassThrough.INSTANCE;
        send.ownLocationReport(cotEvent.toString());



    }


}