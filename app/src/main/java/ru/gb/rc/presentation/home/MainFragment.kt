package ru.gb.rc.presentation.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.gb.rc.R
import ru.gb.rc.databinding.FragmentMainBinding

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    val myAdapter = DeviceAdapter(emptyList()) { device ->
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Компонент будет удален!")
            .setMessage("Согласны удалить?")
            .setIcon(R.drawable.gb)
            .setCancelable(true)
            .setPositiveButton("Да") { _, _ ->
                Toast.makeText(
                    activity, "Компонент удален!", Toast.LENGTH_LONG
                ).show()
                //Вызваем метод во ViewModel на удалениие записи
                viewModel.onDeleteBtn(device)
            }
            .setNegativeButton("Нет") { d, _ ->
                d.dismiss()
                Toast.makeText(
                    activity, "Отмена удаления!", Toast.LENGTH_LONG
                ).show()
            }
        val dialog = builder.create()
        dialog.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        // Определяем жизненный цикл, когда должна происходить сборка данных
        viewLifecycleOwner.lifecycleScope
            .launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    // Собираем данные в соответствующем состоянии жизненного цикла
                    viewModel.allDevices.collect { devices ->
                        myAdapter.setData(devices)
                    }
                }
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            recyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            recyclerView.addItemDecoration(
                CardSpacingDecoration(
                    resources.getDimensionPixelSize(
                        R.dimen.card_spacing
                    )
                )
            )
            recyclerView.adapter = myAdapter

            fab.setOnClickListener {
                val action = Action
                findNavController().navigate(R.id.action_mainFragment_to_editDeviceFragment)
//                val location = args.location


                viewModel.onAddBtn("location-51165165", "protocol", "device")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}