package ru.gb.rc.presentation.mode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.core.view.MenuProvider
import android.view.MenuItem
import android.widget.Toast
import ru.gb.rc.R
import ru.gb.rc.databinding.FragmentModeBinding

class ModeFragment : Fragment(), MenuProvider {

    private var _binding: FragmentModeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val modeViewModel =
            ViewModelProvider(this).get(ModeViewModel::class.java)

        _binding = FragmentModeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textMode
        modeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().addMenuProvider(this, viewLifecycleOwner)
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