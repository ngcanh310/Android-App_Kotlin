package com.example.mylife.ui.Foodvisor

import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

val apiKey = "jJG1lY4Z.sBHHG7scKpZaDFCP9YWQHNOLsvPoOJ5R"
private const val BASE_URL = "https://vision.foodvisor.io/api/1.0/en/"
val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val originalRequest: Request = chain.request()

            val newRequest: Request = originalRequest.newBuilder()
                .header("Authorization", "Api-Key $apiKey")
                .build()

            return chain.proceed(newRequest)
        }
    })
    .build()
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface FoodvisorApiService {
    @Multipart
    @POST("analysis")
    suspend fun uploadImage(
        @Part scopes: MultipartBody.Part,
        @Part image: MultipartBody.Part
    ): Response<FoodDetectionResponse>
}

object FoodvisorApi {
    val retrofitService: FoodvisorApiService by lazy {
        retrofit.create(FoodvisorApiService::class.java)
    }
}

data class FoodDetectionResponse(
    val scope: List<String>,
    val items: List<AnalysisItem>
)

data class AnalysisItem(
    val food: List<AnalysisFood>
)

data class AnalysisFood(
    val confidence: Float,
    val ingredients: List<AnalysisFood>,
    val food_info: FoodInfo
)

data class FoodInfo(
    val food_id: String,
    val display_name: String,
    val g_per_serving: Float,
    val nutrition: nutrition
)

data class nutrition(
    val calories_100g: Float,
    val proteins_100g: Float,
    val fat_100g: Float,
    val carbs_100g: Float
)