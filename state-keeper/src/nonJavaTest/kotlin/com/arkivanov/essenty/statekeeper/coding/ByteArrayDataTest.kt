package com.arkivanov.essenty.statekeeper.coding

import kotlinx.serialization.Serializable
import kotlin.test.Test
import kotlin.test.assertEquals

class ByteArrayDataTest {

    @Test
    fun writes_and_reads_data() {
        val output = ByteArrayDataOutput()
        output.writeTestData()
        val input = ByteArrayDataInput(output.getBytes())

        input.assertTestData()
    }

    private fun DataInput.assertTestData() {
        assertEquals(Byte.MIN_VALUE, readByte())
        assertEquals(0.toByte(), readByte())
        assertEquals(Byte.MAX_VALUE, readByte())
        assertEquals(false, readBoolean())
        assertEquals(true, readBoolean())
        assertEquals(Short.MIN_VALUE, readShort())
        assertEquals(0.toShort(), readShort())
        assertEquals(Short.MAX_VALUE, readShort())
        assertEquals(Int.MIN_VALUE, readInt())
        assertEquals(0, readInt())
        assertEquals(Int.MAX_VALUE, readInt())
        assertEquals(Long.MIN_VALUE, readLong())
        assertEquals(0L, readLong())
        assertEquals(Long.MAX_VALUE, readLong())
        assertEquals(Float.NEGATIVE_INFINITY, readFloat())
        assertEquals(Float.POSITIVE_INFINITY, readFloat())
        assertEquals(Float.MIN_VALUE, readFloat())
        assertEquals(Float.MAX_VALUE, readFloat())
        assertEquals(-Float.MAX_VALUE, readFloat())
        assertEquals(Float.NaN, readFloat())
        assertEquals(0F, readFloat())
        assertEquals(1F, readFloat())
        assertEquals(-1F, readFloat())
        assertEquals(Double.NEGATIVE_INFINITY, readDouble())
        assertEquals(Double.POSITIVE_INFINITY, readDouble())
        assertEquals(Double.MIN_VALUE, readDouble())
        assertEquals(Double.MAX_VALUE, readDouble())
        assertEquals(-Double.MAX_VALUE, readDouble())
        assertEquals(Double.NaN, readDouble())
        assertEquals(0.0, readDouble())
        assertEquals(1.0, readDouble())
        assertEquals(-1.0, readDouble())
        assertEquals(Char.MIN_VALUE, readChar())
        assertEquals(Char.MAX_VALUE, readChar())
        assertEquals(Char.MIN_SURROGATE, readChar())
        assertEquals(Char.MAX_SURROGATE, readChar())
        assertEquals(Char.MIN_LOW_SURROGATE, readChar())
        assertEquals(Char.MAX_LOW_SURROGATE, readChar())
        assertEquals(Char.MIN_HIGH_SURROGATE, readChar())
        assertEquals(Char.MAX_HIGH_SURROGATE, readChar())
        assertEquals('0', readChar())
        assertEquals("", readString())
        assertEquals("str", readString())
    }

    private fun DataOutput.writeTestData() {
        writeByte(Byte.MIN_VALUE)
        writeByte(0)
        writeByte(Byte.MAX_VALUE)
        writeBoolean(false)
        writeBoolean(true)
        writeShort(Short.MIN_VALUE)
        writeShort(0)
        writeShort(Short.MAX_VALUE)
        writeInt(Int.MIN_VALUE)
        writeInt(0)
        writeInt(Int.MAX_VALUE)
        writeLong(Long.MIN_VALUE)
        writeLong(0)
        writeLong(Long.MAX_VALUE)
        writeFloat(Float.NEGATIVE_INFINITY)
        writeFloat(Float.POSITIVE_INFINITY)
        writeFloat(Float.MIN_VALUE)
        writeFloat(Float.MAX_VALUE)
        writeFloat(-Float.MAX_VALUE)
        writeFloat(Float.NaN)
        writeFloat(0F)
        writeFloat(1F)
        writeFloat(-1F)
        writeDouble(Double.NEGATIVE_INFINITY)
        writeDouble(Double.POSITIVE_INFINITY)
        writeDouble(Double.MIN_VALUE)
        writeDouble(Double.MAX_VALUE)
        writeDouble(-Double.MAX_VALUE)
        writeDouble(Double.NaN)
        writeDouble(0.0)
        writeDouble(1.0)
        writeDouble(-1.0)
        writeChar(Char.MIN_VALUE)
        writeChar(Char.MAX_VALUE)
        writeChar(Char.MIN_SURROGATE)
        writeChar(Char.MAX_SURROGATE)
        writeChar(Char.MIN_LOW_SURROGATE)
        writeChar(Char.MAX_LOW_SURROGATE)
        writeChar(Char.MIN_HIGH_SURROGATE)
        writeChar(Char.MAX_HIGH_SURROGATE)
        writeChar('0')
        writeString("")
        writeString("str")
    }
}
