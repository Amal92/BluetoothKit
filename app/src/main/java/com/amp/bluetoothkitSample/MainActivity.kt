package com.amp.bluetoothkitSample

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.amp.bluetoothkit.BluetoothCallback
import com.amp.bluetoothkit.BluetoothKit
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val uuid = UUID.fromString("Your bluetooth device UUID")
    private val macAddress = "Your bluetooth device MAC address"

    private lateinit var bluetoothKit: BluetoothKit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bluetoothKit = BluetoothKit(this)
        bluetoothKit.setCallback(object : BluetoothCallback {
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
            bluetoothKit.write("message")
        }
        connect_btn.setOnClickListener {
            bluetoothKit.connectToDevice(macAddress, uuid)
        }
    }


    override fun onDestroy() {
        bluetoothKit.stop()
        super.onDestroy()
    }
}
