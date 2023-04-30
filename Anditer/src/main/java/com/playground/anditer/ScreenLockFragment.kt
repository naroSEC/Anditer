package com.playground.anditer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.playground.anditer.databinding.FragmentScreenLockBinding

class ScreenLockFragment : Fragment() {

    lateinit var mainActivity: Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentScreenLockBinding.inflate(inflater, container, false)

        binding.btnSetting.setOnClickListener {
            val intent: Intent = Intent(mainActivity, SettingActivity::class.java)
            mainActivity.startActivity(intent)
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context
    }
}