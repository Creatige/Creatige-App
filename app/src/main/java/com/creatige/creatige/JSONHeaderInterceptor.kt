package com.creatige.creatige


import okhttp3.Interceptor
import okhttp3.Response

class JSONHeaderInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header("accept", "application/json")
            .header("apikey", "Yz9NiA13BCeusyIxwwM_2w")
            .header("Content-Type", "application/json")
            .build()
        //todo: find out how to sent the prompt via this interceptor
        return chain.proceed(request)
    }
}