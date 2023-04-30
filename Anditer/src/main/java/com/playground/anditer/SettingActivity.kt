package com.playground.anditer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.playground.anditer.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "설정"

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_setting_view, SelectLockFragment())
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId
        when(id) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}