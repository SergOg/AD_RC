package ru.gb.rc

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.gb.rc.data.Device
import ru.gb.rc.databinding.DeviceCardBinding

class DeviceAdapter(
    values: List<Device>
) : RecyclerView.Adapter<DeviceViewHolder>() {
    private var values = values.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val binding = DeviceCardBinding.inflate(LayoutInflater.from(parent.context))
        return DeviceViewHolder(binding)
    }

    override fun getItemCount(): Int = values.size

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val item = values[position]
        holder.binding.location.text = item.location
        holder.binding.device.text = item.device
        holder.binding.protocol.text = item.protocol
    }

    fun setData(values: List<Device>) {
        this.values = values.toMutableList()
        notifyDataSetChanged()
    }

    fun addItem(index: Int = 0, value: Device){
        values.add(index, value)
        notifyItemInserted(index)
    }

    fun removeItem(index: Int = 0){
        values.removeAt(index)
        notifyItemRemoved(index)
    }
}

class DeviceViewHolder(val binding: DeviceCardBinding) :
    RecyclerView.ViewHolder(binding.root)