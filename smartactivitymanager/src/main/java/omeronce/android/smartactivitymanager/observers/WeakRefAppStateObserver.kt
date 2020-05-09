package omeronce.android.smartactivitymanager.observers

import omeronce.android.smartactivitymanager.enums.AppState
import java.lang.ref.WeakReference

/**
 * AppStateObservers which holds weak ref to function instead holding weak ref to AppStateObserver (in case AppStateObserver weak ref would have been held
 * then the weak ref would have been garbage collected when created anonymously since the reference to Anonymous classes is not consider to be a strong ref)
 *
 * @constructor
 *
 *
 * @param onChanged - invoke function object
 */
class WeakRefAppStateObserver(onChanged: (AppState) -> Unit): WeakReference<(AppState) -> Unit>(onChanged), AppStateObserver {

    override fun onChanged(appState: AppState) {
        get()?.invoke(appState)
    }
}