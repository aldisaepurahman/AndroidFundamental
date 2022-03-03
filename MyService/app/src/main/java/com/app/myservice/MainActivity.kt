package com.app.myservice

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.app.myservice.MyBoundService.*

class MainActivity : AppCompatActivity() {
    private var mServiceBound = false
    private lateinit var mBoundService: MyBoundService

    /*metode koneksi ke service bound*/
    private val mServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName) {
            mServiceBound = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val myBinder = service as MyBinder
            mBoundService = myBinder.getService
            mServiceBound = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStartService = findViewById<Button>(R.id.btn_start_service)
        btnStartService.setOnClickListener {
            /*menjalankan service dengan menggunakan intent*/
            val mStartServiceIntent = Intent(this, MyService::class.java)
            startService(mStartServiceIntent)
        }

        val btnStartJobIntentService = findViewById<Button>(R.id.btn_start_job_intent_service)
        btnStartJobIntentService.setOnClickListener {
            /*menjalankan service yang bisa mengirimkan nilai*/
            val mStartIntentService = Intent(this, MyJobIntentService::class.java)
            mStartIntentService.putExtra(MyJobIntentService.EXTRA_DURATION, 5000L)
            MyJobIntentService.enqueueWork(this, mStartIntentService)
        }

        val btnStartBoundService = findViewById<Button>(R.id.btn_start_bound_service)
        btnStartBoundService.setOnClickListener {
            /*koneksikan serviceconnection diatas menggunakan intent*/
            val mBoundServiceIntent = Intent(this, MyBoundService::class.java)
            bindService(mBoundServiceIntent, mServiceConnection, BIND_AUTO_CREATE)
        }

        val btnStopBoundService = findViewById<Button>(R.id.btn_stop_bound_service)
        btnStopBoundService.setOnClickListener {
            /*menghentikan servicebound*/
            unbindService(mServiceConnection)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        /*ketika aplikasi sudah keluar dan service masih berjalan, akan
        * dihentikan otomatis dengan ini*/
        if (mServiceBound) {
            unbindService(mServiceConnection)
        }
    }
}