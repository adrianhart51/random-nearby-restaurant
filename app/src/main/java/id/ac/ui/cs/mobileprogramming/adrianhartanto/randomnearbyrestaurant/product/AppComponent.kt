package id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.product

import dagger.Component
import id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.MainActivity
import id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.utils.UtilsModule
import javax.inject.Singleton

@Component(modules = [AppModule::class, UtilsModule::class])
@Singleton
interface AppComponent {

    fun doInjection(mainActivity: MainActivity)

}