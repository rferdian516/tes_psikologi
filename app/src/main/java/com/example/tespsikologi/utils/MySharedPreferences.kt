package com.example.tespsikologi.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.tespsikologi.auth.SignInActivity

class MySharedPreferences(mContext: Context) {

    companion object {
        const val USER_PREF = "USER_PREF"
    }

    private val mSharedPreferences = mContext.getSharedPreferences(USER_PREF, 0)

    fun setValue(key: String, value: String) {
        val editor: SharedPreferences.Editor = mSharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getValue(key: String): String? {
        return mSharedPreferences.getString(key, "")
    }
}