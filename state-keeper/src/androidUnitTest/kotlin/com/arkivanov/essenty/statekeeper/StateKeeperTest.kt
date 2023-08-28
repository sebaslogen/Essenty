package com.arkivanov.essenty.statekeeper

import android.os.Bundle
import android.os.Parcel
import androidx.lifecycle.LifecycleRegistry
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import kotlinx.serialization.Serializable
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.Test
import kotlin.test.assertEquals

@Suppress("TestFunctionName")
@RunWith(RobolectricTestRunner::class)
class StateKeeperTest {

    @Test
    fun WHEN_SavedStateRegistryOwner_recreated_THEN_state_restored() {
        val data = Data(value = 1)
        var owner = TestSavedStateRegistryOwner()
        owner.controller.performRestore(null)
        var keeper = StateKeeper(savedStateRegistry = owner.savedStateRegistry)
        keeper.register(key = "key", strategy = Data.serializer()) { data }

        var bundle = Bundle()
        owner.controller.performSave(bundle)
        bundle = bundle.serializeAndDeserialize()
        owner = TestSavedStateRegistryOwner()
        owner.controller.performRestore(bundle)
        keeper = StateKeeper(owner.savedStateRegistry)
        val restoredData = keeper.consume(key = "key", strategy = Data.serializer())

        assertEquals(data, restoredData)
    }

    private fun Bundle.serializeAndDeserialize(): Bundle {
        val parcel = Parcel.obtain()
        parcel.writeBundle(this)
        val bytes = parcel.marshall()
        parcel.unmarshall(bytes, 0, bytes.size)
        parcel.setDataPosition(0)

        return requireNotNull(parcel.readBundle())
    }

    @Serializable
    private data class Data(val value: Int)

    private class TestSavedStateRegistryOwner : SavedStateRegistryOwner {
        private val lifecycleRegistry = LifecycleRegistry(this)
        val controller: SavedStateRegistryController = SavedStateRegistryController.create(this)

        override fun getLifecycle(): LifecycleRegistry =
            lifecycleRegistry

        override fun getSavedStateRegistry(): SavedStateRegistry =
            controller.savedStateRegistry
    }
}
