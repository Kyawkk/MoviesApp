package com.kyawzinlinn.moviesapp.data.di

import com.kyawzinlinn.moviesapp.data.api.CastApi
import com.kyawzinlinn.moviesapp.data.remote.repository_impl.CastRepositoryImpl
import com.kyawzinlinn.moviesapp.domain.repository.CastRepository
import com.kyawzinlinn.moviesapp.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CastApiModule {

    @Provides
    @Singleton
    fun provideCastApi(): CastApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CastApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCastRepository(api: CastApi): CastRepository{
        return CastRepositoryImpl(api)
    }

}