package com.example.feature

import com.example.feature.auth.AuthModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [AuthModule::class])
@ComponentScan(value = ["com.example.feature"])
class FeatureModule