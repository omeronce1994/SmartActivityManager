package omeronce.android.activitymanager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_second.*
import omeronce.android.smartactivitymanager.SmartActivityManager
import omeronce.android.smartactivitymanager.enums.AppState
import omeronce.android.smartactivitymanager.ktextensions.AppStateObserver
import omeronce.android.smartactivitymanager.observers.AppStateObserver
import omeronce.android.smartactivitymanager.observers.WeakRefAppStateObserver

class SecondActivity: AppCompatActivity() {

    companion object {
        @JvmStatic
        fun startActivity(context: Context) {
            val intent = Intent(context, SecondActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        btn_finish.setOnClickListener { startActivity(this) }
        SmartActivityManager.observe(this, AppStateObserver { Log.i("SecondActivity", "app state: $it") })
        SmartActivityManager.observe(AppStateObserver { Log.i("SecondActivity", "weakobs app state: $it") })
        observeNative()
    }

    fun observeNative() {
        val mutableLiveData = MutableLiveData<String>()
        mutableLiveData.observe(this, Observer { btn_finish.text = it })
        HelloWorldNative().observeNative(mutableLiveData)
    }
}