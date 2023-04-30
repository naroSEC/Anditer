package com.playground.anditer

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class LockPinFragment : PreferenceFragmentCompat() {
    var lock = true
    lateinit var mContext: Context

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.lock_pin, rootKey)
        init()
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        return when (preference.key) {
            "enabled_pwd" -> {
                val intent: Intent = Intent(mContext, AppPassWordActivity::class.java).apply {
                    putExtra(AppLockConst.type, AppLockConst.ENABLE_PASSLOCK)
                }
                enabledPwd.launch(intent)
                true
            }
            "disabled_pwd" -> {
                val intent: Intent = Intent(mContext, AppPassWordActivity::class.java).apply {
                    putExtra(AppLockConst.type, AppLockConst.DISABLE_PASSLOCK)
                }
                disabledPwd.launch(intent)
                true
            }
            "changed_pwd" -> {
                val intent: Intent = Intent(mContext, AppPassWordActivity::class.java).apply {
                    putExtra(AppLockConst.type, AppLockConst.CHANGE_PASSWORD)
                }
                chagnedPwd.launch(intent)
                true
            }
            "locked_pwd" -> {
                if(lock && AppLock(mContext).isPassLockSet()){
                    val intent = Intent(mContext, AppPassWordActivity::class.java).apply {
                        putExtra(AppLockConst.type, AppLockConst.UNLOCK_PASSWORD)
                    }
                    unlockPwd.launch(intent)
                }
                true
            }
            else -> {
                super.onPreferenceTreeClick(preference)
            }
        }
    }

    val enabledPwd =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                RESULT_OK -> {
                    Toast.makeText(mContext, "비밀번호가 설정되었습니다.", Toast.LENGTH_SHORT).show()
                    init()
                    lock = true
                }
            }
        }
    val disabledPwd =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                RESULT_OK -> {
                    Toast.makeText(mContext, "비밀번호가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                    init()
                }
            }
        }
    val chagnedPwd =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                RESULT_OK -> {
                    Toast.makeText(mContext, "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show()
                    lock = true
                }
            }
        }
    val unlockPwd =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                RESULT_OK -> {
                    Toast.makeText(mContext, "잠금이 해제되었습니다.", Toast.LENGTH_SHORT).show()
                    lock = false
                }
            }
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    private fun init(){
        val enabled_pwd: Preference? = findPreference("enabled_pwd")
        val disabled_pwd: Preference? = findPreference("disabled_pwd")
        val changed_pwd: Preference? = findPreference("changed_pwd")
        val locked_pwd: Preference? = findPreference("locked_pwd")

        if (AppLock(mContext).isPassLockSet()){
            enabled_pwd?.isEnabled = false
            disabled_pwd?.isEnabled = true
            changed_pwd?.isEnabled = true
            locked_pwd?.isEnabled = true

            enabled_pwd?.summary = "비밀번호가 설정되어 있습니다."

            lock = true
        }
        else{
            enabled_pwd?.isEnabled = true
            disabled_pwd?.isEnabled = false
            changed_pwd?.isEnabled = false
            locked_pwd?.isEnabled = false

            enabled_pwd?.summary = "비밀번호를 설정해주세요."

            lock = false
        }
    }
}