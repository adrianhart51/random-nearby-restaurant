package id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.utils

import com.google.gson.JsonElement
import id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.config.Urls
import io.reactivex.Observable
import retrofit2.http.*

interface ApiCall {
    @FormUrlEncoded
    @POST(Urls.ZOMATO_API_URL)
    fun login(@Field("mobile") mobileNumber: String, @Field("password") password: String): Observable<JsonElement>

    @GET("v2.1/cuisines")
    fun getCuisines(@Query("lat") latitude: Double,
                    @Query("lon") longitude: Double): Observable<JsonElement>

    @GET("v2.1/search")
    fun searchRestaurants(@Query("lat") latitude: Double,
                          @Query("lon") longitude: Double,
                          @Query("cuisines") cuisinesId: Int,
                          @Query("sort") sort: String = "real_distance",
                          @Query("order") order: String = "asc"): Observable<JsonElement>
}