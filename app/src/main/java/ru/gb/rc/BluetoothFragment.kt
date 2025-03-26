package ru.gb.rc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import ru.gb.rc.databinding.FragmentBluetoothBinding
import ru.gb.rc.presentation.pult_device.DevicePultViewModel

class BluetoothFragment : Fragment() {

    private var _binding: FragmentBluetoothBinding? = null
    private val binding get() = _binding!!
    private val bluetoothViewModel: BluetoothViewModel by viewModels()

    companion object {
        fun newInstance() = BluetoothFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBluetoothBinding.inflate(inflater)
        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
    }
}