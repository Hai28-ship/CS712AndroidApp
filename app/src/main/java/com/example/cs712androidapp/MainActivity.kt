package com.example.cs712androidapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var myReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnExplicit = findViewById<Button>(R.id.btnExplicit)
        val btnImplicit = findViewById<Button>(R.id.btnImplicit)
        val btnStartService = findViewById<Button>(R.id.btnStartService)
        val btnSendBroadcast = findViewById<Button>(R.id.btnSendBroadcast)
        val btnThirdActivity = findViewById<Button>(R.id.btnThirdActivity)

        // Explicit Intent → SecondActivity
        btnExplicit.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        // Implicit Intent → Browser
        btnImplicit.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://developer.android.com")
            startActivity(intent)
        }

        // Start Service (API-safe)
        btnStartService.setOnClickListener {
            val serviceIntent = Intent(this, MyService::class.java)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(serviceIntent)
            } else {
                startService(serviceIntent)
            }
        }

        // Send Broadcast
        btnSendBroadcast.setOnClickListener {
            val intent = Intent()
            intent.action = "com.example.cs712androidapp.MY_CUSTOM_BROADCAST"
            intent.setPackage(packageName) // recommended
            sendBroadcast(intent)
        }

        // Third Activity
        btnThirdActivity.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }

        // BroadcastReceiver
        myReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Toast.makeText(context, "Broadcast received!", Toast.LENGTH_SHORT).show()
            }
        }

        val filter = IntentFilter("com.example.cs712androidapp.MY_CUSTOM_BROADCAST")

        // ✅ SAFE + MODERN WAY (works with minSdk 24)
        ContextCompat.registerReceiver(
            this,
            myReceiver,
            filter,
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(myReceiver)
    }
}