package ru.gb.rc.presentation.pult_device

import android.content.Context
import androidx.fragment.app.viewModels
import android.os.Bundle
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
import ru.gb.rc.R
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
            powerButton.setOnClickListener { viewModel.powerButtonClicked() }
            muteButton.setOnClickListener { viewModel.muteButtonClicked() }
            oneButton.setOnClickListener { viewModel.oneButtonClicked() }
            twoButton.setOnClickListener { viewModel.twoButtonClicked() }
            threeButton.setOnClickListener { viewModel.threeButtonClicked() }
            fourButton.setOnClickListener { viewModel.fourButtonClicked() }
            upButton.setOnClickListener { viewModel.upButtonClicked() }
            downButton.setOnClickListener { viewModel.downButtonClicked() }
            minusButton.setOnClickListener { viewModel.minusButtonClicked() }
            plusButton.setOnClickListener { viewModel.plusButtonClicked() }
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            binding.powerButton.visibility =
                if (state.powerButton.isEmpty()) View.INVISIBLE else View.VISIBLE
            binding.muteButton.visibility =
                if (state.muteButton.isEmpty()) View.INVISIBLE else View.VISIBLE
            binding.oneButton.visibility =
                if (state.oneButton.isEmpty()) View.INVISIBLE else View.VISIBLE
            binding.twoButton.visibility =
                if (state.twoButton.isEmpty()) View.INVISIBLE else View.VISIBLE
            binding.threeButton.visibility =
                if (state.threeButton.isEmpty()) View.INVISIBLE else View.VISIBLE
            binding.fourButton.visibility =
                if (state.fourButton.isEmpty()) View.INVISIBLE else View.VISIBLE
            binding.upButton.visibility =
                if (state.upButton.isEmpty()) View.INVISIBLE else View.VISIBLE
            binding.downButton.visibility =
                if (state.downButton.isEmpty()) View.INVISIBLE else View.VISIBLE
            binding.minusButton.visibility =
                if (state.minusButton.isEmpty()) View.INVISIBLE else View.VISIBLE
            binding.plusButton.visibility =
                if (state.plusButton.isEmpty()) View.INVISIBLE else View.VISIBLE
            listener?.setTitle(
                if (state.namePult == "") getString(R.string.pult_device) else state.namePult
            )
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
                // Действие при выборе фото
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
                // Действие при выборе файла
                Toast.makeText(
                    activity, "File mode!", Toast.LENGTH_LONG
                ).show()
                true
            }

            else -> false
        }
    }
}