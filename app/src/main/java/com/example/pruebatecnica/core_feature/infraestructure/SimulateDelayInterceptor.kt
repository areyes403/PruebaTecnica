package com.example.pruebatecnica.core_feature.infraestructure

import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class SimulateDelayInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            TimeUnit.MILLISECONDS.sleep(4000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return chain.proceed(chain.request())
    }
}