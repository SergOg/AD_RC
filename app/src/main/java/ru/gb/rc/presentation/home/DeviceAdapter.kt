package ru.gb.rc.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.gb.rc.data.Device
import ru.gb.rc.data.DeviceWithSettings
import ru.gb.rc.databinding.DeviceCardViewBinding

class DeviceAdapter(
    values: List<DeviceWithSettings>,
    private val onItemClicked: (Device) -> Unit,
    private val onDeleteClicked: (Device) -> Unit,
    private val onPictureClicked: (Device) -> Unit,
) : RecyclerView.Adapter<DeviceViewHolder>() {
    private var values = values.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val binding =
            DeviceCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeviceViewHolder(binding)
    }

    override fun getItemCount(): Int = values.size

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val item = values[position]
        holder.binding.location.text = item.device.location
        holder.binding.equipment.text = item.device.equipment
        holder.binding.protocol.text = item.device.protocol
        holder.binding.pic.setOnLongClickListener {     //Удаление по долгому нажатию на картинку
            onDeleteClicked(item.device)
            true
        }
        holder.binding.pic.setOnClickListener {          //Редактирование по нажатию на картинку
            onItemClicked(item.device)
        }
        holder.binding.deviceCardView.setOnClickListener {  //Переход в управление устройством
            onPictureClicked(item.device)
        }
    }

    fun setData(values: List<DeviceWithSettings>) {
        this.values = values.toMutableList()
        notifyDataSetChanged()
    }
}

class DeviceViewHolder(val binding: DeviceCardViewBinding) :
    RecyclerView.ViewHolder(binding.root)