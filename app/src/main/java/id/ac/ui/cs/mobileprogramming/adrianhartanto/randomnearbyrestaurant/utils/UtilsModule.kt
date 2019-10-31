package id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.utils

import androidx.lifecycle.ViewModelProvider
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.config.Keys
import id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.config.Urls
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class UtilsModule {

    @Provides
    @Singleton
    internal fun getRequestHeader(): OkHttpClient {

        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("user-key", Keys.ZOMATO_API_KEY)
                .build()
            chain.proceed(request)
        }
            .connectTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .readTimeout(300, TimeUnit.SECONDS)

        return httpClient.build()
    }

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        val builder =
            GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return builder.setLenient().create()
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl(Urls.ZOMATO_API_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    internal fun getApiCall(retrofit: Retrofit): ApiCall {
        return retrofit.create<ApiCall>(ApiCall::class.java)
    }

    @Provides
    @Singleton
    internal fun getRepository(ApiCall: ApiCall): Repository {
        return Repository(ApiCall)
    }

    @Provides
    @Singleton
    internal fun getViewModelFactory(myRepository: Repository): ViewModelProvider.Factory {
        return ViewModelFactory(myRepository)
    }
}
