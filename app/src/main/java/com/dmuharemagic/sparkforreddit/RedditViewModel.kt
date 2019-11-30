package com.dmuharemagic.sparkforreddit

import android.app.Application
import androidx.lifecycle.*
import com.dmuharemagic.sparkforreddit.model.RedditNews
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RedditViewModel(application: Application) : AndroidViewModel(application) {

    private val _users = MediatorLiveData<Resource<RedditNews>>()
    val users: LiveData<Resource<RedditNews>> get() = _users
    private var usersSource: LiveData<Resource<RedditNews>> = MutableLiveData()

    init {
        getPostsCached("")
    }

    fun getPostsCached(after: String, limit: String = "10") {
        viewModelScope.launch {
            _users.removeSource(usersSource)

            withContext(Dispatchers.IO) {
                usersSource = PostsRepository(getApplication()).getPostsWithCache(after, limit)
            }

            _users.addSource(usersSource) {
                _users.value
            }
        }
    }
}