package com.salihutimothy.gestureflashlight

import android.annotation.SuppressLint
import android.content.Intent
import android.hardware.camera2.CameraAccessException
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import soup.neumorphism.NeumorphFloatingActionButton


class MainActivity : AppCompatActivity(), View.OnTouchListener {

    private lateinit var utility: Util
    private lateinit var buttonTorch: NeumorphFloatingActionButton
    private lateinit var buttonVibrate: NeumorphFloatingActionButton

    companion object {
        var isGestureOn = false
        var isFlashLightOn = false
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        buttonTorch = findViewById(R.id.button_torch)
        buttonVibrate = findViewById(R.id.button_vibrate)
        utility = Util(this)

        val intent = Intent(applicationContext, GestureDetectionService::class.java)
        startService(intent)

        buttonTorch.setOnTouchListener(this)

        buttonVibrate.setOnClickListener {
            if (!isGestureOn) {
                isGestureOn = true
                buttonVibrate.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
                buttonVibrate.setImageResource(R.drawable.ic_vibrate_on)
            } else {
                isGestureOn = false
                buttonVibrate.setBackgroundColor(ContextCompat.getColor(this, R.color.app_bg))
                buttonVibrate.setImageResource(R.drawable.ic_vibrate_off)
            }
        }

    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        when (view) {
            buttonTorch -> {
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {
                        buttonTorch.setImageResource(R.drawable.ic_power)
                        buttonTorch.setShapeType(1)
                    }
                    MotionEvent.ACTION_UP -> {
                        buttonTorch.setShapeType(0)
                        if (!isFlashLightOn) {
                            try {
                                isFlashLightOn = utility.torchToggle("on")
                                buttonTorch.setImageResource(R.drawable.ic_power_on)
                            } catch (e: CameraAccessException) {
                                e.printStackTrace()
                            }
                        } else {
                            isFlashLightOn = utility.torchToggle("off")
                            buttonTorch.setImageResource(R.drawable.ic_power_off)
                        }
                        buttonTorch.performClick()
                    }
                }
            }
        }
        return true
    }
}