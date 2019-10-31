package id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.gson.JsonElement
import id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.product.AppApplication
import id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.ui.main.MainFragment
import id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.ui.main.MainViewModel
import id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.utils.ApiResponse
import id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.utils.ApiResponseStatus
import id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.utils.ViewModelFactory
import id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.utils.ViewModelFactory_Factory
import javax.inject.Inject
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        ButterKnife.bind(this)
        (getApplication() as AppApplication).appComponent.doInjection(this)

        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        mainViewModel.hitGetCuisinesApi(-6.595038, 106.816635)

        mainViewModel.getCuisinesResponse().observe(this,
            Observer<ApiResponse> { apiResponse -> consumeResponse(apiResponse) })
    }

    @OnClick(R.id.button_get_cuisines)
    fun onClickGetCuisines() {
        mainViewModel.hitGetCuisinesApi(-6.595038, 106.816635)
    }

    private fun consumeResponse(apiResponse: ApiResponse) {

        when (apiResponse.status) {

            ApiResponseStatus.SUCCESS -> {
                renderSuccessResponse(apiResponse.data)
            }

            ApiResponseStatus.ERROR -> {
                Toast.makeText(
                    this@MainActivity,
                    resources.getString(R.string.errorString),
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
                resources.getString(R.string.errorString),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
