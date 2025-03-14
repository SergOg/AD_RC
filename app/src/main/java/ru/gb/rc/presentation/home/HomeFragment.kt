package ru.gb.rc.presentation.home

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
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.gb.rc.R
import ru.gb.rc.databinding.FragmentHomeBinding

@AndroidEntryPoint
class HomeFragment : Fragment(), MenuProvider {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val myAdapter = DeviceAdapter(emptyList(), onPictureClicked = { device ->
        val action = HomeFragmentDirections.actionMainFragmentToEditDeviceFragment(device.id)
        findNavController().navigate(action)
    }, onDeleteClicked = { device ->
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Компонент будет удален!")
            .setMessage("Согласны удалить?")
            .setIcon(R.drawable.neon_robot)
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
    }, onItemClicked = { device ->
        val action = HomeFragmentDirections.actionMainFragmentToSettingsDeviceFragment(device.id)
        findNavController().navigate(action)
    }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

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
                val action = HomeFragmentDirections.actionMainFragmentToEditDeviceFragment(0)
                findNavController().navigate(action)
            }
        }
        requireActivity().addMenuProvider(this, viewLifecycleOwner)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.home_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.home_photo -> {
                // Действие при выборе
                Toast.makeText(
                    activity, "Photo home!", Toast.LENGTH_LONG
                ).show()
                true
            }

            R.id.home_file -> {
                // Действие при выборе
                Toast.makeText(
                    activity, "File home!", Toast.LENGTH_LONG
                ).show()
                true
            }

            else -> false
        }
    }
}