package com.atakmap.android.plugintemplate.mqtt

class lffiMessage(
        val title: String  = "LFFI",
        var trackPositionalData: positionRecord,
        var trackMovementData: movementRecord,
        var trackSourceTransponderId: String,
        var trackSourceSystem: String,
        var trackSecurity: String,
        var trackSIDC: String
         )
{

    override fun toString(): String {
        return "Lffi [ " +
                "trackSourceTransponderId: ${this.trackSourceTransponderId}, " +
                "trackSourceSystem: ${this.trackSourceSystem}, " +
                "trackSecurity: ${this.trackSecurity}, " +
                "trackSIDC: ${this.trackSIDC}," +
                "trackPositionData: ${this.trackPositionalData}," +
                "trackMovementData: ${this.trackMovementData}]"
    }

}