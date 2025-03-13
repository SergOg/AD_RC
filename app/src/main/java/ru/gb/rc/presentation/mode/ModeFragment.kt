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
import ru.gb.rc.R
import ru.gb.rc.databinding.FragmentModeBinding

class ModeFragment : Fragment() {

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        setHasOptionsMenu(true)
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.pult_menu, menu)
//        return true
    }
}