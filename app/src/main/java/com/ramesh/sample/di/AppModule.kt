package com.ramesh.sample.di

import android.content.Context
import androidx.room.Room
import com.ramesh.sample.BuildConfig
import com.ramesh.sample.data.local.AppDatabase
import com.ramesh.sample.data.local.MovieDao
import com.ramesh.sample.data.remote.MovieApi
import com.ramesh.sample.data.repository.MovieRepositoryImpl
import com.ramesh.sample.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
        return OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor())
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(okHttpClient: OkHttpClient): MovieApi =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.OMDB_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext ctx: Context): AppDatabase =
        Room.databaseBuilder(ctx, AppDatabase::class.java, "movies.db").build()

    @Provides
    fun provideMovieDao(db: AppDatabase): MovieDao = db.movieDao()

    @Provides
    @Singleton
    fun provideRepo(
        api: MovieApi,
        dao: MovieDao
    ): MovieRepository = MovieRepositoryImpl(api, dao)
}
