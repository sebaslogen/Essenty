package com.arkivanov.essenty.statekeeper.coding

internal class ByteArrayDataInput(
    private var bytes: ByteArray,
) : DataInput {

    private var index: Int = 0

    override fun readByte(): Byte =
        bytes[index++]
}
