package ru.gb.rc

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.gb.rc.databinding.FragmentEditDeviceBinding

class EditDeviceFragment : Fragment() {

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}