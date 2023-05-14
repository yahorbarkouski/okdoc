package com.yahorbarkouski.okdoc.openai

import com.yahorbarkouski.okdoc.openai.request.ChatRequest
import com.yahorbarkouski.okdoc.openai.response.ChatResponse
import java.time.Duration
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

    fun complete(request: ChatRequest): Response<ChatResponse> {
        return apiService.complete(request).execute()
    }
}
