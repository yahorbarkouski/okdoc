package com.yahorbarkouski.okdoc.plugin.settings

import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JPasswordField

class OkDocSettingsPanel {
    private val apiTokenTextField: JPasswordField = JPasswordField().apply { columns = 20 }

    var apiToken: String
        get() = String(apiTokenTextField.password).trim()
        set(value) {
            apiTokenTextField.text = value
        }

    fun getPanel(): JPanel {
        val contentPanel = JPanel(GridBagLayout())

        with(GridBagConstraints()) {
            fill = GridBagConstraints.HORIZONTAL
            anchor = GridBagConstraints.WEST
            gridx = 0; gridy = 0
            weightx = 1.0
            insets = JBUI.insets(4)

            contentPanel.add(JLabel("OpenAI API Token:"), this)
            gridy = 1; contentPanel.add(apiTokenTextField, this)
        }

        return JPanel(BorderLayout()).apply {
            add(contentPanel, BorderLayout.NORTH)
        }
    }
}