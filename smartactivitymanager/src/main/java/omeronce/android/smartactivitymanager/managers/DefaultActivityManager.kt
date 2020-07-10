package omeronce.android.smartactivitymanager.managers

import android.app.Activity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import omeronce.android.smartactivitymanager.enums.AppState
import omeronce.android.smartactivitymanager.observers.AppStateObserver
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.lang.Exception
import java.lang.IllegalStateException
import java.lang.ref.WeakReference
import java.util.*
import java.util.concurrent.ConcurrentSkipListSet
import kotlin.collections.HashSet

internal class DefaultActivityManager: IActivityManager, AnkoLogger {

    private val activitySet: MutableSet<LifecycleOwner> = HashSet()
    private val observersSet: MutableSet<AppStateObserver> = Collections.newSetFromMap(WeakHashMap<AppStateObserver, Boolean>())
    private val bitAppStateManager = BitAppStateManager()
    private var appState = AppState.KILLED

    override fun registerActivity(activity: Activity) {
        if (activity !is LifecycleOwner) {
            //TODO: use this solution to avoid crash on instrumentation tests, find better solution
            try {
                throw IllegalStateException("${activity.javaClass.simpleName} is not a LifeCycleOwner." +
                        "SmartActivityManager does not support activities that does not implement LifecycleOwner interface")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        else {
            val lifecycleOwner = activity as LifecycleOwner
            lifecycleOwner.lifecycle.addObserver(ActivityLifeCycleObserver(lifecycleOwner))
            activitySet.add(activity as LifecycleOwner)
            info { "${activity.javaClass.canonicalName} registered" }
        }
    }

    override fun getAppState(): AppState {
        return appState
    }

    override fun observe(appStateObserver: AppStateObserver) {
        observersSet.add(appStateObserver)
    }

    override fun observe(lifecycleOwner: LifecycleOwner, appStateObserver: AppStateObserver) {
        lifecycleOwner.lifecycle.addObserver(LifeCycleAwareAppStateObserver(appStateObserver))
        observersSet.add(appStateObserver)
    }

    override fun removeObserver(appStateObserver: AppStateObserver) {
        observersSet.remove(appStateObserver)
    }

    override fun clearObservers() {
        observersSet.clear()
    }

    private fun checkState() {
        val currentState = calcCurrentAppState()
        info { "app state: $appState current state: $currentState" }
        if (appState != currentState) {
            appState  = currentState
            notifyObservers()
        }
    }

    private fun calcCurrentAppState(): AppState {
        if (activitySet.isEmpty()) {
            return AppState.KILLED
        }
        if (bitAppStateManager.getState() == BitAppStateManager.STATE_BACKGROUND) {
            return AppState.BACKGROUND
        }
        return AppState.FOREGROUND
    }

    private fun notifyObservers() {
        observersSet.forEach { it.onChanged(appState) }
    }

    inner class ActivityLifeCycleObserver(private val lifecycleOwner: LifecycleOwner): LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun onForeground() {
            bitAppStateManager.onActivityComesToForeground()
            checkState()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun onBackground() {
            bitAppStateManager.onActivityMovedToBackground()
            checkState()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun release() {
            activitySet.remove(lifecycleOwner)
            checkState()
        }
    }

    inner class LifeCycleAwareAppStateObserver(private val appStateObserver: AppStateObserver): LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun release() {
            observersSet.remove(appStateObserver)
        }
    }
}