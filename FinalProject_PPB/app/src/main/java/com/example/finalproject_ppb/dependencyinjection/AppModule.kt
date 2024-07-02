package com.example.finalproject_ppb.dependencyinjection

import android.app.Application
import androidx.room.Room
import com.example.finalproject_ppb.data.AppDB
import com.example.finalproject_ppb.data.AppRepository
import com.example.finalproject_ppb.data.AppRepositoryImpl
import com.example.finalproject_ppb.data.api.ClassifierApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDB(application: Application): AppDB{
        return  Room.databaseBuilder(
            application,
            AppDB::class.java,
            "app_db"
        ).createFromAsset("app.db").build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient{
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): ClassifierApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://192.168.1.113:5000")
            .client(okHttpClient)
            .build()
            .create(ClassifierApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAppRepository(
        db: AppDB,
        classifierApi: ClassifierApi
    ): AppRepository{
        return AppRepositoryImpl(db.dao, classifierApi)
    }
}