package com.playground.anditer

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream

class RootTools {
    fun isRootCommand(command: String): ArrayList<String> {
        val cmdResult = ArrayList<String>()

        try {
            val process: Process = Runtime.getRuntime().exec("su")
            val stdin: OutputStream = process.outputStream
            val stdout: InputStream = process.inputStream

            stdin.write(("${command}\n").toByteArray())
            stdin.write(("exit\n").toByteArray())

            stdin.flush()
            stdin.close()

            val br = BufferedReader(InputStreamReader(stdout))

            while(true) {
                val line = br.readLine()
                if (line == null)
                    break
                cmdResult.add(line)
            }
            br.close()
            process.waitFor()
            process.destroy()
        } catch (ex: Exception) {
            //
        }

        return cmdResult
    }
}