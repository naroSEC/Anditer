package com.playground.anditer

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.util.*


class EmulatorDetector(context: Context): AppCompatActivity() {

    val mContext = context
    val architecture = System.getProperty("os.arch");
    lateinit var isFile: File
    val hashMap: HashMap<String, Any> = hashMapOf(
        "init.svc.qemud" to "",
        "init.svc.qemu-props" to "",
        "qemu.hw.mainkeys" to "",
        "qemu.sf.fake_camera" to "",
        "qemu.sf.lcd_density" to "",
        "ro.bootloader" to "unknown",
        "ro.bootmode" to "unknown",
        "ro.hardware" to "qcom",
        "ro.kernel.android.qemud" to "",
        "ro.kernel.qemu.gles" to "",
        "ro.kernel.qemu" to "",
        "ro.product.device" to "generic",
        "ro.product.model" to "sdk",
        "ro.product.name" to "sdk",
        "ro.serialno" to "",
    )
    val genyFiles = arrayOf(
        "/dev/socket/genyd",
        "/dev/socket/baseband_genyd",
    )
    val x86Files = arrayOf(
        "ueventd.android_x86.rc",
        "x86.prop",
        "ueventd.ttVM_x86.rc",
        "init.ttVM_x86.rc",
        "fstab.ttVM_x86",
        "fstab.vbox86",
        "init.vbox86.rc",
        "ueventd.vbox86.rc",
    )
    val andyFiles = arrayOf(
        "fstab.andy",
        "ueventd.andy.rc",
    )
    val noxFiles = arrayOf(
        "fstab.nox",
        "init.nox.rc",
        "ueventd.nox.rc",
        "init.qcom.rc",
    )
    val pipeFiles = arrayOf(
        "/dev/socket/qemud",
        "/dev/qemu_pipe",
    )
    val emulatorPackages = arrayOf(
        "com.google.android.launcher.layouts.genymotion",
        "com.bluestacks",
        "com.bignox.app",
        "com.vphone.launcher",
        "com.microvirt.tools",
        "com.microvirt.download",
        "com.cyanogenmod.filemanager",
        "com.mumu.store",
        "com.android.Calendar",
        "com.android.gallery3d",
    )

    fun isCheckBuildSet(): Boolean {
        val check: Boolean = ((Build.FINGERPRINT.startsWith("generic")
                || architecture.contains("x86")
                || architecture.contains("i686")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.lowercase(Locale.getDefault()).contains("droid4x")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion"))
                || Build.HARDWARE == "goldfish"
                || Build.HARDWARE == "vbox86"
                || Build.HARDWARE.lowercase(Locale.getDefault()).contains("nox")
                || Build.PRODUCT == "sdk"
                || Build.PRODUCT == "google_sdk"
                || Build.PRODUCT == "sdk_x86"
                || Build.PRODUCT == "vbox86p"
                || Build.PRODUCT.lowercase(Locale.getDefault()).contains("windroye")
                || Build.PRODUCT.lowercase(Locale.getDefault()).contains("nox")
                || Build.BRAND.lowercase(Locale.getDefault()).contains("windroy")
                || Build.BOARD.lowercase(Locale.getDefault()).contains("nox")
                || Build.BOOTLOADER.lowercase(Locale.getDefault()).contains("nox")
                || Build.MANUFACTURER.contains("Genymotion"))

        return check
    }

    fun isCheckEmulatorFile(): Boolean {
        val check: Boolean = isFileCheck(genyFiles)
                || isFileCheck(andyFiles)
                || isFileCheck(noxFiles)
                || isFileCheck(pipeFiles)

        return check
    }

    fun isCheckEumlatorPackages(): Boolean {
        emulatorPackages.forEach {
            try {
                var checkPackage = mContext.packageManager.getPackageInfoCompat(it, 0)
                return true
            } catch(ex : PackageManager.NameNotFoundException) {
                //ex.printStackTrace()
            }
        }
        return false
    }

    fun PackageManager.getPackageInfoCompat(packageName: String, flags: Int = 0): PackageInfo =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(flags.toLong()))
        } else {
            @Suppress("DEPRECATION") getPackageInfo(packageName, flags)
        }

    private fun isFileCheck(files: Array<String>): Boolean {
        files.forEach {
            isFile = File(it)
            if (isFile.exists())
               return true
        }

        return false
    }
}