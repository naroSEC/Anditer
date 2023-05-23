package com.playground.anditer

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

class NativeDetector(context: Context): AppCompatActivity() {

    fun isCheckRooted(): Boolean {

        return isCheckRooting()
    }

    fun isCheckExecution(): Boolean {

        return isCheckSUExecution()
    }

    fun isCheckDebuggerPtrace(): Boolean {

        return isCheckPtrace()
    }

    fun isCheckDebuggerTracerPID(): Boolean {

        return isCheckTracerPID()
    }

    fun isCheckDebuggerable(): Boolean {

        return isCheckDebuggable()
    }

    fun isCheckFridaFiles(): Boolean {

        return isCheckFridaFile()
    }

    fun isCheckFridaProcess(): Boolean {

        return isCheckFridaPs()
    }

    fun isCheckFridaPorts(): Boolean {

        return isCheckFridaPort()
    }


    external fun isCheckRooting(): Boolean
    external fun isCheckSUExecution(): Boolean
    external fun isCheckPtrace(): Boolean
    external fun isCheckTracerPID(): Boolean
    external fun isCheckDebuggable(): Boolean
    external fun isCheckFridaFile(): Boolean
    external fun isCheckFridaPs(): Boolean
    external fun isCheckFridaPort(): Boolean

    companion object {
        // Used to load the 'nativetestapp' library on application startup.
        init {
            System.loadLibrary("anditer")
        }
    }
}