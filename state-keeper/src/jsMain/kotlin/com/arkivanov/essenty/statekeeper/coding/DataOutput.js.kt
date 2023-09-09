package com.arkivanov.essenty.statekeeper.coding

internal actual fun DataOutput.writeFloat(value: Float) {
    writeString(value.toString())
}
