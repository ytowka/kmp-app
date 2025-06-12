package com.example.feature.auth.domain.usecase

import com.example.feature.auth.domain.dto.TokenPairDto
import com.example.feature.auth.domain.repository.AccessTokenDto
import com.example.feature.auth.domain.repository.AuthRepository
import com.example.network.auth.IAuthTokenProvider
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.koin.core.annotation.Single
import kotlin.concurrent.Volatile
import kotlin.time.Clock.*
import kotlin.time.ExperimentalTime

@Single(binds = [IAuthTokenProvider::class])
class AuthTokenProvider(
    val authRepository: AuthRepository
) : IAuthTokenProvider {

    val mutex = Mutex()

    @Volatile
    var currentToken: AccessTokenDto? = null

    override suspend fun getToken(): String? {
        val accessToken = getAccessToken()
        if(accessToken != null){
            return accessToken
        }

        mutex.withLock {
            val accessToken = getAccessToken()
            if(accessToken != null){
                return accessToken
            }

            val refreshToken = getRefreshToken()
            return refreshToken?.accessToken
        }
    }


    override suspend fun refreshAndGetToken() : String? {
        mutex.withLock {
            val refreshToken = getRefreshToken()
            return refreshToken?.accessToken
        }
    }


    private suspend fun getRefreshToken(): TokenPairDto? {
        val refreshToken = authRepository.getRefreshToken() ?: return null


        return try{
            authRepository.getNewToken(refreshToken)
        }catch(e: Exception){
            null
        }
    }


    @OptIn(ExperimentalTime::class)
    private fun AccessTokenDto.isFresh() = expiresIn > System.now().toEpochMilliseconds() + EXPIRATION_THRESHOLD_MILLIS

    private suspend fun getAccessToken(): String?{
        var token = currentToken?.takeIf { it.isFresh() }
        if(token == null){
            token = authRepository.getAccessToken().first()?.takeIf { it.isFresh() }
            currentToken = token
        }
        return token?.token
    }

    companion object{
        const val EXPIRATION_THRESHOLD_MILLIS = 3_000
    }
}