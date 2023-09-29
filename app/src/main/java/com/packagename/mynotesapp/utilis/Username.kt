package com.packagename.mynotesapp.utilis

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Username @Inject constructor(@ApplicationContext context: Context) {
    private var pref = context.getSharedPreferences(Constants.PREFS_TOKEN_FILE, Context.MODE_PRIVATE)

    fun saveUsername(Username:String){
        val editor = pref.edit()
        editor.putString(Constants.USER_NAME,Username)
        editor.apply()
    }

    fun getUsername(): String?{
        return pref.getString(Constants.USER_NAME,null)
    }
}