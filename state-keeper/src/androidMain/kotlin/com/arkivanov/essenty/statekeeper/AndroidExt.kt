package com.arkivanov.essenty.statekeeper

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryOwner

private const val KEY_STATE = "STATE_KEEPER_STATE"

/**
 * Creates a new instance of [StateKeeper] and attaches it to the provided AndroidX [SavedStateRegistry].
 *
 * @param isSavingAllowed called before saving the state.
 * When `true` then the state will be saved, otherwise it won't. Default value is `true`.
 */
fun StateKeeper(
    savedStateRegistry: SavedStateRegistry,
    isSavingAllowed: () -> Boolean = { true }
): StateKeeper {
    @Suppress("DEPRECATION")
    val dispatcher =
        StateKeeperDispatcher(
            savedState = savedStateRegistry
                .consumeRestoredStateForKey(KEY_STATE)
                ?.apply { classLoader = ParcelableContainer::class.java.classLoader }
                ?.getParcelable<ParcelableContainer>(KEY_STATE)
                ?.container,
        )

    savedStateRegistry.registerSavedStateProvider(KEY_STATE) {
        Bundle().apply {
            if (isSavingAllowed()) {
                putParcelable(KEY_STATE, ParcelableContainer(dispatcher.save()))
            }
        }
    }

    return dispatcher
}

private class ParcelableContainer(
    val container: SerializableContainer,
) : Parcelable {

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeByteArray(container.encodeToByteArray())
    }

    companion object CREATOR : Parcelable.Creator<ParcelableContainer> {
        override fun createFromParcel(parcel: Parcel): ParcelableContainer =
            ParcelableContainer(SerializableContainer.decodeFromByteArray(requireNotNull(parcel.createByteArray())))

        override fun newArray(size: Int): Array<ParcelableContainer?> =
            arrayOfNulls(size)
    }
}

/**
 * Creates a new instance of [StateKeeper] and attaches it to the AndroidX [SavedStateRegistry].
 *
 * @param isSavingAllowed called before saving the state.
 * When `true` then the state will be saved, otherwise it won't. Default value is `true`.
 */
fun SavedStateRegistryOwner.stateKeeper(isSavingAllowed: () -> Boolean = { true }): StateKeeper =
    StateKeeper(
        savedStateRegistry = savedStateRegistry,
        isSavingAllowed = isSavingAllowed
    )
