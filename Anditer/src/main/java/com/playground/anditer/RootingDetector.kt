package com.playground.anditer

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.FileUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.DataInput
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.lang.Byte
import java.nio.Buffer
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.collections.ArrayList

class RootingDetector(context: Context): AppCompatActivity() {

    val mContext = context
    lateinit var isFile: File
    val rootTools = RootTools()
    var rootingPackages: Array<String> = arrayOf(
        "com.noshufou.android.su",
        "com.noshufou.android.su.elite",
        "eu.chainfire.supersu",
        "com.koushikdutta.superuser",
        "com.thirdparty.superuser",
        "com.yellowes.su",
        "com.topjohnwu.magisk",
        "com.kingroot.kinguser",
        "com.kingo.root",
        "com.smedialink.oneclickroot",
        "com.zhiqupk.root.global",
        "com.alephzain.framaroot",
        "com.koushikdutta.rommanager",
        "com.koushikdutta.rommanager.license",
        "com.dimonvideo.luckypatcher",
        "com.chelpus.lackypatch",
        "com.ramdroid.appquarantine",
        "com.ramdroid.appquarantinepro",
        "com.android.vending.billing.InAppBillingService.COIN",
        "com.android.vending.billing.InAppBillingService.LUCK",
        "com.chelpus.luckypatcher",
        "com.blackmartalpha",
        "org.blackmart.market",
        "com.allinone.free",
        "com.repodroid.app",
        "org.creeplays.hack",
        "com.baseappfull.fwd",
        "com.zmapp",
        "com.dv.marketmod.installer",
        "org.mobilism.android",
        "com.android.wp.net.log",
        "com.android.camera.update",
        "cc.madkite.freedom",
        "com.solohsu.android.edxp.manager",
        "org.meowcat.edxposed.manager",
        "com.xmodgame",
        "com.cih.game_cih",
        "com.charles.lpoqasert",
        "catch_.me_.if_.you_.can_",
        "com.devadvance.rootcloak",
        "com.devadvance.rootcloakplus",
        "de.robv.android.xposed.installer",
        "com.saurik.substrate",
        "com.zachspong.temprootremovejb",
        "com.amphoras.hidemyroot",
        "com.amphoras.hidemyrootadfree",
        "com.formyhm.hiderootPremium",
        "com.formyhm.hideroot",
        "eu.chainfire.supersu.pro",
        "me.phh.superuser",
        "com.android.rooting.apk",
        "com.playground.rooting",
        )
    var rootingPath: Array<String> = arrayOf(
        "/system/",
        "/system/app/",
        "/system/xbin/",
        "/system/etc/",
        "/system/bin/",
        "/system/bin/.ext/",
        "/system/sd/xbin/",
        "/system/usr/we-need-root/",
        "/data/",
        "/data/app/",
        "/data/local/",
        "/data/local/bin/",
        "/data/local/xbin/",
        "/data/app-private/",
        "/sbin/",
        "/su/bin/",
        "/dev/",
        "/cache/",
    )
    var rootingBinaries: Array<String> = arrayOf(
        "su",
        "busybox",
        "magisk",
        "supersu",
        "Superuser.apk",
        "KingoUser.apk",
        "SuperSu.apk",
        "daemonsu"
    )
    val writeAbleFiles = arrayOf(
        "/system",
        "/system/bin",
        "/system/sbin",
        "/system/xbin",
        "/vendor",
        "/vendor/bin",
        "/sbin",
        "/etc",
    )

    fun isCheckRootingInstalled() : Boolean {
        rootingPackages.forEach {
            try {
                var checkPackage = mContext.packageManager.getPackageInfoCompat(it,0)
                return true
            } catch(ex : PackageManager.NameNotFoundException) {
                //
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

    fun isCheckRootingBinary() : Boolean {
        rootingPath.forEach {path ->
            rootingBinaries.forEach {file ->
                isFile = File(path, file)
                if(isFile.exists())
                    return true
            }
        }
        return false
    }

    fun isCheckRootingExec() : Boolean {
        try {
            val command = arrayOf("which", "su")
            val process = Runtime.getRuntime().exec(command)
            val bufferedReader = BufferedReader(InputStreamReader(process.inputStream))
            val bufferString = bufferedReader.readText()
            bufferedReader.close()
            return bufferString != null && bufferString != ""

        } catch (ex : IOException) {
            //ex.printStackTrace()
        }
        return false
    }

    fun isCheckRootingKeys() : Boolean {
        val buildKeys = ArrayList<String>()
        val getSystemProperty = getSystemProperty()

        buildKeys.add(getSystemProperty.prop("ro.build.type").toString())
        buildKeys.add(getSystemProperty.prop("ro.build.keys").toString())
        buildKeys.add(getSystemProperty.prop("ro.build.tags").toString())
        buildKeys.add(getSystemProperty.prop("ro.build.description").toString())
        buildKeys.add(getSystemProperty.prop("ro.build.fingerprint").toString())
        buildKeys.add(getSystemProperty.prop("ro.build.display.id").toString())

        return (buildKeys != null) && (buildKeys.contains("test-keys"))
    }

    fun isCheckRWFile() : Boolean {

        writeAbleFiles.forEach {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val path = Paths.get(it)
                try {
                    val dirPermission= Files.getPosixFilePermissions(path)
                    if (dirPermission.toString().contains("OTHERS_WRITE"))
                        return true

                } catch(ex: Exception) {
                    //ex.printStackTrace()
                }
            } else {
                try {
                    isFile = File(it)
                    val process = Runtime.getRuntime().exec("ls -al ${it}")
                    val bufferedReader = BufferedReader(InputStreamReader(process.inputStream))
                    val bufferString = bufferedReader.readText()
                    if (bufferString.length > 1) {
                        val dirPermission = bufferString.split(" ")[0]
                        if (dirPermission[8].toString() == "w") {
                            return true
                        }
                    }
                } catch(ex: Exception) {
                    //ex.printStackTrace()
                }
            }
        }
        return false
    }

    fun isCheckForProps(): Boolean {
        val getSystemProperty = getSystemProperty()
        val roSeucre = getSystemProperty.prop("ro.secure")
        val roAdbSecure = getSystemProperty.prop("ro.adb.secure")

        return roSeucre.equals("0") || roAdbSecure.equals("0")
    }

    fun isCheckRootingProcesses(): Boolean {

        val isCmdResult = rootTools.isRootCommand("ps -ef")
        if (!isCmdResult.isEmpty()) {
            isCmdResult.forEach {
                if (it.contains("magisk"))
                    return true
            }
        }
        return false
    }
}