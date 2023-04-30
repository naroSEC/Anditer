package com.playground.anditer

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import dalvik.system.DexClassLoader
import java.io.*
import java.lang.Byte

class DynamicDetector(context: Context): AppCompatActivity() {

    val mContext = context

    fun isCheckDynamic(delete: Boolean) : Boolean {
        val delCheck: Boolean = delete
        val dexDir = mContext.codeCacheDir
        val dexFileName = "dynamic"
        val dexFile = File(dexDir.absolutePath, dexFileName)
        var isCheckResult = false

        try {
            val bufferedInput = BufferedInputStream(mContext.resources.assets.open(dexFileName))
            val bufferedOutput = BufferedOutputStream(FileOutputStream(dexFile))
            val bufferSize = ByteArray(1024)

            while(true) {
                val length = bufferedInput.read(bufferSize)
                if (length <= 0) {
                    break
                }
                bufferedOutput.write(bufferSize, 0, length)
            }
            bufferedOutput.close()
            bufferedInput.close()

            val dexClassLoader = DexClassLoader(dexFile.absolutePath, dexDir.absolutePath, null, mContext.classLoader)
            val loadClass = dexClassLoader.loadClass("com.playground.anditer.Dynamic")
            val instance = loadClass.newInstance()
            val getMethod = loadClass.getMethod("getResult")
            val result = getMethod.invoke(instance) as Boolean

            if (result) {
                isCheckResult = true
            }
            if (delCheck) {
                deleteFile(dexFile)
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return isCheckResult
    }

    private fun deleteFile(dexFile: File) {
        if (dexFile.exists())
            dexFile.delete()
    }
}