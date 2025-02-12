package com.arkivanov.essenty.lifecycle

import com.arkivanov.essenty.lifecycle.Lifecycle.Callbacks
import com.arkivanov.essenty.lifecycle.Lifecycle.State
import com.arkivanov.essenty.utils.internal.ensureNeverFrozen

internal class LifecycleRegistryImpl(initialState: State) : LifecycleRegistry {

    init {
        ensureNeverFrozen()
    }

    private var callbacks = emptySet<Callbacks>()
    private var _state = initialState
    override val state: State get() = _state

    override fun subscribe(callbacks: Callbacks) {
        check(callbacks !in this.callbacks) { "Already subscribed" }

        this.callbacks += callbacks

        val state = _state
        if (state >= State.CREATED) {
            callbacks.onCreate()
        }
        if (state >= State.STARTED) {
            callbacks.onStart()
        }
        if (state >= State.RESUMED) {
            callbacks.onResume()
        }
    }

    override fun unsubscribe(callbacks: Callbacks) {
        this.callbacks -= callbacks
    }

    override fun onCreate() {
        checkState(State.INITIALIZED)
        _state = State.CREATED
        callbacks.forEach(Callbacks::onCreate)
    }

    override fun onStart() {
        checkState(State.CREATED)
        _state = State.STARTED
        callbacks.forEach(Callbacks::onStart)
    }

    override fun onResume() {
        checkState(State.STARTED)
        _state = State.RESUMED
        callbacks.forEach(Callbacks::onResume)
    }

    override fun onPause() {
        checkState(State.RESUMED)
        _state = State.STARTED
        callbacks.reversed().forEach(Callbacks::onPause)
    }

    override fun onStop() {
        checkState(State.STARTED)
        _state = State.CREATED
        callbacks.reversed().forEach(Callbacks::onStop)
    }

    override fun onDestroy() {
        checkState(State.CREATED)
        _state = State.DESTROYED
        callbacks.reversed().forEach(Callbacks::onDestroy)
        callbacks = emptySet()
    }

    private fun checkState(required: State) {
        check(_state == required) { "Expected state $required but was $_state" }
    }
}
