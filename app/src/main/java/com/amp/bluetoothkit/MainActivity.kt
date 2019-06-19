package com.amp.bluetoothkit

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val MY_UUID = UUID.fromString("Bluetooth Device UUID")
    private val macAddress = "Bluetooth Device MAC Address"

    private lateinit var bluekit: Bluekit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bluekit = Bluekit(this)
        bluekit.setCallback(object : BluetoothCallback {
            override fun Connected() {
                Log.d("Amal", "connected")

                runOnUiThread {
                    connect_btn.visibility = View.GONE
                    open_btn.visibility = View.VISIBLE
                }
            }

            override fun Disconnected() {
                Log.d("Amal", "Disconnected")
                runOnUiThread {
                    connect_btn.visibility = View.VISIBLE
                    open_btn.visibility = View.GONE
                }
            }

            override fun MessageSent() {
                Log.d("Amal", "MessageSent")
            }

            override fun Unsupported() {
                Log.d("Amal", "Unsupported")
            }

            override fun ConnectionFailed(e: Exception, message: String) {
                Log.d("Amal", "ConnectionFailed: $message")
            }

            override fun MessageFailed(e: Exception, message: String) {
                Log.d("Amal", "MessageFailed: $message")
                runOnUiThread {
                    connect_btn.visibility = View.VISIBLE
                    open_btn.visibility = View.GONE
                }
            }
        })
        open_btn.setOnClickListener {
            // Code here executes on main thread after user presses button
            bluekit.write("message")
        }
        connect_btn.setOnClickListener {
            bluekit.connectToDevice(macAddress, MY_UUID)
        }
    }


    override fun onDestroy() {
        bluekit.stop()
        super.onDestroy()
    }
}
