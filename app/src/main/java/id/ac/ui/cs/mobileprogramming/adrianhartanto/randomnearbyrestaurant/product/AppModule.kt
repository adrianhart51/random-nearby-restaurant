package id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.product

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    internal fun provideContext(): Context {
        return context
    }
}