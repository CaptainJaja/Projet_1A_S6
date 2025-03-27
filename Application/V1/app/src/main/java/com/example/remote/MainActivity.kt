package com.example.remote

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.remote.ui.theme.RemoteTheme

//  import androidx.appcompat.app.AppCompatActivity
import android.app.Activity
import android.bluetooth.*
import android.content.Intent
import android.content.pm.PackageManager
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import java.io.IOException
import java.io.OutputStream
import java.util.UUID

class MainActivity : Activity() {

    private var bluetoothAdapter: BluetoothAdapter? = null
    private var bluetoothSocket: BluetoothSocket? = null
    private var outputStream: OutputStream? = null

    private val DEVICE_ADDRESS = "00:00:00:00:00:00" // Remplace par l'adresse MAC de ton HC-05
    private val UUID_BT = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    fun sendCommand(command: String) {
        try {
            outputStream?.write(command.toByteArray())
        } catch (e: IOException) {
            Toast.makeText(this, "Échec d'envoi", Toast.LENGTH_SHORT).show()
        }
        fun connectBluetooth() {
            try {
                val device: BluetoothDevice = bluetoothAdapter!!.getRemoteDevice(DEVICE_ADDRESS)
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.BLUETOOTH_CONNECT
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                bluetoothSocket = device.createRfcommSocketToServiceRecord(UUID_BT)
                bluetoothSocket!!.connect()
                outputStream = bluetoothSocket!!.outputStream
                Toast.makeText(this, "Connecté au robot", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Toast.makeText(this, "Erreur de connexion", Toast.LENGTH_LONG).show()
            }
        }

    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth non supporté", Toast.LENGTH_LONG).show()
            finish()
        }

        // Linking buttons using findViewById
        val btnConnect: Button = findViewById(R.id.btnConnect)
        val btnForward: Button = findViewById(R.id.btnForward)
        val btnBackward: Button = findViewById(R.id.btnBackward)
        val btnLeft: Button = findViewById(R.id.btnLeft)
        val btnRight: Button = findViewById(R.id.btnRight)
        val btnStop: Button = findViewById(R.id.btnStop)

        // Example: Set click listeners
        btnConnect.setOnClickListener {
            // Add Bluetooth connection logic here


        findViewById<Button>(R.id.btnConnect).setOnClickListener { connectBluetooth() }
        findViewById<Button>(R.id.btnForward).setOnClickListener { sendCommand("F") }
        findViewById<Button>(R.id.btnBackward).setOnClickListener { sendCommand("B") }
        findViewById<Button>(R.id.btnLeft).setOnClickListener { sendCommand("L") }
        findViewById<Button>(R.id.btnRight).setOnClickListener { sendCommand("R") }
        findViewById<Button>(R.id.btnStop).setOnClickListener { sendCommand("S") }
    }










    }

}}

