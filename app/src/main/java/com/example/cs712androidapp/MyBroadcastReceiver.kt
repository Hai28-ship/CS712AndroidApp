package com.example.cs712androidapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        // Only respond to our custom broadcast
        if (intent?.action == "com.example.cs712androidapp.MY_CUSTOM_BROADCAST") {
            Toast.makeText(context, "Broadcast received", Toast.LENGTH_SHORT).show()
        }
    }
}

