package ru.visdom.raiffeisenbusinessad.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.visdom.raiffeisenbusinessad.domain.Ad
import ru.visdom.raiffeisenbusinessad.network.ad.AdNetwork
import ru.visdom.raiffeisenbusinessad.network.ad.AdService
import ru.visdom.raiffeisenbusinessad.network.ad.asDomain
import ru.visdom.raiffeisenbusinessad.preferences.UserPreferences
import ru.visdom.raiffeisenbusinessad.utils.getTokenFromPhoneAndPassword
import java.lang.Exception

class AdListViewModel(application: Application) : AndroidViewModel(application) {
    private var _ads = MutableLiveData<List<Ad>>()

    val ads: LiveData<List<Ad>>
        get() = _ads

    /**
     * This is the job for all coroutines started by this ViewModel.
     *
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * Flag to display the progress bar. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private var _isProgressShow = MutableLiveData<Boolean>()

    /**
     * Flag to display the progress bar.
     */
    val isProgressShow: LiveData<Boolean>
        get() = _isProgressShow


    fun update() {
        viewModelScope.launch {
            _isProgressShow.value = true
            try {
                val data = AdNetwork.adService.getAds(
                    getTokenFromPhoneAndPassword(
                        UserPreferences.getPhone()!!,
                        UserPreferences.getPassword()!!
                    )
                ).await()


                Log.i("ads", data.toString())

                _ads.value = data.asDomain()
            } catch (e: Exception) {
                Log.e("ads", e.toString())
            }
            _isProgressShow.value = false
        }

    }

    /**
     * Factory for constructing AuthViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AdListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AdListViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }

    }
}