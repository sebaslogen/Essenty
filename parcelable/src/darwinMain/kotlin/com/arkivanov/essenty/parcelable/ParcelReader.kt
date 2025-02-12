package com.arkivanov.essenty.parcelable

import com.arkivanov.essenty.utils.internal.ExperimentalEssentyApi
import com.arkivanov.parcelize.darwin.decodeParcelableOrNull
import com.arkivanov.parcelize.darwin.decodeStringOrNull
import platform.Foundation.NSCoder
import platform.Foundation.decodeBoolForKey
import platform.Foundation.decodeDoubleForKey
import platform.Foundation.decodeFloatForKey
import platform.Foundation.decodeInt32ForKey
import platform.Foundation.decodeInt64ForKey
import kotlin.reflect.KClass

/**
 * Allows serializing [Parcelable] classes when implementing custom parcelers using [CommonParceler].
 * Serialization is performed via [platform.Foundation.NSCoder].
 */
@ExperimentalEssentyApi
actual class ParcelReader internal constructor(internal val coder: NSCoder) {
    private var key = 0

    fun nextKey(): String = "parcel-key-${key++}"
}

@ExperimentalEssentyApi
actual fun ParcelReader.readBoolean(): Boolean =
    coder.decodeBoolForKey(key = nextKey())

@ExperimentalEssentyApi
actual fun ParcelReader.readInt(): Int =
    coder.decodeInt32ForKey(key = nextKey())

@ExperimentalEssentyApi
actual fun ParcelReader.readLong(): Long =
    coder.decodeInt64ForKey(key = nextKey())

@ExperimentalEssentyApi
actual fun ParcelReader.readFloat(): Float =
    coder.decodeFloatForKey(key = nextKey())

@ExperimentalEssentyApi
actual fun ParcelReader.readDouble(): Double =
    coder.decodeDoubleForKey(key = nextKey())

@ExperimentalEssentyApi
actual fun ParcelReader.readStringOrNull(): String? =
    coder.decodeStringOrNull(key = nextKey())

@ExperimentalEssentyApi
actual fun <T : Parcelable> ParcelReader.readParcelableOrNull(clazz: KClass<T>): T? =
    coder.decodeParcelableOrNull(key = nextKey())
