package com.yahorbarkouski.okdoc.plugin.settings

import com.intellij.openapi.options.Configurable
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
    }

    override fun isModified(): Boolean {
        val settings = OkDocSettings.instance

        return settingsPanel?.apiToken != settings.state.apiToken
    }

    override fun reset() {
        val settings = OkDocSettings.instance

        settingsPanel?.apiToken = settings.state.apiToken
    }
}