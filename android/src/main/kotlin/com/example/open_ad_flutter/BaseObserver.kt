package com.example.open_ad_flutter

import android.app.Activity
import android.app.Application
import android.os.Bundle

open class BaseObserver(application: Application) :
    Application.ActivityLifecycleCallbacks {

    init {
        // Cannot directly use `this`
        // Issue : Leaking 'this' in constructor of non-final class BaseObserver
        registerActivityLifecycleCallbacks(application)
    }

    protected var currentActivity: Activity? = null

    private fun registerActivityLifecycleCallbacks(application: Application) {
        application.registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityStarted(activity: Activity) {
        currentActivity = activity
    }

    override fun onActivityResumed(activity: Activity) {
        currentActivity = activity
    }

    override fun onActivityDestroyed(activity: Activity) {
        currentActivity = null
    }


    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    companion object {
        internal const val TEST_AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712"
    }
}