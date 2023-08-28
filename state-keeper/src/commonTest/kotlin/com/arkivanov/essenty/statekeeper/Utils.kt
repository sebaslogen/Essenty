package com.arkivanov.essenty.statekeeper

internal fun SerializableContainer.serializeAndDeserialize(): SerializableContainer =
    SerializableContainer.decodeFromByteArray(encodeToByteArray())
