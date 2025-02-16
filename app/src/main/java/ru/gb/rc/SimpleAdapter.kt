package ru.gb.rc

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.gb.rc.databinding.SimpleListItemBinding

class SimpleAdapter(
    values: List<String>
) : RecyclerView.Adapter<SimpleViewHolder>() {
    private var values = values.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val binding = SimpleListItemBinding.inflate(LayoutInflater.from(parent.context))
        return SimpleViewHolder(binding)
    }

    override fun getItemCount(): Int = values.size

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        val item = values[position]
        holder.binding.testFirst.text = item
    }

    fun setData(values: List<String>) {
        this.values = values.toMutableList()
        notifyDataSetChanged()
    }

    fun addItem(index: Int = 0, value: String){
        values.add(index, value)
        notifyItemInserted(index)
    }

    fun removeItem(index: Int = 0){
        values.removeAt(index)
        notifyItemRemoved(index)
    }
}

class SimpleViewHolder(val binding: SimpleListItemBinding) :
    RecyclerView.ViewHolder(binding.root)