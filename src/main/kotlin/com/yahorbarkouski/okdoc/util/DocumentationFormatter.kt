package com.yahorbarkouski.okdoc.util

import com.yahorbarkouski.okdoc.openai.response.ChatResponse
import retrofit2.Response

fun Response<ChatResponse>.toDocumentation(tabulation: Int = 0): String {
    val content = extractNonNullContent()

    if (content.contains("/**")) {
        println("OpenAI response contains documentation. Extracting...")
        val extractedDocumentation = content.substringAfter("/**").substringBefore("*/")
        val result = "/**$extractedDocumentation*/".acceptTabulation(tabulation)
        println("Documentation:\n$result")
        return result
    }

    println("OpenAI response doesn't wrap documentation by /**. Simple return.")
    val result = content.acceptTabulation(tabulation)
    println("Documentation:\n$result")
    return result
}

private fun String.acceptTabulation(tabulation: Int): String {
    return lines().joinToString("\n") { "${"\t".repeat(tabulation)}$it" }
}

private fun Response<ChatResponse>.extractNonNullContent(): String {
    val body = body()
    requireNotNull(body) { "Response body is empty, can't build content" }
    val message = body.choices.first().message
    requireNotNull(message) { "Message body is null, can't build content" }
    return message.content.trim()
}
