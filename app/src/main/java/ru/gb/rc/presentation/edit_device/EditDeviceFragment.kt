package ru.gb.rc.presentation.edit_device

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.withCreationCallback
import kotlinx.coroutines.launch
import ru.gb.rc.R
import ru.gb.rc.databinding.FragmentEditDeviceBinding
import javax.inject.Inject

@AndroidEntryPoint
class EditDeviceFragment : Fragment() {

    private var _binding: FragmentEditDeviceBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = EditDeviceFragment()
    }

//    @Inject
//    lateinit var viewModelFactory: EditDeviceViewModel.Factory

    private val viewModel by viewModels<EditDeviceViewModel>(
        extrasProducer = {
            defaultViewModelCreationExtras.withCreationCallback<EditDeviceViewModel.Factory> { factory ->
                factory.create(id = arguments?.getInt("id") ?: -1)
            }
        }
    )
//    private val viewModel by lazy {
//        viewModelFactory.create(id = arguments?.getInt("id") ?: -1)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditDeviceBinding.inflate(inflater)
        viewModel.init(-1)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSave.setOnClickListener {
            viewModel.onAddBtn(
                location = binding.editLocation.text.toString(),
                protocol = binding.editProtocol.text.toString(),
                equipment = binding.editEquipment.text.toString(),
            )
        }
        viewModel.state.observe(viewLifecycleOwner) { state ->
            binding.editLocation.setText(state.location)
            binding.editProtocol.setText(state.protocol)
            binding.editEquipment.setText(state.equipment)
        }

        binding.buttonCancel.setOnClickListener {
            findNavController().navigate(R.id.action_editDeviceFragment_to_mainFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}