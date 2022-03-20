package com.auldy.github3.alarm

import android.content.Context
import androidx.core.content.edit

/**
 * Created By Auldy on 15/04/2021.
 *
 */
class ReminderPreference (context: Context) {
    companion object {
        const val PREFS_NAME = "reminder_preference"
        const val REMIND_KEY = "reminded"
    }

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setReminder(value: Reminder) {
        prefs.edit {
            putBoolean(REMIND_KEY, value.isReminded)
            apply()
        }
    }

    fun getReminder(): Reminder {
        val model = Reminder()
        model.isReminded = prefs.getBoolean(REMIND_KEY, false)
        return model
    }
}