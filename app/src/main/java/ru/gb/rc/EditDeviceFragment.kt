package ru.gb.rc

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.gb.rc.databinding.FragmentEditDeviceBinding
import ru.gb.rc.presentation.home.MainFragment
import ru.gb.rc.presentation.home.MainViewModel

class EditDeviceFragment : Fragment() {

    private val editDeviceViewModel: EditDeviceViewModel by viewModels()
    private var _binding: FragmentEditDeviceBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = EditDeviceFragment()
    }

    private val viewModel: EditDeviceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditDeviceBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSave.setOnClickListener {
            var location = binding.editLocation.text
            var protocol = binding.editProtocol.text
            var device = binding.editDevice.text

// Навигация с передачей аргументов
            val action = EditDeviceFragmentDirections.actionEditFragmentToMainFragment(location, protocol, device)
            findNavController().navigate(action)
//            findNavController().navigate(R.id.action_editDeviceFragment_to_mainFragment)
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