package com.example.network.auth

import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.request.request
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.utils.io.InternalAPI

class AuthPluginConfig {
    lateinit var authTokenProvider: IAuthTokenProvider
    lateinit var logoutUseCase: ILogoutUseCase
}

const val AUTH_PLUGIN = "AuthPlugin"

@OptIn(InternalAPI::class)
fun authPlugin(configure: () -> AuthPluginConfig) = createClientPlugin(AUTH_PLUGIN, configure) {
    val authTokenProvider: IAuthTokenProvider = pluginConfig.authTokenProvider
    val logoutUseCase: ILogoutUseCase = pluginConfig.logoutUseCase

    onRequest { request, _ ->
        val token = authTokenProvider.getToken()
        token?.let {
            request.headers.append(HttpHeaders.Authorization, "Bearer $it")
        }
    }

    onResponse { response ->
        if (response.status == HttpStatusCode.Unauthorized) {
            val newToken = authTokenProvider.refreshAndGetToken()
            val originalRequest = response.call.request

            if (newToken != null) {
                val newCall = this@createClientPlugin.client.request {
                    url(originalRequest.url)
                    method = originalRequest.method
                    headers.appendAll(originalRequest.headers)
                    body = originalRequest.content
                    headers[HttpHeaders.Authorization] = "Bearer $newToken"
                }
                if (newCall.status == HttpStatusCode.Unauthorized) {
                    logoutUseCase()
                }
            } else {
                logoutUseCase()
            }
        }
    }
}