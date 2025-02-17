package ru.gb.rc.presentation.classic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.gb.rc.databinding.FragmentClassicBinding

class ClassicFragment : Fragment() {

    private var _binding: FragmentClassicBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val classicViewModel =
            ViewModelProvider(this).get(ClassicViewModel::class.java)

        _binding = FragmentClassicBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textClassic
        classicViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}