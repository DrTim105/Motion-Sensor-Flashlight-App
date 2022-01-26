package com.salihutimothy.gestureflashlight

import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.camera2.CameraAccessException
import android.os.IBinder
import android.os.Vibrator
import kotlin.math.pow
import kotlin.math.sqrt

class GestureDetectionService() : Service(), SensorEventListener {

    private val MIN_TIME_BETWEEN_SHAKE = 300
    private lateinit var sensorManager: SensorManager
    private lateinit var vibrator: Vibrator
    var lastShakeTime = 0L
    private val shakeThreshold = 250.0f
    private lateinit var utility : Util

    companion object {
        var isFlashLightOn = false
    }

    override fun onCreate() {
        super.onCreate()
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        utility = Util (this)

        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST)


    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                val currentTime = System.currentTimeMillis()

                if ((currentTime - lastShakeTime) > MIN_TIME_BETWEEN_SHAKE){
                    val x = event.values?.get(0)!!.toDouble()
                    val y = event.values?.get(1)!!.toDouble()
                    val z = event.values?.get(2)!!.toDouble()

                    val acceleration = sqrt(x.pow(2.0)) +
                            y.pow(2.0) + z.pow(2.0) - SensorManager.GRAVITY_EARTH

                    if (acceleration > shakeThreshold) {
                        lastShakeTime = currentTime
                        if (!isFlashLightOn) {
                            try {
                                isFlashLightOn = utility.torchToggle("on")
                            } catch (e : CameraAccessException) {
                                e.printStackTrace()
                            }
                        } else {
                            isFlashLightOn = utility.torchToggle("off")
                        }
                    }
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onTaskRemoved(rootIntent: Intent?) {

        val intent = Intent(applicationContext, GestureDetectionService::class.java)
        startService(intent)
        super.onTaskRemoved(rootIntent)
    }

}