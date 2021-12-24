package com.salihutimothy.gestureflashlight

import android.content.Context
import android.graphics.Camera
import android.hardware.camera2.CameraManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    var isSwitchedOn = false
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)

        button.setOnClickListener {
            if (button.text.toString() == "Switch On") {
                button.text = "Switch Off"
                toggle("on")
            } else {
                button.text = "Switch On"
                toggle("off")
            }
        }

    }

    private fun toggle(command: String) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager

            val cameraId: String? = cameraManager.cameraIdList[0]

            if (cameraId != null) {
                isSwitchedOn = if (command == "on") {
                    cameraManager.setTorchMode(cameraId, true)
                    true
                } else {
                    cameraManager.setTorchMode(cameraId, false)
                    false
                }
            }

        }
    }
}