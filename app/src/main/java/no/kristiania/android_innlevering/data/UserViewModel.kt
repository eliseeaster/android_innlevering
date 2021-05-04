package no.kristiania.android_innlevering.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class UserViewModel(private val repository: UserRepository): ViewModel() {
    val allUsers: LiveData<List<User>> = repository.allUsers.asLiveData()

    fun insert(user: User) = userModelScope.launch {
        repository.insert(user)
    }
}

class UserViewModelFactory(private val repository: UserRepository) : UserModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(repository) as T
        }

throw IllegalArgumentException("Unknown UserModel class")
    }
}