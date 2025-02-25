package ru.gb.rc.presentation.home

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import ru.gb.rc.R

class MyDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Компонент будет удален!")
                .setMessage("Согласны удалить?")
                .setIcon(R.drawable.gb)
                .setCancelable(true)
                .setPositiveButton("Да") { _, _ ->
                    Toast.makeText(
                        activity, "Компонент удален!", Toast.LENGTH_LONG
                    ).show()
                }
                .setNegativeButton("Нет") { _, _ ->
                    Toast.makeText(
                        activity, "Отмена!", Toast.LENGTH_LONG
                    ).show()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}