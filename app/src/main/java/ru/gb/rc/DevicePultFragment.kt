package ru.gb.rc

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.withCreationCallback
import kotlinx.coroutines.launch
import ru.gb.rc.databinding.FragmentDevicePultBinding

@AndroidEntryPoint
class DevicePultFragment : Fragment(), MenuProvider {

    private var _binding: FragmentDevicePultBinding? = null
    private val binding get() = _binding!!
    private val devicePultViewModel: DevicePultViewModel by viewModels()

    companion object {
        fun newInstance() = DevicePultFragment()
    }

    private val viewModel by viewModels<DevicePultViewModel>(
        extrasProducer = {
            defaultViewModelCreationExtras.withCreationCallback<DevicePultViewModel.Factory> { factory ->
                factory.create(id = arguments?.getInt("id") ?: 0)
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDevicePultBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().addMenuProvider(this, viewLifecycleOwner)

        with(binding) {
            powerButton.setOnClickListener {
                Toast.makeText(
                    activity, "Power button!", Toast.LENGTH_LONG
                ).show()
            }
            muteButton.setOnClickListener {
                Toast.makeText(
                    activity, "Mute button!", Toast.LENGTH_LONG
                ).show()
            }
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            if (state.powerButton.isEmpty()) {
                binding.powerButton.visibility = View.INVISIBLE
            } else {
                binding.powerButton.visibility = View.VISIBLE
            }

            Log.d("DevicePultFragmentId", viewModel.id.toString())
            Log.d("DevicePultFragmentState", state.powerButton.toString())

            if (state.muteButton.isEmpty()) {
                binding.muteButton.visibility = View.INVISIBLE
            } else {
                binding.muteButton.visibility = View.VISIBLE
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            devicePultViewModel.closeScreenEvent.collect {
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.pult_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.pult_settings -> {
                // Действие при выборе настроек
                Toast.makeText(
                    activity, "Settings mode!", Toast.LENGTH_LONG
                ).show()

                val action =
                    DevicePultFragmentDirections.actionPultDeviceFragmentToSettingsDeviceFragment(
                        viewModel.id
                    )
                findNavController().navigate(action)
                true
            }

            R.id.pult_photo -> {
                // Действие при выборе
                Toast.makeText(
                    activity, "Photo mode!", Toast.LENGTH_LONG
                ).show()
                true
            }

            R.id.pult_file -> {
                // Действие при выборе
                Toast.makeText(
                    activity, "File mode!", Toast.LENGTH_LONG
                ).show()
                true
            }

            else -> false
        }
    }
}