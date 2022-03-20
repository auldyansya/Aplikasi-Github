package com.auldy.github3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import com.auldy.github3.alarm.AlarmReceiver
import com.auldy.github3.alarm.Reminder
import com.auldy.github3.alarm.ReminderPreference
import com.auldy.github3.databinding.ActivitySettingBinding

/**
 * Created By Auldy on 15/04/2021.
 *
 */
class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private lateinit var reminder: Reminder
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val reminderPreference = ReminderPreference(this)
        binding.reminder.isChecked = reminderPreference.getReminder().isReminded

        alarmReceiver = AlarmReceiver()
        binding.reminder.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                saveReminder(true)
                alarmReceiver.setRepeatingAlarm(
                    this,
                    "RepeatingAlarm",
                    "09:00",
                    "Github Search Reminder"
                )
            } else {
                saveReminder(false)
                alarmReceiver.cancelAlarm(this)
            }
        }

        binding.btnAbout.setOnClickListener{
            val moveabout = Intent(this@SettingActivity, AboutActivity::class.java)
            startActivity(moveabout)
        }
        binding.btnLanguage.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
    }


    private fun saveReminder(state: Boolean) {
        val remind = ReminderPreference(this)
        reminder = Reminder()
        reminder.isReminded = state
        remind.setReminder(reminder)
    }
}