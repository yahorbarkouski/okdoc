package com.yahorbarkouski.okdoc.openai

import com.yahorbarkouski.okdoc.openai.request.ChatMessage
import com.yahorbarkouski.okdoc.openai.request.ChatModel
import com.yahorbarkouski.okdoc.openai.request.ChatRequest
import com.yahorbarkouski.okdoc.openai.response.ChatResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Duration

class OpenAIClient(apiKey: String) {

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer $apiKey")
                .build()
            chain.proceed(request)
        }
        .callTimeout(Duration.ofMinutes(1))
        .readTimeout(Duration.ofSeconds(30))
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openai.com/v1/")
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(OpenAIClientInterface::class.java)

    fun generateDocumentation(codeText: String): Response<ChatResponse> {
        val prompt = """
            You're a smart documentation builder.
            Given the following code, write clean, concise javadoc/kdoc for it.
            $codeText
        """.trimIndent()

        return complete(listOf(ChatMessage(content = prompt)))
    }

    private fun complete(
        messages: List<ChatMessage>,
        model: ChatModel = ChatModel.GPT_3_5_TURBO,
    ): Response<ChatResponse> {
        val requestBody = ChatRequest(model = model.id, messages = messages)
        return apiService.complete(requestBody).execute()
    }
}
