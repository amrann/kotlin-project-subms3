package com.example.dicodingsubms3guvm.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.preference.PreferenceFragmentCompat
import com.example.dicodingsubms3guvm.R

class SettingPreferenceFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener, View.OnClickListener {

    private val sharedPref : SharedPreferences
        get() {
            TODO()
        }

    companion object {
        private const val PREFS_NAME = "setting_pref"
        private const val ON_OFF = ""
    }

    override fun onCreatePreferences(bundle: Bundle?, str: String?) {
        addPreferencesFromResource(R.xml.preferences)

    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        TODO("Not yet implemented")
    }
}