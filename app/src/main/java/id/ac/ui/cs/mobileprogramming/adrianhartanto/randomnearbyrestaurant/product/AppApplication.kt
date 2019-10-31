package id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.product

import android.app.Application
import android.content.Context
import id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.utils.UtilsModule

class AppApplication : Application() {
    lateinit var appComponent: AppComponent
    lateinit var context: Context

    override fun onCreate() {
        super.onCreate()
        context = this
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).utilsModule(UtilsModule()).build()
    }

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
    }
}