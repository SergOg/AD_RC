package ru.gb.rc.presentation.settings_device

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
                powerButton = binding.editPowerButton.text.toString(),
                muteButton = binding.editMuteButton.text.toString(),
                oneButton = binding.editOneButton.text.toString(),
                twoButton = binding.editTwoButton.text.toString(),
                threeButton = binding.editThreeButton.text.toString(),
                fourButton = binding.editFourButton.text.toString(),
                upButton = binding.editUpButton.text.toString(),
                downButton = binding.editDownButton.text.toString(),
                minusButton = binding.editMinusButton.text.toString(),
                plusButton = binding.editPlusButton.text.toString(),
            )
        }
        viewModel.state.observe(viewLifecycleOwner) { state ->
            binding.editPowerButton.setText(state.powerButton)
            binding.editMuteButton.setText(state.muteButton)
            binding.editOneButton.setText(state.oneButton)
            binding.editTwoButton.setText(state.twoButton)
            binding.editThreeButton.setText(state.threeButton)
            binding.editFourButton.setText(state.fourButton)
            binding.editUpButton.setText(state.upButton)
            binding.editDownButton.setText(state.downButton)
            binding.editMinusButton.setText(state.minusButton)
            binding.editPlusButton.setText(state.plusButton)
        }

        binding.buttonCancelSettings.setOnClickListener {
            deviceSettingsViewModel.closeScreenEvent.run {
                findNavController().popBackStack()
            }
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