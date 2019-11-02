package id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.utils.ApiResponse
import id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.utils.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val repository: Repository) : ViewModel() {
    private val disposables = CompositeDisposable()
    private val cuisinesResponseLiveData = MutableLiveData<ApiResponse>()
    private val searchResultsResponseLiveData = MutableLiveData<ApiResponse>()

    fun getCuisinesResponse(): MutableLiveData<ApiResponse> {
        return cuisinesResponseLiveData
    }

    fun searchResultsResponse(): MutableLiveData<ApiResponse> {
        return searchResultsResponseLiveData
    }

    fun hitGetCuisinesApi(latitude: Double, longitude: Double) {
        disposables.add(repository.executeGetCuisines(latitude, longitude)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { d -> cuisinesResponseLiveData.setValue(ApiResponse.loading()) }
            .subscribe(
                { result -> cuisinesResponseLiveData.setValue(ApiResponse.success(result)) },
                { throwable -> cuisinesResponseLiveData.setValue(ApiResponse.error(throwable)) }
            ))
    }

    fun hitSearchRestaurantsApi(latitude: Double, longitude: Double, cuisineId: Int) {
        disposables.add(repository.executeSearchRestaurants(latitude, longitude, cuisineId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { d -> searchResultsResponseLiveData.setValue(ApiResponse.loading()) }
            .subscribe(
                { result -> searchResultsResponseLiveData.setValue(ApiResponse.success(result)) },
                { throwable -> searchResultsResponseLiveData.setValue(ApiResponse.error(throwable)) }
            ))
    }

    override fun onCleared() {
        disposables.clear()
    }
}
