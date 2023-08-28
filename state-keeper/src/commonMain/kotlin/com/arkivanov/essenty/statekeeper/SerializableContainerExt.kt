package com.arkivanov.essenty.statekeeper

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.protobuf.ProtoBuf

fun <T : Any> SerializableContainer(value: T, strategy: SerializationStrategy<T>): SerializableContainer =
    SerializableContainer().apply {
        set(value = value, strategy = strategy)
    }

@OptIn(ExperimentalSerializationApi::class)
internal fun SerializableContainer.encodeToByteArray(): ByteArray =
    ProtoBuf.encodeToByteArray(serializer = SerializableContainer.serializer(), value = this)

@OptIn(ExperimentalSerializationApi::class)
internal fun SerializableContainer.Companion.decodeFromByteArray(data: ByteArray): SerializableContainer =
    ProtoBuf.decodeFromByteArray(deserializer = serializer(), bytes = data)
