package ru.gb.rc

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import java.io.IOException
import java.util.UUID

class BluetoothViewModel(private val context: Context) : ViewModel() {
    private var bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    private var socket: BluetoothSocket? = null

    companion object {
        const val REQUEST_ENABLE_BT = 1
    }

    @SuppressLint("MissingPermission")
    fun enableBluetooth() {
        if (bluetoothAdapter == null) {
            Toast.makeText(context, "Ваше устройство не поддерживает Bluetooth", Toast.LENGTH_SHORT)
                .show()
            return
        }

        if (!bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            (context as Activity).startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        }
    }

    @SuppressLint("MissingPermission")
    fun discoverDevices() {
        if (bluetoothAdapter.isDiscovering) {
            bluetoothAdapter.cancelDiscovery()
        }

        bluetoothAdapter.startDiscovery()
    }

    @SuppressLint("MissingPermission")
    fun connectToDevice(device: BluetoothDevice) {
        try {
            val uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
            socket = device.createRfcommSocketToServiceRecord(uuid)

            socket?.connect()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun sendData(data: String) {
        if (socket != null && socket!!.isConnected) {
            try {
                val outputStream = socket!!.outputStream
                outputStream.write(data.toByteArray())
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

}