package com.sourendra.kotlinwithhilt.di

import com.sourendra.kotlinwithhilt.repository.MainRepository
import com.sourendra.kotlinwithhilt.retrofit.ApiInterface
import com.sourendra.kotlinwithhilt.retrofit.NetworkMapper
import com.sourendra.kotlinwithhilt.room.BlogDao
import com.sourendra.kotlinwithhilt.room.CacheMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
     fun provideRepository(
        blogDao: BlogDao,
         apiInterface: ApiInterface,
         cacheMapper: CacheMapper,
        networkMapper: NetworkMapper
    ) : MainRepository{
        return MainRepository(blogDao, apiInterface, cacheMapper, networkMapper)
    }
}