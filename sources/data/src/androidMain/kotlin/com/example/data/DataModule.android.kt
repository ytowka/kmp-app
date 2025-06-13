package com.example.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
actual class DataModule {

    @Single
    fun datastore(
        context: Context
    ) : DataStore<Preferences> {
        return createDataStore(
            producePath = { context.filesDir.resolve(dataStoreFileName).absolutePath }
        )
    }
}