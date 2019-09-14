package ru.visdom.raiffeisenbusinessad.network.auth

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
interface UserService {

    @GET("users/login")
    fun login(@Header("Authorization") token: String): Deferred<UserDto>
}

/**
 * Main entry point for network access
 */
object UserNetwork {
    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl(NetworkUrlConstants.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val userService = retrofit.create(UserService::class.java)
}