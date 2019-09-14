package ru.visdom.raiffeisenbusinessad.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.visdom.raiffeisenbusinessad.network.auth.UserNetwork
import ru.visdom.raiffeisenbusinessad.preferences.UserPreferences
import ru.visdom.raiffeisenbusinessad.utils.getTokenFromPhoneAndPassword

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * This is the job for all coroutines started by this ViewModel.
     *
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * Event triggered for network error. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private var _eventNetworkError = MutableLiveData<Boolean>()

    /**
     * Event triggered for network error. Views should use this to get access
     * to the data.
     */
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    /**
     * Flag to display the error message. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private var _isNetworkErrorShown = MutableLiveData<Boolean>()

    /**
     * Flag to display the error message. Views should use this to get access
     * to the data.
     */
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown


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

    /**
     * Flag to display state of user auth. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private var _isAuthed = MutableLiveData<Boolean>()

    /**
     * Flag to display state of user auth.
     */
    val isAuthed: LiveData<Boolean>
        get() = _isAuthed

    /**
     * Resets the network error flag.
     */
    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }


    init {
        _eventNetworkError.value = false
        _isNetworkErrorShown.value = false
        _isProgressShow.value = false
        _isAuthed.value = false

        if (UserPreferences.getPhone() != null) {
            authUser(UserPreferences.getPhone()!!, UserPreferences.getPassword()!!)
        }
    }

    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun authUser(phone: String, password: String) {
        viewModelScope.launch {
            //Show Progress bar
            _isProgressShow.value = true

            var prevPhone = ""

            if (UserPreferences.getPhone() != null)
                prevPhone = UserPreferences.getPhone()!!

            //Request for auth user
            try {
                val user =
                    UserNetwork.userService.login(getTokenFromPhoneAndPassword(phone, password))
                        .await()

                // Save the user to preferences
                UserPreferences.saveUser(user, password)

                _isAuthed.value = prevPhone == phone

                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
            } catch (e: Exception) {
                // Clear the user preferences
                UserPreferences.clear()
                _isAuthed.value = false
                _eventNetworkError.value = true
            }

            // Hide Progress Bar
            _isProgressShow.value = false
        }
    }

    /**
     * Factory for constructing AuthViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AuthViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}