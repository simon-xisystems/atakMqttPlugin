package com.atakmap.android.plugintemplate.mqtt

class movementRecord(
        val bearing: Float,
        val bearingAccuracy: Float,
        val speed: Float,
        val speedAccuracy: Float,
        val inclination: Float,
        val inclinationAccuracy: Float

) {

    override fun toString(): String {
        return "movementRecord [" +
                "bearing:${this.bearing}," +
                "bearingAccuracy: ${this.bearingAccuracy}," +
                "speed: ${this.speed}," +
                "speedAccuracy: ${this.speedAccuracy}," +
                "inclination: ${this.inclination}," +
                "inclinationAccuracy: ${this.inclinationAccuracy}]"



    }
}