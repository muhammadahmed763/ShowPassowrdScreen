package com.my.daily.diary.journal.diarywithlock.utils

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.my.daily.diary.journal.diarywithlock.R
import com.my.daily.diary.journal.diarywithlock.helpers.ThemeModelClass
import com.my.daily.diary.journal.diarywithlock.utils.MyConstants
import java.io.ByteArrayOutputStream

class SharedPref(var context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("CalculatorPrefs", Context.MODE_PRIVATE)

    fun setLangName(v: String) {
        val editor = sharedPreferences.edit()
        editor.putString("langName", v)
        editor.apply()
    }

    fun getLangName(): String? {
        return sharedPreferences.getString("langName", "English")
    }
    // Save Boolean
    fun saveBoolean(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    // Get Boolean
    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    // Save List<String>
    fun saveStringList(key: String, list: List<String>) {
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
    }

    fun getStringList(key: String): List<String> {
        val gson = Gson()
        val json = sharedPreferences.getString(key, null)
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(json, type) ?: listOf()
    }
    fun removeStringList(key: String?){
        val editor=sharedPreferences.edit()
        editor.remove(key)
        editor.apply()
    }


    // Save Int
    fun saveInt(key: String, value: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }
    // Get Int
    fun getString(key: String?): String? {
        return sharedPreferences.getString(key,null)
    }
    fun saveString(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun saveBitmap(key: String, bitmap: Bitmap) {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
        val encodedBitmapString: String = Base64.encodeToString(byteArray, Base64.DEFAULT)

        val editor = sharedPreferences.edit()
        editor.putString(key, encodedBitmapString)
        editor.apply()
    }

    fun getBitmap(key: String): Bitmap? {
        val encodedBitmapString = sharedPreferences.getString(key, null)
        return if (encodedBitmapString != null) {
            val decodedByteArray: ByteArray = Base64.decode(encodedBitmapString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.size)
        } else {
            null
        }
    }

    fun saveTheme(key: String, themeModelClass: ThemeModelClass) {
        val gson = Gson()
        val themeJson = gson.toJson(themeModelClass)

        val editor = sharedPreferences.edit()
        editor.putString(key, themeJson)
        editor.apply()
    }

    fun getTheme(key: String): ThemeModelClass {
        val gson = Gson()
        val themeJson = sharedPreferences.getString(key, null)

        return if (themeJson != null) {
            gson.fromJson(themeJson, ThemeModelClass::class.java)
        } else {
            // If theme is not saved, return a default theme
            ThemeModelClass(
                themeColor = context.resources.getColor(R.color.black),
                themeImageUrl = "",
                themeBitmapImage = null
            )
        }
    }
    // Get Int


}