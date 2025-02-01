package nz.co.example.nbamodule.features.api.di


import kotlinx.serialization.json.Json
import nz.co.example.nbamodule.BuildConfig
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://api.balldontlie.io/v1/"
private const val AUTHORIZATION_TOKEN = BuildConfig.API_KEY

private val json by lazy { Json { ignoreUnknownKeys = true } }

val apiModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>(named("authInterceptor")))
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .retryOnConnectionFailure(true)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    single(named("authInterceptor")) {
        Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", AUTHORIZATION_TOKEN)
                .addHeader("Accept", "application/json")
                .build()
            chain.proceed(request)
        }
    }
}