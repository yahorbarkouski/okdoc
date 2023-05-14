package com.yahorbarkouski.okdoc.plugin

import com.intellij.openapi.application.ApplicationManager.getApplication
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task.Backgroundable
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Computable
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.codeStyle.CodeStyleManager
import com.yahorbarkouski.okdoc.openai.OpenAIClient
import com.yahorbarkouski.okdoc.openai.request.ChatMessage
import com.yahorbarkouski.okdoc.openai.request.ChatRequest
import com.yahorbarkouski.okdoc.plugin.settings.OkDocSettings
import com.yahorbarkouski.okdoc.util.toDocumentation

object DocumentationBuilder {

    private val settings = OkDocSettings.instance.state
    private val client = OpenAIClient(settings.apiToken)

    fun generateDocumentation(project: Project, element: PsiElement) {
        ProgressManager.getInstance().run(
            object : Backgroundable(project, "Generating documentation") {
                override fun run(indicator: ProgressIndicator) {
                    val elementText = getApplication().runReadAction(Computable { element.text })
                    val response = client.complete(
                        ChatRequest(
                            model = settings.model.id,
                            temperature = settings.temperature,
                            messages = listOf(ChatMessage(content = settings.prompt + ": " + elementText))
                        )
                    )
                    val documentation = response.toDocumentation()

                    getApplication().invokeLater { insertDocumentation(element, documentation) }
                }
            }
        )
    }

    private fun insertDocumentation(element: PsiElement, documentation: String) {
        val factory = JavaPsiFacade.getElementFactory(element.project)
        val documentationElement = factory.createDocCommentFromText(documentation, element)

        WriteCommandAction.runWriteCommandAction(element.project) {
            // Distinguish between class elements and others to correctly position the documentation.
            val createdDoc = if (element is PsiClass) {
                element.addBefore(documentationElement, element.firstChild)
            } else {
                element.parent.addBefore(documentationElement, element)
            }

            // Formatting the code after adding the documentation.
            CodeStyleManager
                .getInstance(element.project)
                .reformat(createdDoc.containingFile)
        }
    }
}