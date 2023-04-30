package com.playground.anditer

import android.content.Context

class AppLock(context: Context) {

    private var sharedPref = context.getSharedPreferences("appLock", Context.MODE_PRIVATE)

    fun setPassLock(password : String){
        sharedPref.edit().apply{
            putString("applock", password)
            apply()
        }
    }

    fun removePassLock(){
        sharedPref.edit().apply{
            remove("applock")
            apply()
        }
    }

    fun checkPassLock(password: String): Boolean {
        return sharedPref.getString("applock","0") == password
    }

    fun isPassLockSet(): Boolean {
        if(sharedPref.contains("applock")){
            return true
        }
        return false
    }
}