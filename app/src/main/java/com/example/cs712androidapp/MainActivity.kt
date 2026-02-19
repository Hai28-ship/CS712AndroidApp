package com.example.cs712androidapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Declare BroadcastReceiver
    private lateinit var myReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnExplicit = findViewById<Button>(R.id.btnExplicit)
        val btnImplicit = findViewById<Button>(R.id.btnImplicit)
        val btnStartService = findViewById<Button>(R.id.btnStartService)
        val btnSendBroadcast = findViewById<Button>(R.id.btnSendBroadcast)

        // Explicit Intent → opens SecondActivity
        btnExplicit.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        // Implicit Intent → opens browser
        btnImplicit.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://developer.android.com")
            startActivity(intent)
        }

        // Start Service → triggers foreground service
        btnStartService.setOnClickListener {
            val serviceIntent = Intent(this, MyService::class.java)
            startForegroundService(serviceIntent)
        }

        // Send Broadcast → triggers MyBroadcastReceiver
        btnSendBroadcast.setOnClickListener {
            val intent = Intent()
            intent.action = "com.example.cs712androidapp.MY_CUSTOM_BROADCAST"
            sendBroadcast(intent)
        }

        // Dynamically register BroadcastReceiver
        myReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Toast.makeText(context, "Broadcast received!", Toast.LENGTH_SHORT).show()
            }
        }
        val filter = IntentFilter("com.example.cs712androidapp.MY_CUSTOM_BROADCAST")
        registerReceiver(myReceiver, filter)
    }

    override fun onStop() {
        super.onStop()
        // Unregister BroadcastReceiver
        unregisterReceiver(myReceiver)
    }
}
