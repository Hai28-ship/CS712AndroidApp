package com.example.cs712androidapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
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

        // =========================
        // CUSTOM PERMISSION CHECK
        // =========================
        if (ContextCompat.checkSelfPermission(
                this,
                "com.example.cs712androidapp.MSE712"
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf("com.example.cs712androidapp.MSE712"),
                100
            )
        }

        // Explicit Intent → SecondActivity (NOW PROTECTED FLOW)
        btnExplicit.setOnClickListener {
            openSecondActivity()
        }

        // Implicit Intent → Browser
        btnImplicit.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://developer.android.com")
            startActivity(intent)
        }

        // Start Service
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
            intent.setPackage(packageName)
            sendBroadcast(intent)
        }

        // Third Activity
        btnThirdActivity.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }

        // Broadcast Receiver
        myReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Toast.makeText(context, "Broadcast received!", Toast.LENGTH_SHORT).show()
            }
        }

        val filter = IntentFilter("com.example.cs712androidapp.MY_CUSTOM_BROADCAST")

        ContextCompat.registerReceiver(
            this,
            myReceiver,
            filter,
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
    }

    // =========================
    // OPEN SECOND ACTIVITY
    // =========================
    private fun openSecondActivity() {
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
    }

    // =========================
    // PERMISSION RESULT HANDLER
    // =========================
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 100) {
            if (grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                openSecondActivity()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(myReceiver)
    }
}