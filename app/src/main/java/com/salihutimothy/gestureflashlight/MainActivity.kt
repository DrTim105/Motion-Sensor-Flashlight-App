package com.salihutimothy.gestureflashlight

import android.annotation.SuppressLint
import android.content.Intent
import android.hardware.camera2.CameraAccessException
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.github.angads25.toggle.widget.LabeledSwitch
import com.salihutimothy.gestureflashlight.GestureDetectionService.Companion.isFlashLightOn
import soup.neumorphism.NeumorphFloatingActionButton


class MainActivity : AppCompatActivity(), View.OnTouchListener {

    private lateinit var utility: Util
    private lateinit var buttonTorch: NeumorphFloatingActionButton

    companion object {
        var isGestureOn = false
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonTorch = findViewById(R.id.button_Torch)
        utility = Util(this)

        val intent = Intent(applicationContext, GestureDetectionService::class.java)
        startService(intent)

        buttonTorch.setOnTouchListener(this)

        val labeledSwitch = findViewById<LabeledSwitch>(R.id.shakeDetection)

        isGestureOn = labeledSwitch.isOn

        labeledSwitch.setOnToggledListener { toggleableView, isOn ->
            isGestureOn = isOn

        }
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        when (view) {
            buttonTorch -> {
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {
                        buttonTorch.setImageResource(R.drawable.ic_power3)
                        buttonTorch.setShapeType(1)
                    }
                    MotionEvent.ACTION_UP -> {
                        buttonTorch.setShapeType(0)
                        if (!isFlashLightOn) {
                            try {
                                isFlashLightOn = utility.torchToggle("on")
                                buttonTorch.setImageResource(R.drawable.ic_power)
                            } catch (e: CameraAccessException) {
                                e.printStackTrace()
                            }
                        } else {
                            isFlashLightOn = utility.torchToggle("off")
                            buttonTorch.setImageResource(R.drawable.ic_power2)
                        }
                        buttonTorch.performClick()
                    }
                }
            }
        }
        return true
    }
}