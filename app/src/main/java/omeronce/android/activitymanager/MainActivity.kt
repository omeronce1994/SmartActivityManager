package omeronce.android.activitymanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_next_activity.setOnClickListener { SecondActivity.startActivity(this) }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("onDestroy", "MainActivity")
    }
}
