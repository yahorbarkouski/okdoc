package com.yahorbarkouski.okdoc.plugin

import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import org.jetbrains.uast.UIdentifier
import org.jetbrains.uast.toUElement

class DocumentationBuilderIntention : IntentionAction {

    override fun startInWriteAction(): Boolean = true

    override fun getText(): String = "Insert AI-generated documentation"

    override fun getFamilyName(): String = FAMILY_NAME

    override fun isAvailable(project: Project, editor: Editor?, file: PsiFile?): Boolean {
        if (editor == null || file == null) {
            return false
        }
        val element = file.findElementAt(editor.caretModel.offset)?.toUElement() ?: return false
        if (element is UIdentifier) {
            return element.uastParent?.comments?.isEmpty() == true
        }
        return false
    }

    override fun invoke(project: Project, editor: Editor, file: PsiFile) {
        val element = file.findElementAt(editor.caretModel.offset)!!.parent!!
        DocumentationBuilder.generateDocumentation(project, element)
    }

    companion object {

        const val FAMILY_NAME = "OkDok"
    }
}