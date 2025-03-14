package ru.gb.rc.domain

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import ru.gb.rc.R
import ru.gb.rc.databinding.FragmentDeviceSettingsBinding

class DeviceSettingsFragment : Fragment() {

    companion object {
        fun newInstance() = DeviceSettingsFragment()
    }

    private val viewModel: DeviceSettingsViewModel by viewModels()
    private var _binding: FragmentDeviceSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeviceSettingsBinding.inflate(inflater, container, false)

        // Найти Spinner в макете
        val spinner = binding.spinnerSettings

        // Получить массив строк из ресурсов
        val options = resources.getStringArray(R.array.options)

        // Установить адаптер для Spinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        // Обработчик выбора элемента
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(
                    context,
                    "Выбрано: ${parent?.getItemAtPosition(position)}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return binding.root
    }
}