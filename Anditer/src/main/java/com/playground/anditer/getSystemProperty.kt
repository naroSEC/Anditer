package com.playground.anditer

class getSystemProperty {
    fun prop(key: String?): String? {
        var value: String? = null
        try {
            value = Class.forName("android.os.SystemProperties")
                .getMethod("get", String::class.java).invoke(null, key) as String
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return value
    }
}