package com.example.feature.auth

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.dsl.module

@Module
@ComponentScan(value = ["com.example.feature.auth"])
expect class AuthModule()