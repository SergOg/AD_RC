package ru.gb.rc

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.gb.rc.databinding.MySimpleListItemBinding

class MySimpleAdapter(
    values: List<String>
) : RecyclerView.Adapter<MySimpleViewHolder>() {
    private var values = values.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySimpleViewHolder {
        val binding = MySimpleListItemBinding.inflate(LayoutInflater.from(parent.context))
        return MySimpleViewHolder(binding)
    }

    override fun getItemCount(): Int = values.size

    override fun onBindViewHolder(holder: MySimpleViewHolder, position: Int) {
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

class MySimpleViewHolder(val binding: MySimpleListItemBinding) :
    RecyclerView.ViewHolder(binding.root)