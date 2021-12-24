package com.salihutimothy.gestureflashlight

import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder
import android.os.Vibrator

class GestureDetectionService() : Service(), SensorEventListener {

    private val MIN_TIME_BETWEEN_SHAKE = 1000
    private lateinit var sensorManager: SensorManager
    private lateinit var vibrator: Vibrator
    var lastShakeTime = 0L
    var isFlashLightOn = false
//    val shakeThreshold

    override fun onCreate() {
        super.onCreate()
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        if (sensorManager != null){
//            accelerometer = sensorManager.getDefaultSensor()
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onSensorChanged(event: SensorEvent?) {
        TODO("Not yet implemented")
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("Not yet implemented")
    }
}