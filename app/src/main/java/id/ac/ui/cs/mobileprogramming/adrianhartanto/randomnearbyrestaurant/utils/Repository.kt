package id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.utils

import com.google.gson.JsonElement
import io.reactivex.Observable

class Repository(private val apiCall: ApiCall) {

    fun executeLogin(mobileNumber: String, password: String): Observable<JsonElement> {
        return apiCall.login(mobileNumber, password)
    }

    fun executeGetCuisines(latitude: Double, longitude: Double): Observable<JsonElement> {
        return apiCall.getCuisines(latitude, longitude)
    }

    fun executeSearchRestaurants(latitude: Double, longitude: Double, cuisinesId: String): Observable<JsonElement> {
        return apiCall.searchRestaurants(latitude, longitude, cuisinesId)
    }
}