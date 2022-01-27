package com.salihutimothy.gestureflashlight

import android.content.Intent
import android.hardware.camera2.CameraAccessException
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.salihutimothy.gestureflashlight.GestureDetectionService.Companion.isFlashLightOn
import soup.neumorphism.NeumorphFloatingActionButton

class MainActivity : AppCompatActivity(), View.OnTouchListener, View.OnClickListener {

    var isSwitchedOn = false
    private lateinit var button: Button
    private lateinit var utility : Util
    private lateinit var buttonTorch : NeumorphFloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)
        buttonTorch = findViewById(R.id.button_Torch)
        utility = Util (this)

        val intent = Intent(this, GestureDetectionService::class.java)
//        startService(intent)

        buttonTorch.setOnTouchListener(this)
//        buttonTorch.setOnClickListener (this)

//        buttonTorch.setOnTouchListener(object : View.OnTouchListener {
//        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//            when (event?.action) {
//                MotionEvent.ACTION_DOWN ->
//                    v?.performClick()
//
//                MotionEvent.ACTION_UP ->
//            }
//
//            return v?.onTouchEvent(event) ?: true
//        }
//
//    })

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

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        when (view) {
            buttonTorch -> {
                when (motionEvent.action){
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
                            } catch (e : CameraAccessException) {
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

    override fun onClick(v: View?) {
        when (v) {
            buttonTorch -> {

            }
        }
    }
}