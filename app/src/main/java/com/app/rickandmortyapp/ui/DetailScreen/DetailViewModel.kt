package com.app.rickandmortyapp.ui.DetailScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.imkbapp.model.Status
import com.app.rickandmortyapp.data.repository.RemoteRepository
import com.app.rickandmortyapp.model.CharacterDetailResponse.CharacterDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(val repository:RemoteRepository) : ViewModel() {

    val _mutableCharacterList: MutableLiveData<CharacterDetail> = MutableLiveData()
    val _loading: MutableLiveData<Boolean> = MutableLiveData()



    fun fetchCharacterDetail(id:String) {
        viewModelScope.launch {
            repository.fetchCharacterDetail(id).collect {
                when(it.status){
                    Status.SUCCESS->{
                        _mutableCharacterList.value = it.data
                        _loading.value = false
                    }
                    Status.LOADING->{
                        _loading.value = true
                    }
                    Status.ERROR->{
                        _loading.value = false
                        _mutableCharacterList.value = null
                    }
                }
            }
        }
    }
}