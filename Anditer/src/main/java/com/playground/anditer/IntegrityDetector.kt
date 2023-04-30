package com.playground.anditer

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION_CODES.P
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import java.security.MessageDigest
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import kotlin.math.sign

class IntegrityDetector(context: Context): AppCompatActivity() {

    val mContext = context
    val appName = "ANDITER"
    val installStore = arrayOf(
        "com.android.vending",
        "com.google.android.feedback",
        "com.skt.skaf.A000Z00040",
        "com.sec.android.app.samsungapps",
        "com.sec.android.easyMover.Agent",
        "com.google.android.packageinstaller",
        "com.samsung.android.mateagent",
    )

    @Suppress ("DEPRECATION")
    fun isCheckHashKey(): Boolean {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val PackageInfo = mContext.packageManager.getPackageInfoCompat(mContext.packageName, PackageManager.GET_SIGNING_CERTIFICATES)
                val signatures = PackageInfo.signingInfo.apkContentsSigners[0]

                if (PackageInfo == null || signatures == null)
                    return true

                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signatures.toByteArray())
                val signatureString = Base64.encodeToString(md.digest(), Base64.DEFAULT).trim()

                if (signatureString != "7nd5QagBehUoHzVC+c43zic+/ro=") {
                    return true
                }

            } else {
                val PackageInfo = mContext.packageManager.getPackageInfo(mContext.packageName, PackageManager.GET_SIGNATURES)
                val signatures = PackageInfo.signatures[0]

                if (PackageInfo == null || signatures == null)
                    return true

                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signatures.toByteArray())
                val signatureString = Base64.encodeToString(md.digest(), Base64.DEFAULT).trim()

                if (signatureString != "7nd5QagBehUoHzVC+c43zic+/ro=") {
                    return true
                }
            }
        } catch (ex: Exception) {
            //ex.printStackTrace()
        }

        return false
    }

    fun PackageManager.getPackageInfoCompat(packageName: String, flags: Int = 0): PackageInfo =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(flags.toLong()))
        } else {
            @Suppress ("DEPRECATION") getPackageInfo(packageName, flags)
        }

    fun isCheckInstaller(): Boolean {
        var installer: String?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            installer = mContext.packageManager.getInstallSourceInfo(mContext.packageName).installingPackageName

        } else {
            @Suppress("DEPRECATION")
            installer = mContext.packageManager.getInstallerPackageName(mContext.packageName)
        }

        installStore.forEach {
            if (it == installer)
                return false
        }
        return true
    }

    fun isCheckAppName(): Boolean {
        if (mContext.getString(R.string.app_name) != appName) {
            return true
        }

        return false
    }

    fun isCheckCRC(): Boolean {
        val entry = ZipFile(mContext.packageCodePath).getEntry("classes.dex")
        val classesCrc = entry.crc.toString()
        val compareCrc = mContext.getString(R.string.CRC_Check_Code)
        Log.d("CRC Check", "${classesCrc}")

        if (classesCrc != compareCrc)
            return true

        return false
    }
}