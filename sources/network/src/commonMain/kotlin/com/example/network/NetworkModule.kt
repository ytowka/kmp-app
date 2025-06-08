package com.example.network

import com.example.network.auth.AuthPluginConfig
import com.example.network.auth.authPlugin
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Module
class NetworkModule {

    @Single
    fun httpClient(
        authPluginConfig: AuthPluginConfig
    ): HttpClient {
        return HttpClient {
            defaultRequest {
                url(baseUrl)
                contentType(ContentType.Application.Json)
            }
            install(ContentNegotiation){
                json()
            }
            install(authPlugin {
               authPluginConfig
            })
            install(Logging)
        }
    }

    @Single
    @Named("auth")
    fun authHttpClient(): HttpClient {
        return HttpClient {
            defaultRequest {
                url(baseUrl)
                contentType(ContentType.Application.Json)
            }
            install(ContentNegotiation){
                json()
            }
            install(Logging)
        }
    }

    companion object {
        //const val baseUrl = "http://192.168.110.49:8080" // hotspot
        //const val baseUrl = "http://192.168.0.178:8080" // home
        const val baseUrl = "http://3504899-nf03819.twc1.net:8080" // timeweb
        //const val baseUrl = "http://10.6.63.137:8080" // surf
    }
}
