package ru.gb.rc

import android.content.Context
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
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.withCreationCallback
import kotlinx.coroutines.launch
import ru.gb.rc.databinding.FragmentDevicePultBinding

@AndroidEntryPoint
class DevicePultFragment : Fragment(), MenuProvider {

    private var listener: OnFragmentInteractionListener? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }
    override fun onDetach() {
        super.onDetach()
        listener = null
    }

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
            powerButton.setOnClickListener {viewModel.powerButtonClicked()}
            muteButton.setOnClickListener {viewModel.muteButtonClicked()}
            oneButton.setOnClickListener {viewModel.oneButtonClicked()}
            twoButton.setOnClickListener {viewModel.twoButtonClicked()}
            threeButton.setOnClickListener {viewModel.threeButtonClicked()}
            fourButton.setOnClickListener {viewModel.fourButtonClicked()}
            upButton.setOnClickListener {viewModel.upButtonClicked()}
            downButton.setOnClickListener {viewModel.downButtonClicked()}
            minusButton.setOnClickListener {viewModel.minusButtonClicked()}
            plusButton.setOnClickListener {viewModel.plusButtonClicked()}
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            if (state.powerButton.isEmpty()) {binding.powerButton.visibility = View.INVISIBLE
            } else {binding.powerButton.visibility = View.VISIBLE}
            if (state.muteButton.isEmpty()) {binding.muteButton.visibility = View.INVISIBLE
            } else { binding.muteButton.visibility = View.VISIBLE}
            if (state.oneButton.isEmpty()) {binding.oneButton.visibility = View.INVISIBLE
            } else {binding.oneButton.visibility = View.VISIBLE}
            if (state.twoButton.isEmpty()) {binding.twoButton.visibility = View.INVISIBLE
            } else {binding.twoButton.visibility = View.VISIBLE}
            if (state.threeButton.isEmpty()) {binding.threeButton.visibility = View.INVISIBLE
            } else {binding.threeButton.visibility = View.VISIBLE}
            if (state.fourButton.isEmpty()) {binding.fourButton.visibility = View.INVISIBLE
            } else {binding.fourButton.visibility = View.VISIBLE}
            if (state.upButton.isEmpty()) {binding.upButton.visibility = View.INVISIBLE
            } else {binding.upButton.visibility = View.VISIBLE}
            if (state.downButton.isEmpty()) {binding.downButton.visibility = View.INVISIBLE
            } else {binding.downButton.visibility = View.VISIBLE}
            if (state.minusButton.isEmpty()) {binding.minusButton.visibility = View.INVISIBLE
            } else {binding.minusButton.visibility = View.VISIBLE}
            if (state.plusButton.isEmpty()) {binding.plusButton.visibility = View.INVISIBLE
            } else {binding.plusButton.visibility = View.VISIBLE}
            if (state.namePult == "") {getString(R.string.pult_device)
            } else {listener?.setTitle(state.namePult)}
        }

        viewLifecycleOwner.lifecycleScope.launch {
            devicePultViewModel.closeScreenEvent.collect {
                findNavController().popBackStack()
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            devicePultViewModel.toastScreenEvent.collect {
                Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.init()
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

//                val action =
//                    DevicePultFragmentDirections.actionPultDeviceFragmentToPhotoDeviceFragment(
//                        viewModel.id
//                    )
//                findNavController().navigate(action)
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