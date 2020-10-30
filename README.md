# MQTT ATAK Plugin Example

This is a demo plugin for ATAK showing MQTT functionality witin the plugin.

The plugin starts the paho MQTT service through the Plugin Lifecycle class and then sets up the client connection once the plugin ui has been opened. Once the UI has opened the plugin will publish any COT event over MQTT to the public Hive MQTT Broker under the testtopic/atak topic. 

## Disclaimer!
I spent a good amount of time developing in a Android AVD device running ATAK On the x86_64 API 26 image. Everything works fine in this build and on that OS. However I migrated an intall to a Samsung S9 Tactical Edition it it fails to start the MQTT service in the background. It appears that this may be related to a change in background service handling within Android, for which the Paho MQTT client hasnt been updated, there are several comments surrounding this. If anyone manages to fix this please push a change and let me know!

