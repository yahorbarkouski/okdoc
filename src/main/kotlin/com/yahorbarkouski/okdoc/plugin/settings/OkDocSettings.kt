package com.yahorbarkouski.okdoc.plugin.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.service
import com.yahorbarkouski.okdoc.openai.request.ChatModel

@State(name = "OkDocSettings", storages = [Storage("okdocSettings.xml")])
class OkDocSettings : PersistentStateComponent<OkDocSettings.State> {
    data class State(
        var apiToken: String = "",
        var model: ChatModel = ChatModel.GPT_3_5_TURBO,
        var temperature: Double = 1.0,
        var prompt: String = """
            You're a smart documentation builder.
            Given the following code, write clean, concise javadoc/kdoc for it.
        """.trimIndent(),
    )

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