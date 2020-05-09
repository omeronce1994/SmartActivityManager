package omeronce.android.smartactivitymanager

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import omeronce.android.smartactivitymanager.ktextensions.onActivityCreated
import omeronce.android.smartactivitymanager.observers.AppStateObserver
import omeronce.android.smartactivitymanager.managers.DefaultActivityManager
import omeronce.android.smartactivitymanager.managers.IActivityManager

object SmartActivityManager {

    private lateinit var activityManager: IActivityManager

    fun init(application: Application, activityManager: IActivityManager = DefaultActivityManager()) {
        this.activityManager = activityManager
        application.onActivityCreated { activityManager.registerActivity(it) }
    }

    fun getAppState() = activityManager.getAppState()

    fun observe(appStateObserver: AppStateObserver) {
        activityManager.observe(appStateObserver)
    }

    fun observe(lifecycleOwner: LifecycleOwner, appStateObserver: AppStateObserver) {
        activityManager.observe(lifecycleOwner, appStateObserver)
    }

    fun removeObserver(appStateObserver: AppStateObserver) {
        activityManager.removeObserver(appStateObserver)
    }

    fun clearObserves() {
        activityManager.clearObservers()
    }
}