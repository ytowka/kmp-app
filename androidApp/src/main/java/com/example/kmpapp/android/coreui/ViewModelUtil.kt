package com.example.kmpapp.android.coreui

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import org.koin.compose.LocalKoinScope
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.parameter.ParametersDefinition
import org.koin.viewmodel.factory.AndroidParametersHolder
import kotlin.reflect.KClass

@OptIn(KoinInternalApi::class)
@Composable
inline fun <reified T : ViewModel> injectViewModel(
    viewModelStore: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current),
    noinline parameters: ParametersDefinition? = null,
): T {
    val koinScope = LocalKoinScope.current
    return ViewModelProvider.create(
        owner = viewModelStore,
        factory = object : ViewModelProvider.Factory {
            override fun <V : ViewModel> create(modelClass: Class<V>): V {
                return koinScope.get(T::class)
            }

            override fun <V : ViewModel> create(modelClass: KClass<V>, extras: CreationExtras): V {
                val androidParams = AndroidParametersHolder(parameters, extras)
                return koinScope.getWithParameters(
                    clazz = T::class,
                    parameters = androidParams,
                )
            }
        }
    ).get(T::class)
}