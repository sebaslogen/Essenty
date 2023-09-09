package com.arkivanov.essenty.statekeeper.coding

internal class ByteArrayDataOutput : DataOutput {

    private var buffer: ByteArray = ByteArray(size = 16)
    private var size: Int = 0

    override fun writeByte(byte: Byte) {
        if (buffer.size - size < 1) {
            buffer = buffer.copyOf(newSize = buffer.size * 2)
        }

        buffer[size++] = byte
    }

    fun getBytes(): ByteArray =
        buffer.copyOf(newSize = size)
}
