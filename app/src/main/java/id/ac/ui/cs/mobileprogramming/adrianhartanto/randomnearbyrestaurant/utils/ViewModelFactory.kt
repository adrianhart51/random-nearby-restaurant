package id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.utils

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.ui.main.MainViewModel
import javax.inject.Inject

class ViewModelFactory @Inject
constructor(private val repository: Repository) : ViewModelProvider.Factory {

    @NonNull
    override fun <T : ViewModel> create(@NonNull modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}