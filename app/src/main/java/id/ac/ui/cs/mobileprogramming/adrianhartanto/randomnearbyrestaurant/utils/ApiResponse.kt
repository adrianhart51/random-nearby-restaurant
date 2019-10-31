package id.ac.ui.cs.mobileprogramming.adrianhartanto.randomnearbyrestaurant.utils

import com.google.gson.JsonElement
import io.reactivex.annotations.NonNull
import io.reactivex.annotations.Nullable

class ApiResponse(
    val status: ApiResponseStatus, @Nullable
    val data: JsonElement?, @Nullable
    val error: Throwable?
) {
    companion object {
        fun loading(): ApiResponse {
            return ApiResponse(ApiResponseStatus.LOADING, null, null)
        }

        fun success(@NonNull data: JsonElement): ApiResponse {
            return ApiResponse(ApiResponseStatus.SUCCESS, data, null)
        }

        fun error(@NonNull error: Throwable): ApiResponse {
            return ApiResponse(ApiResponseStatus.ERROR, null, error)
        }
    }
}