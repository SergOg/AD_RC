package ru.gb.rc

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
import ru.gb.rc.databinding.FragmentDevicePultBinding

class DevicePultFragment : Fragment(), MenuProvider {

    companion object {
        fun newInstance() = DevicePultFragment()
    }

    private val viewModel: DevicePultViewModel by viewModels()
    private var _binding: FragmentDevicePultBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().addMenuProvider(this, viewLifecycleOwner)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDevicePultBinding.inflate(inflater, container, false)
        return binding.root
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