package ru.gb.rc.presentation.edit_device

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.withCreationCallback
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
        viewModel.init()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSave.setOnClickListener {

// Навигация с передачей аргументов
//            val action = EditDeviceFragmentDirections.actionEditFragmentToMainFragment(location, protocol, device)
//            findNavController().navigate(action)
//            findNavController().navigate(R.id.action_editDeviceFragment_to_mainFragment)
            viewModel.onAddBtn()
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