package id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.dao

data class UserRating(
    val aggregate_rating: String,
    val rating_color: String,
    val rating_obj: RatingObj,
    val rating_text: String,
    val votes: String
)