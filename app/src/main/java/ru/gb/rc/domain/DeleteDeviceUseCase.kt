package ru.gb.rc.domain

import ru.gb.rc.data.Device

interface DeleteDeviceUseCase {
    suspend operator fun invoke(device: Device)
}