package com.atakmap.android.plugintemplate.mqtt

import com.atakmap.coremap.maps.time.CoordinatedTime

class positionRecord(
       var latitudeInDecimalDegrees: Double,
        var longitudeInDecimalDegrees: Double,
        var zuluTimeOfFix: CoordinatedTime,
        var horizontalAccuracyInM: Double,
        var altitudeInM: Double,
        var verticalAccuracyInM: Double
) {
    override fun toString(): String {
        return "positionRecord [" +
                "latitudeInDecimalDegrees: ${this.latitudeInDecimalDegrees}," +
                "longitudeInDecimalDegrees: ${this.longitudeInDecimalDegrees}," +
                "zuluTimeOfFix: ${this.zuluTimeOfFix}," +
                "horizontalAccuracyInM:, ${this.horizontalAccuracyInM}," +
                "altitudeInM: ${this.altitudeInM}," +
                "verticalAccuracyInM: ${this.verticalAccuracyInM}]"


    }


}