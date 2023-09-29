package com.packagename.mynotesapp.repository



interface PreferenceManager {

     fun saveUsername(username:String)

    fun getUsername() : String
}