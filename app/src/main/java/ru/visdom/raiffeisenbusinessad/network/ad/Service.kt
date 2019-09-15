package ru.visdom.raiffeisenbusinessad.network.ad

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import ru.visdom.raiffeisenbusinessad.utils.NetworkUrlConstants

/**
 * A retrofit service to fetch a user
 */
interface AdService {

    @GET("ad/user/ads")
    fun getAds(@Header("Authorization") token: String): Deferred<List<AdDto>>
}

/**
 * Main entry point for network access
 */
object AdNetwork {
    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl(NetworkUrlConstants.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val adService = retrofit.create(AdService::class.java)
}