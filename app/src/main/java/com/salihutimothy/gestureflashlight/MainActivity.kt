package com.salihutimothy.gestureflashlight

import android.content.Intent
import android.hardware.camera2.CameraAccessException
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.salihutimothy.gestureflashlight.GestureDetectionService.Companion.isFlashLightOn

class MainActivity : AppCompatActivity() {

    var isSwitchedOn = false
    private lateinit var button: Button
    private lateinit var utility : Util


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)
        utility = Util (this)

        val intent = Intent(this, GestureDetectionService::class.java)
        startService(intent)

        button.setOnClickListener {
            if (!isFlashLightOn) {
                try {
                    isFlashLightOn = utility.torchToggle("on")
                    button.text = "Switch Off"
                } catch (e : CameraAccessException) {
                    e.printStackTrace()
                }
            } else {
                isFlashLightOn = utility.torchToggle("off")
                button.text = "Switch On"
            }
        }
    }
}