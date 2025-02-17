package ru.gb.rc.presentation.ble

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.gb.rc.databinding.FragmentBleBinding

class BleFragment : Fragment() {

    private var _binding: FragmentBleBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bleViewModel =
            ViewModelProvider(this).get(BleViewModel::class.java)

        _binding = FragmentBleBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textBle
        bleViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}