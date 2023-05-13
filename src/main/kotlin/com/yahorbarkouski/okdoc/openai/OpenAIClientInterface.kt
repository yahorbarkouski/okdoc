package com.yahorbarkouski.okdoc.openai

import com.yahorbarkouski.okdoc.openai.request.ChatRequest
import com.yahorbarkouski.okdoc.openai.response.ChatResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

internal interface OpenAIClientInterface {

    @Headers("Content-Type: application/json")
    @POST("chat/completions")
    fun complete(@Body request: ChatRequest): Call<ChatResponse>
}
