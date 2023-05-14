package com.yahorbarkouski.okdoc.plugin.settings

import com.intellij.openapi.ui.ComboBox
import com.intellij.util.ui.JBUI
import com.yahorbarkouski.okdoc.openai.request.ChatModel
import java.awt.BorderLayout
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.util.Hashtable
import javax.swing.*

class OkDocSettingsPanel {

    private val apiTokenTextField = JPasswordField()
    var apiToken: String
        get() = String(apiTokenTextField.password).trim()
        set(value) {
            apiTokenTextField.text = value
        }

    private val modelComboBox = ComboBox(ChatModel.values().map { it.name }.toTypedArray()).apply {
        isEditable = false
        selectedItem = ChatModel.GPT_3_5_TURBO.name
    }
    var model: String
        get() = modelComboBox.selectedItem!!.toString().trim()
        set(value) {
            modelComboBox.selectedItem = value
        }

    private val temperatureSlider = JSlider(0, 20, 10).apply {
        val labelTable = Hashtable<Int, JLabel>()
        for (i in 0..10) {
            labelTable[i * 2] = JLabel((i * 2 / 10.0).toString())
        }
        this.labelTable = labelTable
        this.paintTicks = true
        this.paintLabels = true
        this.majorTickSpacing = 2
    }
    var temperature: Double
        get() = temperatureSlider.value / 10.0
        set(value) {
            temperatureSlider.value = (value * 10).toInt()
        }

    private val promptTextArea = JTextArea(5, 20).apply {
        lineWrap = true
        wrapStyleWord = true
    }
    var prompt: String
        get() = promptTextArea.text.trim()
        set(value) {
            promptTextArea.text = value
        }

    fun getPanel(): JPanel {
        val contentPanel = JPanel(GridBagLayout())
        val constraints = GridBagConstraints().apply {
            fill = GridBagConstraints.HORIZONTAL
            anchor = GridBagConstraints.WEST
            weightx = 1.0
            insets = JBUI.insets(4)
        }

        addComponent(contentPanel, "OpenAI API Token:", apiTokenTextField, constraints)
        addComponent(contentPanel, "OpenAI Completion Model:", modelComboBox, constraints)
        addComponent(contentPanel, "Temperature:", temperatureSlider, constraints)
        addComponent(contentPanel, "Prompt:", JScrollPane(promptTextArea), constraints)

        return JPanel(BorderLayout()).apply {
            add(contentPanel, BorderLayout.NORTH)
        }
    }

    private fun addComponent(
        panel: JPanel,
        labelText: String,
        component: JComponent,
        constraints: GridBagConstraints
    ) {
        constraints.apply {
            gridx = 0; gridy++; panel.add(JLabel(labelText), this)
            gridx = 1; panel.add(component, this)
        }
    }
}
