package id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.dao

data class SearchRestaurantResult(
    val restaurants: List<Restaurant>,
    val results_found: Int,
    val results_shown: Int,
    val results_start: Int
)