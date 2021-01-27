package com.example.dicodingsubms3guvm

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.dicodingsubms3guvm.fragment.SettingPreferenceFragment
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    private lateinit var sharedPref: SharedPreferences
    private lateinit var alarmReceiver: AlarmReceiver

    companion object {
        private const val PREFS_NAME = "setting_pref"
        private const val ON_OFF = "on_off"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

//        supportFragmentManager.beginTransaction().add(R.id.setting_holder, SettingPreferenceFragment()).commit()

        alarmReceiver = AlarmReceiver()
        sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        idSwitch.isChecked = sharedPref.getBoolean(ON_OFF, false)
        idSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                editor.putBoolean(ON_OFF, true)
                alarmReceiver.setTimeAlarm(this, AlarmReceiver.TYPE_TIME, "Alarm")
//                Toast.makeText(this, "Push Notification ON", Toast.LENGTH_SHORT).show()

            } else {
                editor.putBoolean(ON_OFF, false)
                alarmReceiver.cancelAlarm(this)
//                Toast.makeText(this, "Push Notification Off", Toast.LENGTH_SHORT).show()
            }
            editor.apply()
        }

    }
}