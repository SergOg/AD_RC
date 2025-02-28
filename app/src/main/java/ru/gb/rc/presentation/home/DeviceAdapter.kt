package ru.gb.rc.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.gb.rc.data.Device
import ru.gb.rc.databinding.DeviceCardViewBinding

class DeviceAdapter(
    values: List<Device>,
    private val onDeleteClicked: (Device) -> Unit,
    private val onItemClicked: (Device) -> Unit
) : RecyclerView.Adapter<DeviceViewHolder>() {
    private var values = values.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val binding = DeviceCardViewBinding.inflate(LayoutInflater.from(parent.context))
        return DeviceViewHolder(binding)
    }

    override fun getItemCount(): Int = values.size

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val item = values[position]
        holder.binding.location.text = item.location
        holder.binding.device.text = item.device
        holder.binding.protocol.text = item.protocol
        holder.binding.pic.setOnLongClickListener {     //Удаление по нажатию на картинку
            onDeleteClicked(item)
            true
        }
        holder.binding.deviceCardView.setOnClickListener{
            onItemClicked(item)
        }
    }

    fun setData(values: List<Device>) {
        this.values = values.toMutableList()
        notifyDataSetChanged()
    }
}

class DeviceViewHolder(val binding: DeviceCardViewBinding) :
    RecyclerView.ViewHolder(binding.root)