package ru.gb.rc.presentation.home

import android.content.Context
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ru.gb.rc.DeviceAdapter
import ru.gb.rc.R
import ru.gb.rc.data.Device
import ru.gb.rc.databinding.FragmentMainBinding
import kotlin.random.Random

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

//    private val recyclerView : RecyclerView = binding.recyclerView

    //    val data: List<String> = (0..100).map { it.toString() }
    val myAdapter = DeviceAdapter(emptyList())

//    val deviceDao: DeviceDao = (application as App).db.deviceDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        recyclerView.addItemDecoration(CardSpacingDecoration(resources.getDimensionPixelSize(R.dimen.card_spacing)))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        with(binding) {
            add.setOnClickListener {
                viewModel.onAddBtn()
            }

            set.setOnClickListener {
                viewModel.onUpdateBtn()
            }

            remove.setOnClickListener {
                viewModel.onDeleteBtn()
            }
        }
//        lifecycleScope.launchWhenCreated {
//            viewModel.allDevices
//                .collect { devices ->
//                    myAdapter.setData(devices)
//                }
//        }

// Кнопка для вызова диалога
        binding.set.setOnClickListener {
            val myDialogFragment = MyDialogFragment()
            val manager = supportFragmentManager
            myDialogFragment.show(manager, "myDialog")
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var countDevice = 0
        binding.fab.setOnClickListener {
            Toast.makeText(context, "Pressed add button!", Toast.LENGTH_SHORT).show()
            val item = Device(
                countDevice,
                "Location",
                Random.nextInt(100, 200).toString(),
                "text",
                Random.nextInt(100, 200).toString()
            )
            myAdapter.addItem(0, item)
            countDevice++
        }
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        val data: List<String> = (0..100).map { it.toString() }
//        val myAdapter = SimpleAdapter(data)
        binding.recyclerView.adapter = myAdapter

//        binding.add.setOnClickListener {
//            val item = Random.nextInt(100, 200).toString()
//            myAdapter.addItem(0, item)
//            Toast.makeText(context, "Pressed add button!", Toast.LENGTH_SHORT).show()
//        }

        binding.remove.setOnClickListener {
            if (countDevice != 0) {
                myAdapter.removeItem(0)
                countDevice--
            }
            Toast.makeText(context, "Pressed remove button!", Toast.LENGTH_SHORT).show()
        }

//            binding.del_button.setOnClickListener {
//                val myDialogFragment = MyDialogFragment()
//                val manager = supportFragmentManager
//                myDialogFragment.show(manager, "myDialog")
//            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}