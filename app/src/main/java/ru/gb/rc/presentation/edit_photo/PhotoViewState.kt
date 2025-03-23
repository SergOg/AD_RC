package ru.gb.rc.presentation.edit_photo

data class PhotoViewState(
    val id: Int? = null,
    val location: String = "",
    val imgSrc: String = "",
    val protocol: String = "",
    val equipment: String = "",
)