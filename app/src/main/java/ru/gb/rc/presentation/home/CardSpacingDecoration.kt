package ru.gb.rc.presentation.home

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CardSpacingDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        // Устанавливаем отступы слева, сверху, справа и снизу
        outRect.left = spacing
        outRect.top = spacing
        outRect.right = spacing
        outRect.bottom = spacing
    }
}