package com.arkivanov.essenty.statekeeper

import kotlinx.serialization.Serializable
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

@Suppress("TestFunctionName")
class SerializableContainerTest {

    @Test
    fun GIVEN_container_with_data_WHEN_serializeAndDeserialize_THEN_data_restored() {
        val data = getData()
        val container = SerializableContainer(value = data, strategy = Config.serializer())

        val restoredData = container.serializeAndDeserialize().serializeAndDeserialize().consume(strategy = Config.serializer())

        assertEquals(data, restoredData)
    }

    @Test
    fun GIVEN_container_without_data_WHEN_serializeAndDeserialize_THEN_data_null() {
        val container = SerializableContainer()

        val restoredData = container.serializeAndDeserialize().serializeAndDeserialize().consume(strategy = Config.serializer())

        assertNull(restoredData)
    }

    private fun getData(): Data =
        Data(value = "value")

    @Serializable
    sealed interface Config

    @Serializable
    data class Data(val value: String) : Config
}
