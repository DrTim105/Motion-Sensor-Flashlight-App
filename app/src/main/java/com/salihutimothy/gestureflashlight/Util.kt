package com.salihutimothy.gestureflashlight

import android.content.Context
import android.hardware.camera2.CameraManager
import android.os.Build

class Util(private val context: Context) {

    private var isSwitchedOn = false

    fun torchToggle(command: String): Boolean {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
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
        return isSwitchedOn
    }

}