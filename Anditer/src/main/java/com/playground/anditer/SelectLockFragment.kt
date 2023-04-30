package com.playground.anditer

import android.os.Bundle
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat


class SelectLockFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.select_lock, rootKey)
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        return when (preference.key) {
            "pattern" -> {
                Toast.makeText(requireContext(), "미구현", Toast.LENGTH_LONG).show()
                true
            }
            "fingerprint" -> {
                Toast.makeText(requireContext(), "미구현", Toast.LENGTH_LONG).show()
                true
            }
            else -> {
                super.onPreferenceTreeClick(preference)
            }
        }
    }
}