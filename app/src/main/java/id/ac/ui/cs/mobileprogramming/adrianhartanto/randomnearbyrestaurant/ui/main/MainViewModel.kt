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
    private val responseLiveData = MutableLiveData<ApiResponse>()


    fun loginResponse(): MutableLiveData<ApiResponse> {
        return responseLiveData
    }

    fun hitLoginApi(mobileNumber: String, password: String) {

        disposables.add(repository.executeLogin(mobileNumber, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { d -> responseLiveData.setValue(ApiResponse.loading()) }
            .subscribe(
                { result -> responseLiveData.setValue(ApiResponse.success(result)) },
                { throwable -> responseLiveData.setValue(ApiResponse.error(throwable)) }
            ))

    }

    fun getCuisinesResponse(): MutableLiveData<ApiResponse> {
        return responseLiveData
    }

    fun hitGetCuisinesApi(latitude: Double, longitude: Double) {
        disposables.add(repository.executeGetCuisines(latitude, longitude)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { d -> responseLiveData.setValue(ApiResponse.loading()) }
            .subscribe(
                { result -> responseLiveData.setValue(ApiResponse.success(result)) },
                { throwable -> responseLiveData.setValue(ApiResponse.error(throwable)) }
            ))
    }

    override fun onCleared() {
        disposables.clear()
    }
}
