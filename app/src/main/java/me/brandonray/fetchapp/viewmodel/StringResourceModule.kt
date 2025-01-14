package me.brandonray.fetchapp.viewmodel

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.brandonray.fetchapp.R
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StringResourceModule {

    @Provides
    @Singleton
    @Named("lastUpdated")
    fun provideLastUpdatedString(@ApplicationContext context: Context): String {
        return context.getString(R.string.last_updated)
    }
}