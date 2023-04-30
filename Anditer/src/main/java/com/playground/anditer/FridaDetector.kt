package com.playground.anditer

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.InputStreamReader
import java.net.Socket


class FridaDetector(context: Context): AppCompatActivity() {

    val mContext = context
    val IP_ADDRESS = "127.0.0.1"
    lateinit var isFile: File

    fun isCheckFridaBinary(): Boolean {
        try {
            File("/data/local/tmp").walk().maxDepth(2).forEach {
                if(it.name.contains("frida") || it.name.contains("linjector"))
                    return true
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return false
    }

    fun isCheckFridaPort(): Boolean {
        var result = false

        val mainThread = Thread(Runnable {
            for (PORT_NUM in 27000..27500) {
                try {
                    val socket = Socket(IP_ADDRESS, PORT_NUM)
                    if (socket.isConnected) {
                        result = true
                        break
                    }
                 } catch (ex: Exception) {
                    //ex.printStackTrace()
                }
            }
        })

        mainThread.start()
        mainThread.join()
        return result
    }

    fun isCheckFridaModule(): Boolean {

        isFile = File("/proc/self/maps")
        try {
            val bufferedReader = BufferedReader(FileReader(isFile))
            val bufferString = bufferedReader.readLines()
            bufferString.forEach {
                if( it.contains("frida") )
                    return true
            }

        } catch (ex: Exception) {
            //ex.printStackTrace()
        }
        return false
    }

    fun isCheckFridaPipe(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            CustomAlert(mContext).smaliDialog("알림", "안드로이드 9 버전 이하에서 사용 가능합니다.", "Frida")
        } else {
            val command = arrayOf("netstat", "|", "grep frida")
            val process = Runtime.getRuntime().exec(command)
            val bufferedReader = BufferedReader(InputStreamReader(process.inputStream))
            val bufferString = bufferedReader.readText()
            bufferedReader.close()

            if (bufferString != null && bufferString != "") {
                return true
            }

        }
        return false
    }
}