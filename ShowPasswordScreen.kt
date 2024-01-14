package com.my.daily.diary.journal.diarywithlock.di

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.navigation.fragment.findNavController
import com.my.daily.diary.journal.diarywithlock.R
import com.my.daily.diary.journal.diarywithlock.di.app.HiltApplication
import com.my.daily.diary.journal.diarywithlock.utils.MyConstants
import com.my.daily.diary.journal.diarywithlock.utils.SharedPref

fun String.printIt() {
    Log.e("-->", this)
}
fun Context.isShowPasswordScreen(show: Boolean){
    HiltApplication.showPasswordScreen = show
}

class ShowPasswordScreen(var application: Application) : Application.ActivityLifecycleCallbacks {

    private var currentActivity: Activity? = null

    init {
        application.registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onStart(owner: LifecycleOwner) {
                super.onStart(owner)
                val sharedPref = SharedPref(application)
                if (!sharedPref.getBoolean("isInterAdShow", false)) {
                    // Show password screen directly
                    showPasswordScreen(HiltApplication.showPasswordScreen
                    ,sharedPref.getBoolean(MyConstants.SKIP_TOKEN,false))
                }
            }
        })
    }
    private fun showPasswordScreen(show: Boolean,skip: Boolean) {
        // Add code here to navigate or show the password screen
        // If using Navigation component, find the NavController and navigate to the password screen
        if (show && !skip ){
            currentActivity?.let { activity ->
                HiltApplication.handleNavigationOnPasswordShowScreen = true
                val navController = (activity as AppCompatActivity).supportFragmentManager.findFragmentById(R.id.fragmentContainerView)?.
                findNavController()
                navController?.navigate(R.id.setPasswordFragment)
            }
        }
    }
    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {
        currentActivity = activity

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {
        if (currentActivity == activity) {
            currentActivity = null
        }
    }
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }

}
