package id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.dao

data class Location(
    val address: String,
    val city: String,
    val city_id: Int,
    val country_id: Int,
    val latitude: String,
    val locality: String,
    val locality_verbose: String,
    val longitude: String,
    val zipcode: String
)