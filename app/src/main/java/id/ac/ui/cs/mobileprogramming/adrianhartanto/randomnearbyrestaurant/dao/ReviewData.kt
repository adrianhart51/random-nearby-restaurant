package id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.dao

data class ReviewData(
    val comments_count: Int,
    val id: Int,
    val likes: Int,
    val rating: Int,
    val rating_color: String,
    val rating_text: String,
    val review_text: String,
    val review_time_friendly: String,
    val timestamp: Int,
    val user: User
)