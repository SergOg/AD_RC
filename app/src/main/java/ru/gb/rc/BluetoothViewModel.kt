package ru.gb.rc

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.lifecycle.ViewModel
import java.io.IOException
import java.io.OutputStream
import java.util.UUID

class BluetoothViewModel(private val context: Context) : ViewModel() {

    private val packageManager: PackageManager = context.packageManager
    private var bluetoothAdapter: BluetoothAdapter? = null
    private var socket: BluetoothSocket? = null
    private lateinit var outputStream: OutputStream

    companion object {
        const val REQUEST_ENABLE_BT = 1
    }

    @SuppressLint("MissingPermission")
    fun enableBluetooth() {

        if (packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)) {
            // Устройство поддерживает Bluetooth
            val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
            bluetoothAdapter = bluetoothManager.adapter
        } else {
            // Устройства не поддерживает Bluetooth
            Toast.makeText(context, "Ваше устройство не поддерживает Bluetooth", Toast.LENGTH_SHORT)
                .show()
        }

        if (!bluetoothAdapter?.isEnabled!!) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            (context as Activity).startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        }
    }

    @SuppressLint("MissingPermission")
    fun discoverDevices() {
        if (bluetoothAdapter!!.isDiscovering) {
            bluetoothAdapter!!.cancelDiscovery()
        }
        bluetoothAdapter!!.startDiscovery()
    }

        // Найти устройство по адресу
    //        val device = bluetoothAdapter?.getRemoteDevice(deviceAddress)
    @SuppressLint("MissingPermission")
    fun connectToDevice(device: BluetoothDevice) {
        try {
            val uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
            socket = device.createRfcommSocketToServiceRecord(uuid)
            socket?.connect()
            outputStream = socket!!.outputStream
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun sendData(data: String) {
        if (socket != null && socket!!.isConnected) {
            try {
                val outputStream = socket!!.outputStream
                outputStream.write(data.toByteArray())
                outputStream.flush()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun disconnect() {
        try {
            socket?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}