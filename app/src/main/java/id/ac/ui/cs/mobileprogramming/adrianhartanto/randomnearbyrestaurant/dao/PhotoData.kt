package id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.dao

data class PhotoData(
    val caption: String,
    val friendly_time: String,
    val height: Int,
    val id: String,
    val res_id: Int,
    val thumb_url: String,
    val timestamp: Int,
    val url: String,
    val user: User,
    val width: Int
)