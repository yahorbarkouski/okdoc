package com.yahorbarkouski.okdoc.plugin.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.service

@State(name = "OkDocSettings", storages = [Storage("okdocSettings.xml")])
class OkDocSettings : PersistentStateComponent<OkDocSettings.State> {
    data class State(var apiUrl: String = "", var apiToken: String = "")

    private var state = State()

    override fun getState(): State = state

    override fun loadState(state: State) {
        this.state = state
    }

    companion object {
        val instance: OkDocSettings
            get() = service<OkDocSettings>()
    }
}