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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.withCreationCallback
import kotlinx.coroutines.launch
import ru.gb.rc.R
import ru.gb.rc.databinding.FragmentDeviceSettingsBinding
import ru.gb.rc.presentation.edit_device.EditDeviceViewModel

@AndroidEntryPoint
class DeviceSettingsFragment : Fragment() {

    private var _binding: FragmentDeviceSettingsBinding? = null
    private val binding get() = _binding!!
    private val deviceSettingsViewModel: DeviceSettingsViewModel by viewModels()

    companion object {
        fun newInstance() = DeviceSettingsFragment()
    }

    private val viewModel by viewModels<DeviceSettingsViewModel>(
        extrasProducer = {
            defaultViewModelCreationExtras.withCreationCallback<DeviceSettingsViewModel.Factory> { factory ->
                factory.create(id = arguments?.getInt("id") ?: 0)
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeviceSettingsBinding.inflate(inflater)

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSaveSettings.setOnClickListener {
            viewModel.onSaveBtn(
                location = binding.editPowerButton.text.toString(),
                protocol = binding.editMuteButton.text.toString(),
                equipment = binding.editOneButton.text.toString(),
            )
        }
        viewModel.state.observe(viewLifecycleOwner) { state ->
            binding.editPowerButton.setText(state.location)
            binding.editMuteButton.setText(state.protocol)
            binding.editOneButton.setText(state.equipment)
        }

        binding.buttonCancelSettings.setOnClickListener {
            findNavController().navigate(R.id.action_settingsDeviceFragment_to_pultDeviceFragment)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            deviceSettingsViewModel.closeScreenEvent.collect {
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}