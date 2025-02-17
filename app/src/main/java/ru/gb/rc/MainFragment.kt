package ru.gb.rc

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import ru.gb.rc.databinding.FragmentMainBinding
import kotlin.random.Random

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val data: List<String> = (0..100).map { it.toString() }
        val myAdapter = SimpleAdapter(data)
        binding.recyclerView.adapter = myAdapter

        binding.add.setOnClickListener {
            val item = Random.nextInt(100, 200).toString()
            myAdapter.addItem(5, item)
            Toast.makeText(context, "Pressed add button!", Toast.LENGTH_SHORT).show()
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null)
//                .setAnchorView(R.id.fab).show()
        }

        binding.remove.setOnClickListener {
            myAdapter.removeItem(1)
            Toast.makeText(context, "Pressed remove button!", Toast.LENGTH_SHORT).show()
        }

//        binding.set.setOnClickListener {
//            val newData = List(100) { Random.nextInt(0, 100).toString() }
//            myAdapter.setData(newData)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}