package com.arkivanov.essenty.statekeeper

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.builtins.ByteArraySerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.protobuf.ProtoBuf

@Serializable(with = SerializableContainer.Serializer::class)
class SerializableContainer private constructor(
    private var data: ByteArray?,
) {

    constructor() : this(data = null)

    private var holder: Holder<*>? = null

    fun <T : Any> consume(strategy: DeserializationStrategy<T>): T? {
        holder?.also {
            holder = null

            @Suppress("UNCHECKED_CAST")
            return it.value as T?
        }

        data?.also {
            data = null

            @OptIn(ExperimentalSerializationApi::class)
            return ProtoBuf.decodeFromByteArray(deserializer = strategy, bytes = it)
        }

        return null
    }

    fun <T : Any> set(value: T, strategy: SerializationStrategy<T>) {
        holder = Holder(value = value, strategy = strategy)
        data = null
    }

    fun clear() {
        holder = null
        data = null
    }

    private class Holder<T : Any>(
        val value: T,
        val strategy: SerializationStrategy<T>,
    )

    internal object Serializer : KSerializer<SerializableContainer> {
        private val byteArraySerializer = ByteArraySerializer()

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(serialName = "SerializableContainer") {
                element<Boolean>(elementName = "exists")
                element(elementName = "data", descriptor = byteArraySerializer.descriptor, isOptional = true)
            }

        override fun serialize(encoder: Encoder, value: SerializableContainer) {
            val data: ByteArray? = value.holder?.encodeToByteArray() ?: value.data
            encoder.encodeBoolean(data != null)
            if (data != null) {
                encoder.encodeSerializableValue(serializer = byteArraySerializer, value = data)
            }
        }

        @OptIn(ExperimentalSerializationApi::class)
        private fun <T : Any> Holder<T>.encodeToByteArray(): ByteArray =
            ProtoBuf.encodeToByteArray(serializer = strategy, value = value)

        override fun deserialize(decoder: Decoder): SerializableContainer =
            SerializableContainer(data = decoder.takeIf(Decoder::decodeBoolean)?.decodeSerializableValue(byteArraySerializer))
    }
}
