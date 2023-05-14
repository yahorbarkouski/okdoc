package com.yahorbarkouski.okdoc.plugin.settings

import com.intellij.openapi.options.Configurable
import com.yahorbarkouski.okdoc.openai.request.ChatModel
import javax.swing.JComponent

class OkDocConfigurable : Configurable {

    private var settingsPanel: OkDocSettingsPanel? = null

    override fun createComponent(): JComponent {
        settingsPanel = OkDocSettingsPanel()
        return settingsPanel!!.getPanel()
    }

    override fun getDisplayName(): String = "OkDoc"

    override fun apply() {
        val settings = OkDocSettings.instance

        settings.state.apiToken = settingsPanel?.apiToken ?: ""
        settings.state.prompt = settingsPanel?.prompt ?: ""
        settings.state.model = ChatModel.valueOf(settingsPanel?.model ?: ChatModel.GPT_3_5_TURBO.name)
        settings.state.temperature = settingsPanel?.temperature ?: 1.0
    }

    override fun isModified(): Boolean {
        val settings = OkDocSettings.instance

        return settingsPanel?.apiToken != settings.state.apiToken ||
                settingsPanel?.prompt != settings.state.prompt ||
                settingsPanel?.model != settings.state.model.name ||
                settingsPanel?.temperature != settings.state.temperature
    }

    override fun reset() {
        val settings = OkDocSettings.instance

        settingsPanel?.apiToken = settings.state.apiToken
        settingsPanel?.prompt = settings.state.prompt
        settingsPanel?.model = settings.state.model.name
        settingsPanel?.temperature = settings.state.temperature
    }
}