package com.example.fooddonationapp.utils

import android.content.Context
import android.content.SharedPreferences

object PrefManager {
    private lateinit var prefs: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private const val PREFS_NAME = "params"
    const val IS_LOGIN = "IS_LOGIN"
    const val ACCESS_TOKEN = "ACCESS_TOKEN"
    const val FIRE_BASE_TOKEN="FIRE_BASE_TOKEN"
    const val DONT_SHOW_CAP_LIMIT_POPUP = "SHOW_CAP_LIMIT_POPUP"
    const val LOGIN_NGO ="LOGIN_NGO"
    const val LOGIN_DONAR="LOGIN_DONAR"

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        editor = prefs.edit()
    }

    fun setString(key: String?, value: String?) {
        editor.putString(key, value)
        editor.commit()
    }

    fun setStringSet(key: String?, value: MutableSet<String>?) {
        editor.putStringSet(key, value)
        editor.commit()
    }

    fun getString(key: String?, default: String = ""): String {
        return prefs.getString(key, default) ?: ""
    }

    fun getStringSet(key: String?, default: MutableSet<String>): MutableSet<String> {
        return prefs.getStringSet(key, default) ?: default
    }

    fun setBoolean(key: String?, value: Boolean) {
        editor.putBoolean(key, value)
        editor.commit()
    }

    fun getBoolean(key: String?, default: Boolean = false): Boolean {
        return prefs.getBoolean(key, default)
    }

    fun removeValue(key: String?) {
        with(editor) {
            remove(key)
            commit()
        }
    }

//    fun saveHashMap(key: String?, obj: Any?) {
//        val editor = prefs.edit()
//        val gson = Gson()
//        val json = gson.toJson(obj)
//        editor.putString(key, json)
//        editor.apply() // This line is IMPORTANT !!!
//    }
//
//    fun getHashMap(key: String?): HashMap<String?, String?>? {
//        val gson = Gson()
//        val json = prefs.getString(key, "")
//        val type = object : TypeToken<HashMap<String?, String?>?>() {}.type
//        return gson.fromJson(json, type)
//    }

    fun clearAll() {
        with(editor) {
            clear()
            apply()
        }
    }

    fun clearExcept(keyList: List<String?>) {
        val keys: Map<String?, *> = prefs.all
        for ((key) in keys) {
            if (!keyList.contains(key)) {
                removeValue(key)
            }
        }
    }
}