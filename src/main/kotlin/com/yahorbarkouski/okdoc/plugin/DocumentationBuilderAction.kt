package com.yahorbarkouski.okdoc.plugin

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys.PSI_FILE
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.codeStyle.CodeFormatterFacade
import org.jetbrains.uast.UClass
import org.jetbrains.uast.toUElement

class DocumentationBuilderAction : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        val parentClass = extractParentClass(event) ?: return
        val publicMethods = extractPublicMethodsWithoutDocs(parentClass)

        publicMethods.forEach { DocumentationBuilder.generateDocumentation(project, it) }
        parentClass.run { DocumentationBuilder.generateDocumentation(project, sourcePsi!!) }
    }

    private fun extractParentClass(event: AnActionEvent): UClass? {
        val psiFile = event.getData(PSI_FILE) ?: return null
        return psiFile.children.firstNotNullOfOrNull { it.toUElement() } as? UClass
    }

    private fun extractPublicMethodsWithoutDocs(parentClass: UClass): Set<PsiElement> {
        return parentClass.methods
            .filter { !it.isConstructor && it.comments.isEmpty() }
            .mapNotNull { it.sourcePsi }
            .toSet()
    }
}
