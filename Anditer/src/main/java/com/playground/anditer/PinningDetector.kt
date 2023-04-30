package com.playground.anditer

import android.content.Context
import android.provider.Settings.Global
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import okhttp3.*
import java.io.IOException
import java.util.concurrent.CountDownLatch

class PinningDetector(context: Context): AppCompatActivity() {

    private val request = Request.Builder().url("https://igloo.co.kr").build()

    fun isCheckRootCA(): Boolean {
        val count = CountDownLatch(1)
        var resResult = false

        val sendRequestJob = GlobalScope.launch {
            try {
                OkHttpClient().newCall(request).enqueue(object: Callback{
                    override fun onFailure(call: Call, e: IOException) {
                        resResult = true
                        count.countDown()
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val responseBody = response.body?.string()
                        count.countDown()
                    }
                })
            } catch (ex : Exception) {
                //
            }
        }
        runBlocking {
            sendRequestJob.join()
        }
        try {
            count.await()
        } catch (ex: Exception) {
            //
        }

        return resResult
    }

    fun isCheckAllowCA(): Boolean {
        val count  = CountDownLatch(1)
        val hostName = "igloo.co.kr"
        val hashValue = "sha256/+hIxKEB6NLsH9zGi9fx81iY3nbFfuipoNjz1liPufEI="
        val certificatePinner = CertificatePinner.Builder().add(hostName, hashValue).build()
        val client = OkHttpClient.Builder().certificatePinner(certificatePinner).build()
        var resResult = false

        val sendRequestJob = GlobalScope.launch {
            try {
                client.newCall(request).enqueue(object: Callback{
                    override fun onFailure(call: Call, e: IOException) {
                        resResult = true
                        count.countDown()
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val responseBody = response.body?.string()
                        count.countDown()
                    }
                })
            } catch (ex: Exception) {
                //
            }
        }
        runBlocking {
            sendRequestJob.join()
        }
        try {
            count.await()
        } catch (ex: Exception) {
            //
        }
        return resResult
    }
}