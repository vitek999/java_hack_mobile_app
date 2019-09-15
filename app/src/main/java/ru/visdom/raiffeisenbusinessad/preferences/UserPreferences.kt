package ru.visdom.raiffeisenbusinessad.preferences

import android.content.Context
import android.content.SharedPreferences
import ru.visdom.raiffeisenbusinessad.network.auth.UserDto

object UserPreferences {
    private lateinit var preferences: SharedPreferences

    private const val PREFERENCES_NAME = "user_preferences"

    private const val PREFERENCES_USER_PHONE = "user_phone"
    private const val PREFERENCES_USER_PASSWORD = "user_password"
    private const val PREFERENCES_USER_ID = "user_id"

    fun init(context: Context) {
        preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun saveUser(userDto: UserDto, userPassword: String) {
        val editor: SharedPreferences.Editor = preferences.edit()
        with(editor) {
            putString(PREFERENCES_USER_PHONE, userDto.login)
            putString(PREFERENCES_USER_PASSWORD, userPassword)
            putLong(PREFERENCES_USER_ID, userDto.id)
            apply()
        }
    }

    fun getPhone() = preferences.getString(PREFERENCES_USER_PHONE, null)

    fun getPassword() = preferences.getString(PREFERENCES_USER_PASSWORD, null)

    fun getId() = preferences.getLong(PREFERENCES_USER_ID, Long.MAX_VALUE)

    fun clear() {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.clear()
        editor.apply()
    }
}