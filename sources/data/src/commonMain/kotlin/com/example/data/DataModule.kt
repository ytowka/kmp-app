package com.example.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.example.data")
class DataModule {

    @Single
    fun datastore(
        dataStoreFactory: DataStoreFactory
    ) : DataStore<Preferences> {
        return dataStoreFactory.getDatastore()
    }
}