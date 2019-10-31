package id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.JsonElement
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        (getApplication() as AppApplication).appComponent.doInjection(this)

        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)

        mainViewModel.getCuisinesResponse().observe(this,
            Observer<ApiResponse> { apiResponse -> consumeResponse(apiResponse) })

        button_get_cuisines.setOnClickListener { view ->
            mainViewModel.hitGetCuisinesApi(-6.595038, 106.816635)
        }
    }

    private fun consumeResponse(apiResponse: ApiResponse) {

        when (apiResponse.status) {

            ApiResponseStatus.SUCCESS -> {
                renderSuccessResponse(apiResponse.data)
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

    private fun renderSuccessResponse(response: JsonElement?) {
        if (!response?.isJsonNull!!) {
            Log.d("response=", response.toString())
        } else {
            Toast.makeText(
                this@MainActivity,
                resources.getString(R.string.error_string),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
