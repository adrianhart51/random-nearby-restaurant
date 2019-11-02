package id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.google.gson.JsonElement
import id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.dao.Cuisine
import id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.dao.Restaurant
import id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.dao.SearchRestaurantResult
import id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.product.AppApplication
import id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.ui.main.MainViewModel
import id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.utils.ApiResponse
import id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.utils.ApiResponseStatus
import id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.utils.ViewModelFactory
import javax.inject.Inject
import kotlinx.android.synthetic.main.main_activity.*


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var mainViewModel: MainViewModel

    var gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        (getApplication() as AppApplication).appComponent.doInjection(this)

        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)

        mainViewModel.getCuisinesResponse().observe(this,
            Observer<ApiResponse> { apiResponse -> consumeGetCuisinesResponse(apiResponse) })

        mainViewModel.searchResultsResponse().observe(this,
            Observer<ApiResponse> { apiResponse -> consumeSearchResultsResponse(apiResponse) })

        mainViewModel.hitGetCuisinesApi(-6.595038, 106.816635)
    }

    private fun consumeGetCuisinesResponse(apiResponse: ApiResponse) {

        when (apiResponse.status) {

            ApiResponseStatus.SUCCESS -> {
                renderGetCuisines(apiResponse.data)
            }

            ApiResponseStatus.ERROR -> {
                Toast.makeText(
                    this@MainActivity,
                    resources.getString(R.string.error_string),
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> {
            }
        }
    }

    private fun consumeSearchResultsResponse(apiResponse: ApiResponse) {

        when (apiResponse.status) {

            ApiResponseStatus.SUCCESS -> {
                renderSearchRestaurants(apiResponse.data)
            }

            ApiResponseStatus.ERROR -> {
                Toast.makeText(
                    this@MainActivity,
                    resources.getString(R.string.error_string),
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> {
            }
        }
    }

    private fun renderGetCuisines(response: JsonElement?) {
        if (!response?.isJsonNull!!) {
            Log.d(
                "cuisines=",
                response.toString()
            )
            val cuisines = response.asJsonObject.get("cuisines").asJsonArray.map { cuisine ->
                gson.fromJson(
                    cuisine.asJsonObject.get("cuisine"),
                    Cuisine::class.java
                )
            }
            renderGetCuisinesSpinner(cuisines)
        } else {
            Toast.makeText(
                this@MainActivity,
                resources.getString(R.string.error_string),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun largeLog(tag: String, content: String) {
        if (content.length > 4000) {
            Log.d(tag, content.substring(0, 4000))
            largeLog(tag, content.substring(4000))
        } else {
            Log.d(tag, content)
        }
    }

    private fun renderSearchRestaurants(response: JsonElement?) {
        if (!response?.isJsonNull!!) {
            val searchRestaurantResult = gson.fromJson(response, SearchRestaurantResult::class.java)
            val restaurants = searchRestaurantResult.restaurants

            largeLog("Restaurants=", restaurants.toString())
            renderSearchRestaurantsGridView(restaurants)
        } else {
            Toast.makeText(
                this@MainActivity,
                resources.getString(R.string.error_string),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun renderGetCuisinesSpinner(cuisines: List<Cuisine>) {
        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, cuisines)
        spinner_cuisines.adapter = adapter
        spinner_cuisines.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val cuisine = parent.getItemAtPosition(position) as Cuisine
                Log.d("Cuisine selected", cuisine.cuisine_id.toString())
                mainViewModel.hitSearchRestaurantsApi(-6.595038, 106.816635, cuisine.cuisine_id)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    private fun renderSearchRestaurantsGridView(restaurants: List<Restaurant>) {
        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, restaurants)
        grid_view_search_results.adapter = adapter
        grid_view_search_results.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val restaurant = parent.getItemAtPosition(position)

                Log.d("Restaurant selected", restaurant.toString())
            }
    }
}
