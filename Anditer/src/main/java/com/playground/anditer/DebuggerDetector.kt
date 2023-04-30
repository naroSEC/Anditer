package com.playground.anditer

import android.content.Context
import android.content.IntentFilter
import android.os.Build
import android.provider.Settings
import android.system.Os.getppid
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class DebuggerDetector(context: Context): AppCompatActivity() {

    lateinit var isFile: File
    val context = context

    fun isCheckDebuggerState() : Boolean {
        isFile = File("/proc/self/status")
        if (isFile.exists()) {
            try {
                val bufferedReader = BufferedReader(FileReader(isFile))
                val bufferString = bufferedReader.readLines()
                bufferedReader.close()

                bufferString.forEach {
                    if (it.contains("TracerPid"))
                        return it.split(":")[1].trim().toInt() != 0

                    if (it.contains("State")) {
                        val pattern = Regex("^t.+")
                        val stateProp = it.split(":")[1].trim()

                        return pattern.matches(stateProp)
                    }
                }
            } catch (ex: Exception) {
                //ex.printStackTrace()
            }
        }
        return false
    }

    fun isCheckDebuggable(): Boolean {
        val getSystemProperty = getSystemProperty()
        val roDebuggable = getSystemProperty.prop("ro.debuggable")

        return roDebuggable.equals("1")
    }

    fun isCheckCallPPID(): Boolean {
        isFile = File("/proc/self/maps")
        if (isFile.exists()) {
            try {
                val bufferedReader = BufferedReader(FileReader(isFile))
                val bufferString = bufferedReader.readLines()
                bufferedReader.close()

                bufferString.forEach {
                    if(it.contains("libopenjdkjvmti.so"))
                        return true
                }

            } catch (ex: Exception) {
                //ex.printStackTrace()
            }
        }
        return false
    }

    fun isCheckDevelopMode(): Boolean {
        return Settings.Global.getInt(
            context.contentResolver,
            Settings.Global.DEVELOPMENT_SETTINGS_ENABLED,
            0
        ) != 0
    }

    fun isCheckUSBDebuggingMode(): Boolean {
        return Settings.Global.getInt(context.getContentResolver(), Settings.Global.ADB_ENABLED, 0) != 0;
    }

    fun isCheckConnectUSB(): Boolean {
        val intent = context.registerReceiver(null, IntentFilter("android.hardware.usb.action.USB_STATE"))
        return intent != null && intent.extras != null && intent.getBooleanExtra(
            "connected",
            false
        )
    }
}